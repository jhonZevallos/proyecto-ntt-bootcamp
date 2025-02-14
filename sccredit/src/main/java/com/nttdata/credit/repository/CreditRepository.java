package com.nttdata.credit.repository;

import com.nttdata.credit.model.CreditEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CreditRepository extends ReactiveMongoRepository<CreditEntity,String> {

    Flux<CreditEntity> findByCustomerId(String id);
}