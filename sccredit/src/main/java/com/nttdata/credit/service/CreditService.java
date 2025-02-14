package com.nttdata.credit.service;

import com.nttdata.credit.model.Credit;
import com.nttdata.credit.model.CreditRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {

    Flux<Credit> findAll();
    Flux<Credit> findByCustomerId(String id);
    Mono<Credit> findById(String id);
    Mono<Credit> create(CreditRequest request);
    Mono<Credit> update(Credit request);
    Mono<Void> delete(String id);
}
