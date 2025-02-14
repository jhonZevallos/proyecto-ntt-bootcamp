package com.nttdata.movement.apidelegate;

import com.nttdata.movement.api.MovementApiDelegate;
import com.nttdata.movement.model.Movement;
import com.nttdata.movement.model.MovementRequest;
import com.nttdata.movement.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MovementDelegate implements MovementApiDelegate {

    @Autowired
    private MovementService service;

    @Override
    public Mono<ResponseEntity<Void>> deleteMovement(String id, ServerWebExchange exchange) {
        return service.delete(id).then(Mono.just(ResponseEntity.ok().<Void>build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<Movement>>> findByProductId(String idProduct, ServerWebExchange exchange) {
        return service.findByIdProduct(idProduct).collectList()
                .flatMap(collect ->
                        Mono.just(ResponseEntity.ok(Flux.fromIterable(collect))));
    }

    @Override
    public Mono<ResponseEntity<Flux<Movement>>> listMovement(ServerWebExchange exchange) {
        return service.findAll().collectList()
                .flatMap(collect ->
                        Mono.just(ResponseEntity.ok(Flux.fromIterable(collect))));
    }

    @Override
    public Mono<ResponseEntity<Movement>> registerMovement(Mono<MovementRequest> movementRequest, ServerWebExchange exchange) {
        return movementRequest.flatMap( request ->
                service.create(request).map(ResponseEntity::ok));
    }

    @Override
    public Mono<ResponseEntity<Movement>> updateMovement(Mono<Movement> movement, ServerWebExchange exchange) {
        return movement.flatMap(request ->
                service.update(request).map(ResponseEntity::ok));
    }
}
