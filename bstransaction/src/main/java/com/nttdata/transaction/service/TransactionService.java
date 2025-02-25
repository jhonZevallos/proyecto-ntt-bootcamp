package com.nttdata.transaction.service;

import com.nttdata.transaction.client.model.Product;
import com.nttdata.transaction.client.model.TransferRequest;
import com.nttdata.transaction.client.model.TransferResponse;
import com.nttdata.transaction.server.account.model.Account;
import com.nttdata.transaction.server.account.model.AccountRequest;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<Product> listProduct(String idClient);
    Mono<Account> createAccount(AccountRequest request);
    Mono<TransferResponse> transferBetweenAccounts(TransferRequest request);
}