package com.nttdata.payments.apidelegate;

import com.nttdata.payments.api.PaymentApiDelegate;
import com.nttdata.payments.model.Payment;
import com.nttdata.payments.model.PaymentRequest;
import com.nttdata.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PaymentDelegate implements PaymentApiDelegate {

    @Autowired
    private PaymentService service;

    @Override
    public Mono<ResponseEntity<Void>> deletePayment(String id, ServerWebExchange exchange) {
        return service.delete(id).then(Mono.just(ResponseEntity.ok().build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<Payment>>> findByProductId(String idProduct, ServerWebExchange exchange) {
        return service.findByIdProduct(idProduct).collectList()
                .flatMap(collect ->
                        Mono.just(ResponseEntity.ok(Flux.fromIterable(collect))));
    }

    @Override
    public Mono<ResponseEntity<Flux<Payment>>> listPayment(ServerWebExchange exchange) {
        return service.findAll().collectList()
                .flatMap(collect ->
                        Mono.just(ResponseEntity.ok(Flux.fromIterable(collect))));
    }

    @Override
    public Mono<ResponseEntity<Payment>> registerPayment(Mono<PaymentRequest> paymentRequest, ServerWebExchange exchange) {
        return paymentRequest.flatMap( request ->
                service.create(request).map(ResponseEntity::ok));
    }

    @Override
    public Mono<ResponseEntity<Payment>> updatePayment(Mono<Payment> payment, ServerWebExchange exchange) {
        return payment.flatMap(request ->
                service.update(request).map(ResponseEntity::ok));
    }
}
