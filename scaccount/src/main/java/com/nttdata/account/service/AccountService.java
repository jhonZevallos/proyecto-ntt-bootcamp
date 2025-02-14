package com.nttdata.account.service;

import com.nttdata.account.model.Account;
import com.nttdata.account.model.AccountRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<Account> findAll();
    Flux<Account> findByHolder(String idHolder);
    Mono<Account> findById(String id);
    Mono<Account> create(AccountRequest request);
    Mono<Account> update(Account request);
    Mono<Void> delete(String id);
}
