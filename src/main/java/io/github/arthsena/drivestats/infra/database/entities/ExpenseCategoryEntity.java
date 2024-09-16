package io.github.arthsena.drivestats.infra.database.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "drivestats-expense-categories")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExpenseCategoryEntity extends PanacheEntityBase {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="ownerId", nullable=false)
    private UserEntity owner;
}
