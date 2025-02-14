package com.nttdata.creditcard.service.impl;

import com.nttdata.creditcard.mapper.CreditCardMapper;
import com.nttdata.creditcard.model.CreditCard;
import com.nttdata.creditcard.model.CreditCardRequest;
import com.nttdata.creditcard.repository.CreditCardRepository;
import com.nttdata.creditcard.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardRepository repository;

    @Autowired
    private CreditCardMapper mapper;

    @Override
    public Flux<CreditCard> findAll() {
        return repository.findAll().map(mapper::getCreditCardOfCreditCardEntity);
    }

    @Override
    public Flux<CreditCard> findByCustomerId(String id) {
        return repository.findByCustomerId(id).map(mapper::getCreditCardOfCreditCardEntity);
    }

    @Override
    public Mono<CreditCard> findById(String id) {
        return repository.findById(id).map(mapper::getCreditCardOfCreditCardEntity);
    }

    @Override
    public Mono<CreditCard> create(CreditCardRequest request) {
        return repository.save(mapper.getCreditCardEntityOfCreditCardRequest(request))
                .map(mapper::getCreditCardOfCreditCardEntity);
    }

    @Override
    public Mono<CreditCard> update(CreditCard request) {
        return repository.findById(request.getId())
                .flatMap(exist ->
                        repository.save(mapper.getCreditCardEntityOfCreditCard(request)))
                .map(mapper::getCreditCardOfCreditCardEntity)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Credito no encontrado")));
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}
