package com.nttdata.creditcard.model;

import lombok.Builder;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "creditcard")
public class CreditCardEntity {

    @BsonId
    private String id;
    private String customerId;
    private String typeCredit;
    private String cardNumber;
    private LocalDate expirationDate;
    private BigDecimal creditLimit;
    private BigDecimal currentBalance;
    private LocalDateTime creationDate;
    private LocalDateTime changeDate;
}
