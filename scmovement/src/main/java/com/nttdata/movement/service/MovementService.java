package com.nttdata.movement.service;

import com.nttdata.movement.model.Movement;
import com.nttdata.movement.model.MovementRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {

    Flux<Movement> findAll();
    Flux<Movement> findByIdProduct(String idProduct);
    Mono<Movement> create(MovementRequest request);
    Mono<Movement> update(Movement request);
    Mono<Void> delete(String id);
}
