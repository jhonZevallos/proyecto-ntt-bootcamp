package com.nttdata.transaction.adapter.impl;

import com.nttdata.transaction.server.customer.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CustomerAdapterImplTest {

    @Mock
    private WebClient clientCreditCard;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private CustomerAdapterImpl customerAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(clientCreditCard.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    public void testGetCustomerById_Success() {
        Customer customer = new Customer();
        customer.setId("123456789");
        when(responseSpec.bodyToMono(Customer.class))
                .thenReturn(Mono.just(customer));

        Mono<Customer> result = customerAdapter.getCustomerById("123456789");

        StepVerifier.create(result)
                .expectNextMatches(c -> c.getId().equals("123456789"))
                .verifyComplete();
    }

    @Test
    public void testGetCustomerById_Error() {
        when(responseSpec.bodyToMono(Customer.class))
                .thenReturn(Mono.error(new WebClientResponseException
                        (404, "Not Found", null, null, null)));

        Mono<Customer> result = customerAdapter.getCustomerById("123456789");

        StepVerifier.create(result)
                .expectError(WebClientResponseException.class)
                .verify();
    }
}