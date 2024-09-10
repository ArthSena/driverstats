package io.github.arthsena.drivestats.domain.models;

import io.github.arthsena.drivestats.infra.database.entities.CashRegistryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CashRegistry {

    private UUID id;

    private User owner;

    private double initialBalance;
    private double initialMileage;

    private double earnedBalance;
    private double finalMileage;
    private int totalTrips;

    private State state;

    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

    public CashRegistry(User owner, double initialBalance, double initialMileage){
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.initialBalance = initialBalance;
        this.initialMileage = initialMileage;
        this.earnedBalance = 0;
        this.finalMileage = 0;
        this.totalTrips = 0;
        this.state = State.OPEN;
        this.createdAt = LocalDateTime.now();
    }

    public CashRegistry(CashRegistryEntity entity) {
        this(entity.getId(), new User(entity.getOwner()), entity.getInitialBalance(), entity.getInitialMileage(), entity.getEarnedBalance(), entity.getFinalMileage(), entity.getTotalTrips(), entity.getState(), entity.getCreatedAt(), entity.getClosedAt());
    }

    public enum State {
        OPEN,
        CLOSED
    }

}
