package io.github.arthsena.drivestats.domain.models;

import io.github.arthsena.drivestats.infra.database.entities.ExpenseCategoryEntity;
import io.github.arthsena.drivestats.infra.database.entities.ExpenseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Expense {

    private UUID id;
    private User owner;
    private String description;
    private double amount;
    private LocalDateTime date;
    private Category category;

    public Expense(User owner, String description, double amount, Category category) {
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.description = description;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.category = category;
    }

    public Expense(ExpenseEntity entity) {
        this(entity.getId(), new User(entity.getOwner()), entity.getDescription(), entity.getAmount(), entity.getDate(), (entity.getCategory() != null ? new Category(entity.getCategory()) : null));
    }

    @Data
    @AllArgsConstructor
    public static class Category {

        private UUID id;
        private String name;
        private String color;
        private User owner;

        public Category(ExpenseCategoryEntity entity) {
            this(entity.getId(), entity.getName(), entity.getColor(), new User(entity.getOwner()));
        }
    }
}
