package com.nttdata.credit.service.impl;

import com.nttdata.credit.mapper.CreditMapper;
import com.nttdata.credit.model.Credit;
import com.nttdata.credit.model.CreditRequest;
import com.nttdata.credit.repository.CreditRepository;
import com.nttdata.credit.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private CreditRepository repository;

    @Autowired
    private CreditMapper mapper;

    @Override
    public Flux<Credit> findAll() {
        return repository.findAll().map(mapper::getCreditOfCreditEntity);
    }

    @Override
    public Flux<Credit> findByCustomerId(String id) {
        return repository.findByCustomerId(id).map(mapper::getCreditOfCreditEntity);
    }

    @Override
    public Mono<Credit> findById(String id) {
        return repository.findById(id).map(mapper::getCreditOfCreditEntity);
    }

    @Override
    public Mono<Credit> create(CreditRequest request) {
        return repository.save(mapper.getCreditEntityOfCreditRequest(request))
                .map(mapper::getCreditOfCreditEntity);
    }

    @Override
    public Mono<Credit> update(Credit request) {
        return repository.findById(request.getId())
                .flatMap(exist ->
                        repository.save(mapper.getCreditEntityOfCredit(request)))
                .map(mapper::getCreditOfCreditEntity)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Credito no encontrado")));
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}
