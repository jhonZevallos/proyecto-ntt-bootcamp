package com.nttdata.credit.mapper;

import com.nttdata.credit.model.Credit;
import com.nttdata.credit.model.CreditEntity;
import com.nttdata.credit.model.CreditRequest;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class CreditMapper {

    public Credit getCreditOfCreditEntity(CreditEntity entity) {
        Credit credit = new Credit();
        credit.setId(entity.getId());
        credit.setCustomerId(entity.getCustomerId());
        credit.setTypeCredit(Credit.TypeCreditEnum.valueOf(entity.getTypeCredit().toUpperCase()));
        credit.setAmount(entity.getAmount());
        credit.setApprovalDate(this.convertToOffsetDateTime(entity.getApprovalDate()));
        credit.setInterest(entity.getInterest());
        credit.setQuota(entity.getQuota());
        credit.setState(Credit.StateEnum.valueOf(entity.getState().toUpperCase()));
        return credit;
    }

    public CreditEntity getCreditEntityOfCredit(Credit model) {
        return CreditEntity.builder()
                .id(model.getId())
                .customerId(model.getCustomerId())
                .typeCredit(model.getTypeCredit().getValue())
                .amount(model.getAmount())
                .approvalDate(model.getApprovalDate().toLocalDateTime())
                .interest(model.getInterest())
                .quota(model.getQuota())
                .state(model.getState().getValue())
                .build();
    }

    public CreditEntity getCreditEntityOfCreditRequest(CreditRequest request) {
        return CreditEntity.builder()
                .customerId(request.getCustomerId())
                .typeCredit(request.getTypeCredit().getValue())
                .amount(request.getAmount())
                .approvalDate(request.getApprovalDate().toLocalDateTime())
                .interest(request.getInterest())
                .quota(request.getQuota())
                .state(request.getState().getValue())
                .build();
    }

    private OffsetDateTime convertToOffsetDateTime(LocalDateTime localDateTime) {
        ZoneOffset offset = ZoneOffset.of("+00:00");
        return localDateTime.atOffset(offset);
    }
}
