package com.nttdata.payments.repository;

import com.nttdata.payments.model.PaymentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PaymentRepository extends ReactiveMongoRepository<PaymentEntity,String> {

    Flux<PaymentEntity> findByIdProduct(String idProduct);
}
