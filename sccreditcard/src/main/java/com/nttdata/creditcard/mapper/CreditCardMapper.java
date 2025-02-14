package com.nttdata.creditcard.mapper;

import com.nttdata.creditcard.model.CreditCard;
import com.nttdata.creditcard.model.CreditCardEntity;
import com.nttdata.creditcard.model.CreditCardRequest;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class CreditCardMapper {

    public CreditCard getCreditCardOfCreditCardEntity(CreditCardEntity entity) {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(entity.getId());
        creditCard.setCustomerId(entity.getCustomerId());
        creditCard.setTypeCredit(CreditCard.TypeCreditEnum
                .valueOf(entity.getTypeCredit().toUpperCase()));
        creditCard.setCardNumber(entity.getCardNumber());
        creditCard.setExpirationDate(entity.getExpirationDate());
        creditCard.setCreditLimit(entity.getCreditLimit());
        creditCard.setCurrentBalance(entity.getCurrentBalance());
        return creditCard;
    }

    public CreditCardEntity getCreditCardEntityOfCreditCard(CreditCard model) {
        return CreditCardEntity.builder()
                .id(model.getId())
                .customerId(model.getCustomerId())
                .typeCredit(model.getTypeCredit().getValue())
                .cardNumber(model.getCardNumber())
                .expirationDate(model.getExpirationDate())
                .creditLimit(model.getCreditLimit())
                .currentBalance(model.getCurrentBalance())
                .changeDate(LocalDateTime.now())
                .build();
    }

    public CreditCardEntity getCreditCardEntityOfCreditCardRequest(CreditCardRequest request) {
        return CreditCardEntity.builder()
                .customerId(request.getCustomerId())
                .typeCredit(request.getTypeCredit().getValue())
                .cardNumber(request.getCardNumber())
                .expirationDate(request.getExpirationDate())
                .creditLimit(request.getCreditLimit())
                .currentBalance(request.getCurrentBalance())
                .creationDate(LocalDateTime.now())
                .build();
    }
}
