package com.nttdata.customer.model;

import lombok.Builder;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "customer")
public class CustomerEntity {

    @BsonId
    private String id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String correo;
    private String dni;
    private String tipo;
    private List<Holder> titulares;
    private List<Holder> firmantesAutorizados;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}
