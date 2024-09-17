package io.github.arthsena.drivestats.infra.database.entities;

import io.github.arthsena.drivestats.domain.models.Registry;
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
public class RegistryEntity extends PanacheEntityBase {

    @Id private UUID id;

    @ManyToOne
    @JoinColumn(name="ownerId", nullable=false)
    private UserEntity owner;

    private double initialMileage;
    private double finalMileage;
    private double billed;
    private int trips;

    private Registry.State state;

    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

    public RegistryEntity(UserEntity owner, double initialMileage) {
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.initialMileage = initialMileage;
        this.finalMileage = 0;
        this.billed = 0;
        this.trips = 0;
        this.state = Registry.State.OPEN;
        this.createdAt = LocalDateTime.now();
    }

    public void close(double billed, double finalMileage, int trips) {
        if(this.state != null && this.state == Registry.State.CLOSED)
            return;

        this.state = Registry.State.CLOSED;
        this.closedAt = LocalDateTime.now();
        this.billed = billed;
        this.finalMileage = finalMileage;
        this.trips = trips;
    }

    public void reopen() {
        if(this.state != null && this.state == Registry.State.OPEN)
            return;

        this.state = Registry.State.OPEN;
        this.closedAt = null;
    }
}
