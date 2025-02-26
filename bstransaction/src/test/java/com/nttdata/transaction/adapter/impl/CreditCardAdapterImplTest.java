package com.nttdata.transaction.adapter.impl;

import com.nttdata.transaction.server.creditcard.model.CreditCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CreditCardAdapterImplTest {

    @Mock
    private WebClient clientCreditCard;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private CreditCardAdapterImpl creditCardAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(clientCreditCard.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    public void testGetCreditCardByCustomerId_Success() {
        CreditCard creditCard = new CreditCard();
        creditCard.setCustomerId("123456789");
        when(responseSpec.bodyToFlux(CreditCard.class))
                .thenReturn(Flux.just(creditCard));

        Mono<List<CreditCard>> result = creditCardAdapter.getCreditCardByCustomerId("123456789");

        StepVerifier.create(result)
                .expectNextMatches(creditCards ->  creditCards.size() == 1
                && creditCards.get(0).getCustomerId().equals("123456789"))
                .verifyComplete();
    }

    @Test
    public void testGetCreditCardByCustomerId_Error() {
        when(responseSpec.bodyToFlux(CreditCard.class))
                .thenReturn(Flux.error(new WebClientResponseException
                        (404, "Not Found", null, null, null)));

        Mono<List<CreditCard>> result = creditCardAdapter.getCreditCardByCustomerId("123456789");

        StepVerifier.create(result)
                .expectError(WebClientResponseException.class)
                .verify();
    }
}
