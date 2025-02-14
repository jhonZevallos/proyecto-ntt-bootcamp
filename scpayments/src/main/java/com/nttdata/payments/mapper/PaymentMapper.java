package com.nttdata.payments.mapper;

import com.nttdata.payments.model.Payment;
import com.nttdata.payments.model.PaymentEntity;
import com.nttdata.payments.model.PaymentRequest;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class PaymentMapper {

    public Payment getPaymentOfPaymentEntity(PaymentEntity entity) {
        Payment payment = new Payment();
        payment.setId(entity.getId());
        payment.setIdProduct(entity.getIdProduct());
        payment.setDate(this.convertToOffsetDateTime(entity.getDate()));
        payment.setAmount(entity.getAmount());
        return payment;
    }

    public PaymentEntity getPaymentEntityOfPayment(Payment model) {
        return PaymentEntity.builder()
                .id(model.getId())
                .idProduct(model.getIdProduct())
                .amount(model.getAmount())
                .build();
    }

    public PaymentEntity getPaymentEntityOfPaymentRequest(PaymentRequest request) {
        return PaymentEntity.builder()
                .idProduct(request.getIdProduct())
                .date(LocalDateTime.now())
                .amount(request.getAmount())
                .build();
    }

    private OffsetDateTime convertToOffsetDateTime(LocalDateTime localDateTime) {
        ZoneOffset offset = ZoneOffset.of("+00:00");
        return localDateTime.atOffset(offset);
    }
}