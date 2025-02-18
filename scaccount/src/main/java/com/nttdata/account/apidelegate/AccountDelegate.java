package com.nttdata.account.apidelegate;

import com.nttdata.account.api.AccountApiDelegate;
import com.nttdata.account.model.Account;
import com.nttdata.account.model.AccountRequest;
import com.nttdata.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AccountDelegate implements AccountApiDelegate {

    @Autowired
    private AccountService service;

    @Override
    public Mono<ResponseEntity<Void>> deleteAccount(String id, ServerWebExchange exchange) {
        return service.delete(id).then(Mono.just(ResponseEntity.ok().<Void>build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<Account>>> listAccount(ServerWebExchange exchange) {
        return service.findAll()
                .collectList()
                .flatMap(accounts ->
                        Mono.just(ResponseEntity.ok(Flux.fromIterable(accounts))));
    }

    @Override
    public Mono<ResponseEntity<Flux<Account>>> accountByHolderId(String id, ServerWebExchange exchange) {
        return service.findByHolder(id).collectList()
                .flatMap( accounts ->
                        Mono.just(ResponseEntity.ok(Flux.fromIterable(accounts))));
    }

    @Override
    public Mono<ResponseEntity<Account>> accountByAccountNumber(String accountNumber, ServerWebExchange exchange) {
        return service.findByAccountNumber(accountNumber).map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Account>> accountByAccountNumberCci(String accountNumberCci, ServerWebExchange exchange) {
        return service.findByAccountNumberCci(accountNumberCci).map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Account>> accountById(String id, ServerWebExchange exchange) {
        return service.findById(id).map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Account>> registerAccount(Mono<AccountRequest> accountRequest, ServerWebExchange exchange) {
        return accountRequest.flatMap(request ->
                service.create(request).map(ResponseEntity::ok));
    }

    @Override
    public Mono<ResponseEntity<Account>> updateAccount(Mono<Account> account, ServerWebExchange exchange) {
        return account.flatMap(request ->
                service.update(request).map(ResponseEntity::ok));
    }
}
