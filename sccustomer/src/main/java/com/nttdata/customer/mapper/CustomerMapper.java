package com.nttdata.customer.mapper;

import com.nttdata.customer.model.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public Customer getCustomerOfCustomerEntity (CustomerEntity entity) {

        List<CustomerHolders> holders = entity.getTitulares() != null
                && !entity.getTitulares().isEmpty()
                ? entity.getTitulares().stream()
                .map(data -> {
                    CustomerHolders h = new CustomerHolders();
                    h.setName(data.getNombre());
                    h.setLastname(data.getApellido());
                    h.setDni(data.getDni());
                    h.setPhone(data.getTelefono());
                    h.setEmail(data.getCorreo());
                    return h;
                }).toList() : new ArrayList<>();

        List<CustomerHolders> signatories = entity.getFirmantesAutorizados() != null
                && !entity.getFirmantesAutorizados().isEmpty()
                ? entity.getFirmantesAutorizados().stream()
                .map(data -> {
                    CustomerHolders h = new CustomerHolders();
                    h.setName(data.getNombre());
                    h.setLastname(data.getApellido());
                    h.setDni(data.getDni());
                    h.setPhone(data.getTelefono());
                    h.setEmail(data.getCorreo());
                    return h;
                }).toList() : new ArrayList<>();

        Customer customer = new Customer();
        customer.setId(entity.getId());
        customer.setDirection(entity.getDireccion());
        customer.setEmail(entity.getCorreo());
        customer.setName(entity.getNombre());
        customer.setPhone(entity.getTelefono());
        customer.setType(entity.getTipo());
        customer.setLastname(entity.getApellido());
        customer.setDni(entity.getDni());
        customer.setHolders(holders);
        customer.setSignatories(signatories);
        return customer;
    }

    public CustomerEntity getCustomerEntityOfCustomer (Customer model) {

        if(model.getType().equals("empresarial")) {

            List<Holder> titulares = model.getHolders().stream()
                    .map(data -> Holder.builder()
                            .nombre(data.getName())
                            .apellido(data.getLastname())
                            .dni(data.getDni())
                            .telefono(data.getPhone())
                            .correo(data.getEmail())
                            .build())
                    .toList();

            List<Holder> firmantes = model.getSignatories() != null
                    ? model.getSignatories().stream()
                    .map(data -> Holder.builder()
                            .nombre(data.getName())
                            .apellido(data.getLastname())
                            .dni(data.getDni())
                            .telefono(data.getPhone())
                            .correo(data.getEmail())
                            .build())
                    .toList()
                    : new ArrayList<>();

            return CustomerEntity.builder()
                    .id(model.getId())
                    .nombre(model.getName())
                    .apellido(model.getLastname())
                    .direccion(model.getDirection())
                    .correo(model.getEmail())
                    .telefono(model.getPhone())
                    .dni(model.getDni())
                    .tipo(model.getType())
                    .fechaModificacion(LocalDateTime.now())
                    .firmantesAutorizados(firmantes)
                    .titulares(titulares)
                    .build();
        }

        return CustomerEntity.builder()
                .id(model.getId())
                .nombre(model.getName())
                .apellido(model.getLastname())
                .direccion(model.getDirection())
                .correo(model.getEmail())
                .telefono(model.getPhone())
                .dni(model.getDni())
                .tipo(model.getType())
                .fechaModificacion(LocalDateTime.now())
                .build();
    }

    public CustomerEntity getCustomerEntityOfCustomerRequest (CustomerRequest request) {

        if(request.getType().equals("empresarial")) {

            List<Holder> titulares = request.getHolders().stream()
                    .map(data -> Holder.builder()
                            .nombre(data.getName())
                            .apellido(data.getLastname())
                            .dni(data.getDni())
                            .telefono(data.getPhone())
                            .correo(data.getEmail())
                            .build())
                    .collect(Collectors.toList());

            List<Holder> firmantes = request.getSignatories() != null
                    ? request.getSignatories().stream()
                    .map(data -> Holder.builder()
                            .nombre(data.getName())
                            .apellido(data.getLastname())
                            .dni(data.getDni())
                            .telefono(data.getPhone())
                            .correo(data.getEmail())
                            .build())
                    .collect(Collectors.toList())
                    : new ArrayList<>();

            return CustomerEntity.builder()
                    .nombre(request.getName())
                    .apellido(request.getLastname())
                    .direccion(request.getDirection())
                    .correo(request.getEmail())
                    .telefono(request.getPhone())
                    .dni(request.getDni())
                    .tipo(request.getType())
                    .fechaCreacion(LocalDateTime.now())
                    .firmantesAutorizados(firmantes)
                    .titulares(titulares)
                    .build();
        }

        return CustomerEntity.builder()
                .nombre(request.getName())
                .apellido(request.getLastname())
                .direccion(request.getDirection())
                .correo(request.getEmail())
                .telefono(request.getPhone())
                .dni(request.getDni())
                .tipo(request.getType())
                .fechaCreacion(LocalDateTime.now())
                .build();
    }
}
