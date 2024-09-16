package io.github.arthsena.drivestats.domain.models;

import io.github.arthsena.drivestats.infra.database.entities.RegistryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Registry {

    private UUID id;

    private User owner;

    private double initialMileage;
    private double finalMileage;

    private double billed;
    private int trips;

    private State state;

    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

    public Registry(User owner, double initialMileage){
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.initialMileage = initialMileage;
        this.finalMileage = 0;
        this.billed = 0;
        this.trips = 0;
        this.state = State.OPEN;
        this.createdAt = LocalDateTime.now();
    }

    public Registry(RegistryEntity entity) {
        this(entity.getId(), new User(entity.getOwner()), entity.getInitialMileage(), entity.getFinalMileage(), entity.getBilled(), entity.getTrips(), entity.getState(), entity.getCreatedAt(), entity.getClosedAt());
    }

    public enum State {
        OPEN,
        CLOSED
    }

}
