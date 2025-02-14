package com.nttdata.transaction.adapter;

import com.nttdata.transaction.server.creditcard.model.CreditCard;
import reactor.core.publisher.Mono;
import java.util.List;

public interface CreditCardAdapter {

    Mono<List<CreditCard>> getCreditCardByCustomerId(String id);
}
