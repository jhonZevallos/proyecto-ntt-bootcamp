package com.nttdata.credit.apidelegate;

import com.nttdata.credit.api.CreditApiDelegate;
import com.nttdata.credit.model.Credit;
import com.nttdata.credit.model.CreditRequest;
import com.nttdata.credit.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CreditDelegate implements CreditApiDelegate {

    @Autowired
    private CreditService service;

    @Override
    public Mono<ResponseEntity<Credit>> creditById(String id, ServerWebExchange exchange) {
        return service.findById(id).map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCredit(String id, ServerWebExchange exchange) {
        return service.delete(id).then(Mono.just(ResponseEntity.ok().build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<Credit>>> listCredit(ServerWebExchange exchange) {
        return service.findAll()
                .collectList()
                .flatMap(credits ->
                        Mono.just(ResponseEntity.ok(Flux.fromIterable(credits))));
    }

    @Override
    public Mono<ResponseEntity<Flux<Credit>>> creditByCustomerId(String id, ServerWebExchange exchange) {
        return service.findByCustomerId(id)
                .collectList()
                .flatMap(credits ->
                        Mono.just(ResponseEntity.ok(Flux.fromIterable(credits))));
    }

    @Override
    public Mono<ResponseEntity<Credit>> registerCredit(Mono<CreditRequest> creditRequest, ServerWebExchange exchange) {
        return creditRequest.flatMap(request ->
                service.create(request).map(ResponseEntity::ok));
    }

    @Override
    public Mono<ResponseEntity<Credit>> updateCredit(Mono<Credit> credit, ServerWebExchange exchange) {
        return credit.flatMap(request ->
                service.update(request).map(ResponseEntity::ok));
    }
}
