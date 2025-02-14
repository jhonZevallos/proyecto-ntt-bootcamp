package com.nttdata.creditcard.repository;

import com.nttdata.creditcard.model.CreditCardEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CreditCardRepository extends ReactiveMongoRepository<CreditCardEntity,String> {

    Flux<CreditCardEntity> findByCustomerId(String id);
}
