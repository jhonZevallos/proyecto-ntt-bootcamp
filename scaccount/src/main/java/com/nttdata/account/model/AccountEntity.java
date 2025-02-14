package com.nttdata.account.model;

import lombok.Builder;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "account")
public class AccountEntity {

    @BsonId
    private String id;
    private String titularCuenta;
    private String tipo;
    private BigDecimal saldo;
    private BigDecimal comisionMantenimiento;
    private Integer limiteMovimientos;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;
}
