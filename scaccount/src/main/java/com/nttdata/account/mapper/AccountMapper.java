package com.nttdata.account.mapper;

import com.nttdata.account.model.Account;
import com.nttdata.account.model.AccountEntity;
import com.nttdata.account.model.AccountRequest;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class AccountMapper {

    public Account getAccountOfAccountEntity(AccountEntity entity) {
        Account account = new Account();
        account.setId(entity.getId());
        account.setAccountNumber(entity.getNumeroCuenta());
        account.setAccountNumberCCI(entity.getNumeroCuentaCci());
        account.setHolder(entity.getTitularCuenta());
        account.setTypeAccount(Account.TypeAccountEnum.fromValue(entity.getTipo().toUpperCase()));
        account.setBalance(entity.getSaldo());
        account.setMaintenanceCommission(entity.getComisionMantenimiento());
        account.setLimitMovement(entity.getLimiteMovimientos());
        account.setOpeningDate(this.convertToOffsetDateTime(entity.getFechaApertura()));
        return account;
    }

    public AccountEntity getAccountEntityOfAccount(Account model) {
        return AccountEntity.builder()
                .id(model.getId())
                .numeroCuenta(model.getAccountNumber())
                .numeroCuentaCci(model.getAccountNumberCCI())
                .titularCuenta(model.getHolder())
                .tipo(model.getTypeAccount().getValue())
                .saldo(model.getBalance())
                .comisionMantenimiento(model.getMaintenanceCommission())
                .limiteMovimientos(model.getLimitMovement())
                .fechaApertura(model.getOpeningDate().toLocalDateTime())
                .fechaCierre(model.getClosingDate().toLocalDateTime())
                .build();
    }

    public AccountEntity getAccountEntityOfAccountRequest(AccountRequest request) {
        return AccountEntity.builder()
                .numeroCuenta(request.getAccountNumber())
                .numeroCuentaCci(request.getAccountNumberCCI())
                .titularCuenta(request.getHolder())
                .tipo(request.getTypeAccount().getValue())
                .saldo(request.getBalance())
                .comisionMantenimiento(request.getMaintenanceCommission())
                .limiteMovimientos(request.getLimitMovement())
                .fechaApertura(request.getOpeningDate().toLocalDateTime())
                .build();
    }

    private OffsetDateTime convertToOffsetDateTime(LocalDateTime localDateTime) {
        ZoneOffset offset = ZoneOffset.of("+00:00");
        return localDateTime.atOffset(offset);
    }
}
