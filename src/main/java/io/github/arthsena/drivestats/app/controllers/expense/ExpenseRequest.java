package io.github.arthsena.drivestats.app.controllers.expense;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

public class ExpenseRequest {

    @Getter
    public static class Create {
        @NotNull(message = "parameter 'amount' must be not null")
        private double amount;

        @NotBlank(message = "parameter 'amount' must be not blank")
        private String description;

        private UUID categoryId;
    }

    @Getter
    public static class Update {
        private Double amount;
        private String description;
        private UUID categoryId;
    }
}
