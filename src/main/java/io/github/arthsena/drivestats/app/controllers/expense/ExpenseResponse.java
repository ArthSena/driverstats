package io.github.arthsena.drivestats.app.controllers.expense;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.github.arthsena.drivestats.app.controllers.category.CategoryResponse;
import io.github.arthsena.drivestats.domain.models.Expense;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ExpenseResponse {

    @JsonRootName("expense")
    public static class Single{

        public final UUID id;
        public final double amount;
        public final String description;
        public final String date;
        public CategoryResponse.Single category;

        public Single(Expense expense) {
            this.id = expense.getId();
            this.amount = expense.getAmount();
            this.description = expense.getDescription();
            this.date = expense.getDate().toString();
            if(expense.getCategory() != null) this.category = new CategoryResponse.Single(expense.getCategory());
        }
    }

    @JsonRootName("expenses")
    public static class Multiple {
        public final List<Single> list;
        public double totalAmount;
        public final int count;

        public Multiple(List<Expense> expenses){
            this.list = expenses.stream().map(Single::new).collect(Collectors.toList());
            this.list.forEach(r -> this.totalAmount += r.amount);
            this.count = expenses.size();
        }
    }
}
