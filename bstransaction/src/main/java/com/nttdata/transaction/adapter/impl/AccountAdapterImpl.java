package com.nttdata.transaction.adapter.impl;

import com.nttdata.transaction.adapter.AccountAdapter;
import com.nttdata.transaction.server.account.model.Account;
import com.nttdata.transaction.server.account.model.AccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;

@Component
public class AccountAdapterImpl implements AccountAdapter {

    @Autowired
    private WebClient clientAccount;

    @Override
    public Mono<List<Account>> getAccountByIdHolder(String idHolder) {
        return clientAccount.get()
                .uri("/account/findByHolder/{idHolder}",idHolder)
                .retrieve()
                .bodyToFlux(Account.class)
                .collectList();
    }

    @Override
    public Mono<Account> createAccount(AccountRequest request) {
        return clientAccount.post()
                .uri("/account/created")
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(Account.class);
    }
}
