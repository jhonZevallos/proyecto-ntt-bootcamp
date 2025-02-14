package com.nttdata.payments.service.impl;

import com.nttdata.payments.mapper.PaymentMapper;
import com.nttdata.payments.model.Payment;
import com.nttdata.payments.model.PaymentRequest;
import com.nttdata.payments.repository.PaymentRepository;
import com.nttdata.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private PaymentMapper mapper;

    @Override
    public Flux<Payment> findAll() {
        return repository.findAll().map(mapper::getPaymentOfPaymentEntity);
    }

    @Override
    public Flux<Payment> findByIdProduct(String idProduct) {
        return repository.findByIdProduct(idProduct).map(mapper::getPaymentOfPaymentEntity);
    }

    @Override
    public Mono<Payment> create(PaymentRequest request) {
        return repository.save(mapper.getPaymentEntityOfPaymentRequest(request))
                .map(mapper::getPaymentOfPaymentEntity);
    }

    @Override
    public Mono<Payment> update(Payment request) {
        return repository.findById(request.getId())
                .flatMap(exist ->
                        repository.save(mapper.getPaymentEntityOfPayment(request)))
                .map(mapper::getPaymentOfPaymentEntity);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}
