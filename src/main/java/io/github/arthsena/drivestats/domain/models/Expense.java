package io.github.arthsena.drivestats.domain.models;

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

    @Data
    @AllArgsConstructor
    public static class Category {
        private long id;
        private String name;
    }
}
