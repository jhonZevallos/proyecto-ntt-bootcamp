package com.nttdata.customer.apidelegate;

import com.nttdata.customer.api.CustomerApiDelegate;
import com.nttdata.customer.model.Customer;
import com.nttdata.customer.model.CustomerRequest;
import com.nttdata.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerDelegate  implements CustomerApiDelegate {

    @Autowired
    private CustomerService service;


    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(String id, ServerWebExchange exchange) {
        return service.delete(id).then(Mono.just(ResponseEntity.ok().<Void>build()));
    }

    @Override
    public Mono<ResponseEntity<Customer>> findById(String id, ServerWebExchange exchange) {
        return service.findById(id).map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<Customer>>> listCustomer(ServerWebExchange exchange) {
        return service.findAll()
                .collectList()
                .flatMap(customers ->
                        Mono.just(ResponseEntity.ok(Flux.fromIterable(customers))));
    }

    @Override
    public Mono<ResponseEntity<Customer>> registerCustomer(Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) {
        return customerRequest.flatMap(request ->
                service.create(request).map(ResponseEntity::ok)
        );
    }

    @Override
    public Mono<ResponseEntity<Customer>> updateCustomer(Mono<Customer> customer, ServerWebExchange exchange) {
        return customer.flatMap(request ->
                service.update(request).map(ResponseEntity::ok));
    }
}
