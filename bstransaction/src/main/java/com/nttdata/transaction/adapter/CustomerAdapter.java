package com.nttdata.transaction.adapter;

import com.nttdata.transaction.server.customer.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerAdapter {

    Mono<Customer> getCustomerById(String id);
}
