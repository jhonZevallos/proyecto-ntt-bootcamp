package com.nttdata.transaction.mapper;

import com.nttdata.transaction.client.model.AccountClientRequest;
import com.nttdata.transaction.client.model.AccountResponse;
import com.nttdata.transaction.server.account.model.Account;
import com.nttdata.transaction.server.account.model.AccountRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class TransactionMapper {

    public AccountRequest getAccountRequestOfAccountClientRequest(AccountClientRequest clientRequest) {
        AccountRequest server = new AccountRequest();
        server.setHolder(clientRequest.getHolder());
        server.setTypeAccount(AccountRequest.TypeAccountEnum
                .valueOf(clientRequest.getTypeAccount().getValue().toUpperCase()));
        server.setBalance(clientRequest.getBalance());
        server.maintenanceCommission(clientRequest.getMaintenanceCommission());
        server.setLimitMovement(clientRequest.getLimitMovement());
        server.setOpeningDate(this.convertToOffsetDateTime(LocalDateTime.now()));
        return server;
    }

    public AccountResponse getAccountResponseOfAccount(Account server) {
        AccountResponse client = new AccountResponse();
        client.setId(server.getId());
        client.setHolder(server.getHolder());
        client.setTypeAccount(AccountResponse.TypeAccountEnum
                .valueOf(server.getTypeAccount().getValue()));
        client.setBalance(server.getBalance());
        client.setOpeningDate(server.getOpeningDate());
        client.setClosingDate(server.getClosingDate());
        client.setMaintenanceCommission(server.getMaintenanceCommission());
        client.setLimitMovement(server.getLimitMovement());
        return client;
    }

    private OffsetDateTime convertToOffsetDateTime(LocalDateTime localDateTime) {
        ZoneOffset offset = ZoneOffset.of("+00:00");
        return localDateTime.atOffset(offset);
    }
}
