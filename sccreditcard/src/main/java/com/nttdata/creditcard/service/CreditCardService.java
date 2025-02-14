package com.nttdata.creditcard.service;

import com.nttdata.creditcard.model.CreditCard;
import com.nttdata.creditcard.model.CreditCardRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardService {

    Flux<CreditCard> findAll();
    Flux<CreditCard> findByCustomerId(String id);
    Mono<CreditCard> findById(String id);
    Mono<CreditCard> create(CreditCardRequest request);
    Mono<CreditCard> update(CreditCard request);
    Mono<Void> delete(String id);
}
