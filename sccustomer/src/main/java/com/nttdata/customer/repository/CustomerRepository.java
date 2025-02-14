package com.nttdata.customer.repository;

import com.nttdata.customer.model.CustomerEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<CustomerEntity, String> {
}
