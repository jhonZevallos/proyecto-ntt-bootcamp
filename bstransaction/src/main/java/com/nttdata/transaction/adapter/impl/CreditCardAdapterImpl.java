package com.nttdata.transaction.adapter.impl;

import com.nttdata.transaction.adapter.CreditCardAdapter;
import com.nttdata.transaction.server.creditcard.model.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;

@Component
public class CreditCardAdapterImpl implements CreditCardAdapter {

    @Autowired
    private WebClient clientCreditCard;

    @Override
    public Mono<List<CreditCard>> getCreditCardByCustomerId(String id) {
        return clientCreditCard.get()
                .uri("/creditcard/findByCustomerId/{id}",id)
                .retrieve()
                .bodyToFlux(CreditCard.class)
                .collectList();
    }
}
