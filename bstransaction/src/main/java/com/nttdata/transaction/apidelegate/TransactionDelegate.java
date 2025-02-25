package com.nttdata.transaction.apidelegate;

import com.nttdata.transaction.client.api.TransactionApiDelegate;
import com.nttdata.transaction.client.model.*;
import com.nttdata.transaction.mapper.TransactionMapper;
import com.nttdata.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TransactionDelegate implements TransactionApiDelegate {

    @Autowired
    private TransactionService service;

    @Autowired
    private TransactionMapper mapper;

    @Override
    public Mono<ResponseEntity<Product>> listProduct(String idClient, ServerWebExchange exchange) {
        return service.listProduct(idClient).map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<AccountResponse>> registerAccount(Mono<AccountClientRequest> accountClientRequest, ServerWebExchange exchange) {
        return accountClientRequest
                .flatMap(request -> service.createAccount(mapper.getAccountRequestOfAccountClientRequest(request)))
                .flatMap(account -> {
                    AccountResponse accountResponse = mapper.getAccountResponseOfAccount(account);
                    return Mono.just(ResponseEntity.ok(accountResponse));
                });
    }

    @Override
    public Mono<ResponseEntity<TransferResponse>> transferAnotherAccount(Mono<TransferRequest> transferRequest, ServerWebExchange exchange) {
        return transferRequest
                .flatMap(request -> service.transferBetweenAccounts(request))
                .map(ResponseEntity::ok);
    }
}
