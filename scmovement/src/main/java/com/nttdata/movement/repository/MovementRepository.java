package com.nttdata.movement.repository;

import com.nttdata.movement.model.MovementEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MovementRepository extends ReactiveMongoRepository<MovementEntity, String> {

    Flux<MovementEntity> findByIdProduct(String idProduct);
}
