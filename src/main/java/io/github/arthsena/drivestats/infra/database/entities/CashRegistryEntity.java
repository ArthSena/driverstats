package io.github.arthsena.drivestats.infra.database.entities;

import io.github.arthsena.drivestats.domain.models.CashRegistry;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "drivestats-registries")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CashRegistryEntity extends PanacheEntityBase {

    @Id private UUID id;

    @ManyToOne
    @JoinColumn(name="ownerId", nullable=false)
    private UserEntity owner;

    private double initialBalance;
    private double initialMileage;

    private double earnedBalance;
    private double finalMileage;
    private int totalTrips;

    private CashRegistry.State state;

    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

    public CashRegistryEntity(UserEntity owner, double initialBalance, double initialMileage) {
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.initialBalance = initialBalance;
        this.initialMileage = initialMileage;
        this.earnedBalance = 0;
        this.finalMileage = 0;
        this.totalTrips = 0;
        this.state = CashRegistry.State.OPEN;
        this.createdAt = LocalDateTime.now();
    }

    public void close(double earnedBalance, double finalMileage, int totalTrips) {
        if(this.state != null && this.state == CashRegistry.State.CLOSED)
            return;

        this.state = CashRegistry.State.CLOSED;
        this.closedAt = LocalDateTime.now();
        this.earnedBalance = earnedBalance;
        this.finalMileage = finalMileage;
        this.totalTrips = totalTrips;
    }
}
