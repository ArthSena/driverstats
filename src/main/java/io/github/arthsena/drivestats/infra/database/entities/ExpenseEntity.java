package io.github.arthsena.drivestats.infra.database.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "drivestats-expenses")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExpenseEntity extends PanacheEntityBase {

    @Id private UUID id;

    private double amount;

    private LocalDateTime date;

    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    private ExpenseCategoryEntity category;

    @ManyToOne
    @JoinColumn(name="ownerId", nullable=false)
    private UserEntity owner;

}
