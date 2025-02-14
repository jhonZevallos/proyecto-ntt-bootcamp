package com.nttdata.movement.service.impl;

import com.nttdata.movement.mapper.MovementMapper;
import com.nttdata.movement.model.Movement;
import com.nttdata.movement.model.MovementRequest;
import com.nttdata.movement.repository.MovementRepository;
import com.nttdata.movement.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementServiceImpl implements MovementService {

    @Autowired
    private MovementRepository repository;

    @Autowired
    private MovementMapper mapper;

    @Override
    public Flux<Movement> findAll() {
        return repository.findAll().map(mapper::getMovementOfMovementEntity);
    }

    @Override
    public Flux<Movement> findByIdProduct(String idProduct) {
        return repository.findByIdProduct(idProduct).map(mapper::getMovementOfMovementEntity);
    }

    @Override
    public Mono<Movement> create(MovementRequest request) {
        return repository.save(mapper.getMovementEntityOfMovementRequest(request))
                .map(mapper::getMovementOfMovementEntity);
    }

    @Override
    public Mono<Movement> update(Movement request) {
        return repository.findById(request.getId())
                .flatMap(exist -> repository.save(mapper.getMovementEntityOfMovement(request)))
                .map(mapper::getMovementOfMovementEntity)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @Override
    public Mono<Void> delete(String id) {
         return repository.deleteById(id);
    }
}
