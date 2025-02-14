package com.nttdata.transaction.adapter;

import com.nttdata.transaction.server.account.model.Account;
import com.nttdata.transaction.server.account.model.AccountRequest;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountAdapter {

    Mono<List<Account>> getAccountByIdHolder(String idHolder);
    Mono<Account> createAccount(AccountRequest request);
}
