package com.nttdata.account.service.impl;

import com.nttdata.account.mapper.AccountMapper;
import com.nttdata.account.model.Account;
import com.nttdata.account.model.AccountRequest;
import com.nttdata.account.repository.AccountRepository;
import com.nttdata.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private AccountMapper mapper;

    @Override
    public Flux<Account> findAll() {
        return repository.findAll().map(mapper::getAccountOfAccountEntity);
    }

    @Override
    public Flux<Account> findByHolder(String idHolder) {
        return repository.findByTitularCuenta(idHolder).map(mapper::getAccountOfAccountEntity);
    }

    @Override
    public Mono<Account> findById(String id) {
        return repository.findById(id).map(mapper::getAccountOfAccountEntity);
    }

    @Override
    public Mono<Account> create(AccountRequest request) {
        return repository.save(mapper.getAccountEntityOfAccountRequest(request))
                .map(mapper::getAccountOfAccountEntity);
    }

    @Override
    public Mono<Account> update(Account request) {
        return repository.findById(request.getId())
                .flatMap(existing ->
                        repository.save(mapper.getAccountEntityOfAccount(request))
                .map(mapper::getAccountOfAccountEntity)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit no encontrado"))));
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}
