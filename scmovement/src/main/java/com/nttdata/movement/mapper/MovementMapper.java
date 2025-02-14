package com.nttdata.movement.mapper;

import com.nttdata.movement.model.Movement;
import com.nttdata.movement.model.MovementEntity;
import com.nttdata.movement.model.MovementRequest;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class MovementMapper {

    public Movement getMovementOfMovementEntity(MovementEntity entity) {
        Movement movement = new Movement();
        movement.setId(entity.getId());
        movement.setIdProduct(entity.getIdProduct());
        movement.setDate(this.convertToOffsetDateTime(entity.getDate()));
        movement.setType(Movement.TypeEnum.valueOf(entity.getType().toUpperCase()));
        movement.setAmount(entity.getAmount());
        return movement;
    }

    public MovementEntity getMovementEntityOfMovement(Movement model) {
        return MovementEntity.builder()
                .id(model.getId())
                .idProduct(model.getIdProduct())
                .type(model.getType().getValue())
                .amount(model.getAmount())
                .build();
    }

    public MovementEntity getMovementEntityOfMovementRequest(MovementRequest request) {
        return MovementEntity.builder()
                .idProduct(request.getIdProduct())
                .date(LocalDateTime.now())
                .type(request.getType().getValue())
                .amount(request.getAmount())
                .build();
    }

    private OffsetDateTime convertToOffsetDateTime(LocalDateTime localDateTime) {
        ZoneOffset offset = ZoneOffset.of("+00:00");
        return localDateTime.atOffset(offset);
    }
}