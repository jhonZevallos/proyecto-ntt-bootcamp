package com.nttdata.transaction.adapter.impl;

import com.nttdata.transaction.adapter.CustomerAdapter;
import com.nttdata.transaction.server.customer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CustomerAdapterImpl implements CustomerAdapter {

    @Autowired
    private WebClient clientCustomer;

    public Mono<Customer> getCustomerById(String id) {
        return clientCustomer.get()
                .uri("/customer/findById/{id}",id)
                .retrieve()
                .bodyToMono(Customer.class);
    }
}
