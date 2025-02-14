package com.nttdata.customer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Holder {

    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String correo;
}
