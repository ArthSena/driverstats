package io.github.arthsena.drivestats.infra.database.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "drivestats-expense-categories")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExpenseCategoryEntity extends PanacheEntityBase {

    @Id
    private UUID id;

    private String name;

    private String color;

    @ManyToOne
    @JoinColumn(name="ownerId", nullable=false)
    private UserEntity owner;

    public ExpenseCategoryEntity(UserEntity owner, String name, String color) {
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.name = name;
        this.color = color;
    }
}
