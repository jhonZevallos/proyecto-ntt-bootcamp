package com.nttdata.transaction.adapter.impl;

import com.nttdata.transaction.server.account.model.Account;
import com.nttdata.transaction.server.account.model.AccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AccountAdapterImplTest {

    @Mock
    private WebClient clientAccount;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private AccountAdapterImpl accountAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(clientAccount.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(clientAccount.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    public void testGetAccountByIdHolder_Success() {
        Account account = new Account();
        account.setAccountNumber("123456789");
        when(responseSpec.bodyToFlux(Account.class))
                .thenReturn(Flux.just(account));

        Mono<List<Account>> result = accountAdapter.getAccountByIdHolder("holder123");

        StepVerifier.create(result)
                .expectNextMatches(accounts -> accounts.size() == 1
                        && accounts.get(0).getAccountNumber().equals("123456789"))
                .verifyComplete();
    }

    @Test
    public void testGetAccountByIdHolder_Error() {
        when(responseSpec.bodyToFlux(Account.class))
                .thenReturn(Flux
                        .error(new WebClientResponseException(404, "Not Found", null, null, null)));

        Mono<List<Account>> result = accountAdapter.getAccountByIdHolder("holder123");

        StepVerifier.create(result)
                .expectError(WebClientResponseException.class)
                .verify();
    }

    @Test
    public void testCreateAccount_Success() {
        Account account = new Account();
        account.setAccountNumber("123456789");
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber("123456789");
        when(responseSpec.bodyToMono(Account.class)).thenReturn(Mono.just(account));

        Mono<Account> result = accountAdapter.createAccount(accountRequest);

        StepVerifier.create(result)
                .expectNextMatches(acc -> acc.getAccountNumber().equals("123456789"))
                .verifyComplete();
    }

    @Test
    public void testCreateAccount_Error() {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber("123456789");
        when(responseSpec.bodyToMono(Account.class)).thenReturn(Mono.error(new WebClientResponseException(400, "Bad Request", null, null, null)));

        Mono<Account> result = accountAdapter.createAccount(accountRequest);

        StepVerifier.create(result)
                .expectError(WebClientResponseException.class)
                .verify();
    }
}
