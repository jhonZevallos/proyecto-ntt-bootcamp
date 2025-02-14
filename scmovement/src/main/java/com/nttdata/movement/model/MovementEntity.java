package com.nttdata.movement.model;

import lombok.Builder;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "movement")
public class MovementEntity {

    @BsonId
    private String id;
    private String idProduct;
    private LocalDateTime date;
    private String type;
    private BigDecimal amount;
}
