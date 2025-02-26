package com.nttdata.transaction.adapter.impl;

import com.nttdata.transaction.server.credit.model.Credit;
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

public class CreditAdapterImplTest {

    @Mock
    private WebClient clientCredit;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private CreditAdapterImpl creditAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(clientCredit.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    public void testGetCreditByCustomerId_Success() {
        Credit credit = new Credit();
        credit.setCustomerId("123456789");
        when(responseSpec.bodyToFlux(Credit.class))
                .thenReturn(Flux.just(credit));

        Mono<List<Credit>> result = creditAdapter.getCreditByCustomerId("123456789");

        StepVerifier.create(result)
                .expectNextMatches(credits -> credits.size() == 1
                && credits.get(0).getCustomerId().equals("123456789"))
                .verifyComplete();
    }

    @Test
    public void testGetCreditByCustomerId_Error() {
        when(responseSpec.bodyToFlux(Credit.class))
                .thenReturn(Flux.error(new WebClientResponseException
                        (404, "Not Found", null, null, null)));

        Mono<List<Credit>> result = creditAdapter.getCreditByCustomerId("123456789");

        StepVerifier.create(result)
                .expectError(WebClientResponseException.class)
                .verify();
    }
}
