package com.nttdata.payments.model;

import lombok.Builder;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "payment")
public class PaymentEntity {

    @BsonId
    private String id;
    private String idProduct;
    private LocalDateTime date;
    private BigDecimal amount;
}
