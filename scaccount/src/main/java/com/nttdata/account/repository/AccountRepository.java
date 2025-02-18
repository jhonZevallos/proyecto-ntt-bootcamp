package com.nttdata.account.repository;

import com.nttdata.account.model.AccountEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<AccountEntity,String> {

    Flux<AccountEntity> findByTitularCuenta(String idHolder);
    Mono<AccountEntity> findByNumeroCuenta(String numeroCuenta);
    Mono<AccountEntity> findByNumeroCuentaCci(String numeroCuentaCci);
}
