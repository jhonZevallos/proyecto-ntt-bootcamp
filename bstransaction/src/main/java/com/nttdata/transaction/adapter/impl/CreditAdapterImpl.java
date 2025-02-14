package com.nttdata.transaction.adapter.impl;

import com.nttdata.transaction.adapter.CreditAdapter;
import com.nttdata.transaction.server.credit.model.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;

@Component
public class CreditAdapterImpl implements CreditAdapter {

    @Autowired
    private WebClient clientCredit;

    @Override
    public Mono<List<Credit>> getCreditByCustomerId(String id) {
        return clientCredit.get()
                .uri("/credit/findByCustomerId/{id}",id)
                .retrieve()
                .bodyToFlux(Credit.class)
                .collectList();
    }
}
