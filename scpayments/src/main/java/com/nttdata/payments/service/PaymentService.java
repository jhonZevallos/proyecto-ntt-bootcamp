package com.nttdata.payments.service;

import com.nttdata.payments.model.Payment;
import com.nttdata.payments.model.PaymentRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentService {

    Flux<Payment> findAll();
    Flux<Payment> findByIdProduct(String idProduct);
    Mono<Payment> create(PaymentRequest request);
    Mono<Payment> update(Payment request);
    Mono<Void> delete(String id);
}
