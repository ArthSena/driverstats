package io.github.arthsena.drivestats.infra.database.entities;

import io.github.arthsena.drivestats.domain.models.User;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "drivestats-users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserEntity extends PanacheEntityBase {

    @Id private UUID id;

    private String name;
    @Column(unique = true) private String email;
    private String password;

    private User.Role role;

     @OneToMany(targetEntity= RegistryEntity.class, cascade= CascadeType.ALL, mappedBy="owner")
     private Set<RegistryEntity> registries;

    @OneToMany(targetEntity= ExpenseEntity.class, cascade= CascadeType.ALL, mappedBy="owner")
    private Set<ExpenseEntity> expenses;

    @OneToMany(targetEntity= ExpenseCategoryEntity.class, cascade= CascadeType.ALL, mappedBy="owner")
    private Set<ExpenseCategoryEntity> categories;

    public UserEntity(String name, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = User.Role.USER;
    }
}