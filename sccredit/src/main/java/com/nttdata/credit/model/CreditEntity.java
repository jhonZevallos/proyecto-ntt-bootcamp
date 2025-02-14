package com.nttdata.credit.model;

import lombok.Builder;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "credit")
public class CreditEntity {

    @BsonId
    private String id;
    private String customerId;
    private String typeCredit;
    private BigDecimal amount;
    private LocalDateTime approvalDate;
    private BigDecimal interest;
    private Integer quota;
    private String state;
}
