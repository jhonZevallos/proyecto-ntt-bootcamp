package com.nttdata.customer.service.impl;

import com.nttdata.customer.mapper.CustomerMapper;
import com.nttdata.customer.model.Customer;
import com.nttdata.customer.model.CustomerRequest;
import com.nttdata.customer.repository.CustomerRepository;
import com.nttdata.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerMapper mapper;

    @Override
    public Flux<Customer> findAll() {
        return repository.findAll().map(mapper::getCustomerOfCustomerEntity);
    }

    @Override
    public Mono<Customer> findById(String id) {
        return repository.findById(id).map(mapper::getCustomerOfCustomerEntity);
    }

    @Override
    public Mono<Customer> create(CustomerRequest request) {
        return repository.save(mapper.getCustomerEntityOfCustomerRequest(request))
                .map(mapper::getCustomerOfCustomerEntity);
    }

    @Override
    public Mono<Customer> update(Customer request) {
        return repository.findById(request.getId())
                .flatMap(existingCustomer ->
                        repository.save(mapper.getCustomerEntityOfCustomer(request))
                .map(mapper::getCustomerOfCustomerEntity)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer no encontrado"))));
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}
