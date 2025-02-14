package com.nttdata.customer.service;

import com.nttdata.customer.model.Customer;
import com.nttdata.customer.model.CustomerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
     Flux<Customer> findAll();
     Mono<Customer> findById(String id);
     Mono<Customer> create(CustomerRequest request);
     Mono<Customer> update(Customer request);
     Mono<Void> delete(String id);
}
