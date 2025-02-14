package com.nttdata.creditcard.apidelegate;

import com.nttdata.creditcard.api.CreditcardApiDelegate;
import com.nttdata.creditcard.model.CreditCard;
import com.nttdata.creditcard.model.CreditCardRequest;
import com.nttdata.creditcard.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CreditCardDelegate implements CreditcardApiDelegate {

    @Autowired
    private CreditCardService service;

    @Override
    public Mono<ResponseEntity<CreditCard>> creditCardById(String id, ServerWebExchange exchange) {
        return service.findById(id).map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCreditCard(String id, ServerWebExchange exchange) {
        return service.delete(id).then(Mono.just(ResponseEntity.ok().build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<CreditCard>>> listCreditCard(ServerWebExchange exchange) {
        return service.findAll()
                .collectList()
                .flatMap(credit -> Mono.just(ResponseEntity.ok(Flux.fromIterable(credit))));
    }

    @Override
    public Mono<ResponseEntity<Flux<CreditCard>>> creditCardByCustomerId(String id, ServerWebExchange exchange) {
        return service.findByCustomerId(id)
                .collectList()
                .flatMap(creditCards ->
                        Mono.just(ResponseEntity.ok(Flux.fromIterable(creditCards))));
    }

    @Override
    public Mono<ResponseEntity<CreditCard>> registerCreditCard(Mono<CreditCardRequest> creditCardRequest, ServerWebExchange exchange) {
        return creditCardRequest.flatMap(request ->
                service.create(request).map(ResponseEntity::ok));
    }

    @Override
    public Mono<ResponseEntity<CreditCard>> updateCreditCard(Mono<CreditCard> creditCard, ServerWebExchange exchange) {
        return creditCard.flatMap(request ->
                service.update(request).map(ResponseEntity::ok));
    }
}
