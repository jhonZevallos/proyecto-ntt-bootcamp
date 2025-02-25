package com.nttdata.transaction.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nttdata.transaction.client.model.AccountClientRequest;
import com.nttdata.transaction.server.account.model.Account;
import com.nttdata.transaction.server.account.model.AccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class TransactionMapperTest {

    private final TransactionMapper transactionMapper = new TransactionMapper();
    private AccountClientRequest clientRequest;
    private Account server;

    @BeforeEach
    public void setUp() {
        clientRequest = new AccountClientRequest();
        clientRequest.setHolder("holder123");
        clientRequest.setAccountNumber("123456789");
        clientRequest.setAccountNumberCCI("987654321");
        clientRequest.setTypeAccount(AccountClientRequest.TypeAccountEnum.AHORRO);
        clientRequest.setBalance(new BigDecimal("1000.00"));
        clientRequest.setMaintenanceCommission(new BigDecimal("10.00"));
        clientRequest.setLimitMovement(5);

        server = new Account();
        server.setId("1");
        server.setHolder("holder123");
        server.setAccountNumber("123456789");
        server.setAccountNumberCCI("987654321");
        server.setTypeAccount(Account.TypeAccountEnum.AHORRO);
        server.setBalance(new BigDecimal("1000.00"));
        server.setOpeningDate(OffsetDateTime.now());
        server.setClosingDate(OffsetDateTime.now().plusYears(1));
        server.setMaintenanceCommission(new BigDecimal("10.00"));
        server.setLimitMovement(5);
    }

    @Test
    public void testGetAccountRequestOfAccountClientRequest() {

        AccountRequest accountRequest = transactionMapper.getAccountRequestOfAccountClientRequest(clientRequest);

        assertEquals(clientRequest.getHolder(), accountRequest.getHolder());
        assertEquals(clientRequest.getAccountNumber(), accountRequest.getAccountNumber());
        assertEquals(clientRequest.getAccountNumberCCI(), accountRequest.getAccountNumberCCI());
        assertEquals(clientRequest.getTypeAccount().getValue(), accountRequest.getTypeAccount().getValue());
        assertEquals(clientRequest.getBalance(), accountRequest.getBalance());
        assertEquals(clientRequest.getMaintenanceCommission(), accountRequest.getMaintenanceCommission());
        assertEquals(clientRequest.getLimitMovement(), accountRequest.getLimitMovement());
        assertEquals(LocalDateTime.now().getDayOfYear(), accountRequest.getOpeningDate().toLocalDateTime().getDayOfYear());
    }
}
