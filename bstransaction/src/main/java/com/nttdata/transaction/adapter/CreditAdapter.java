package com.nttdata.transaction.adapter;

import com.nttdata.transaction.server.credit.model.Credit;
import reactor.core.publisher.Mono;
import java.util.List;

public interface CreditAdapter {

    Mono<List<Credit>> getCreditByCustomerId(String id);
}
