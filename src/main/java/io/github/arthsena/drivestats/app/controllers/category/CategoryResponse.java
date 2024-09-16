package io.github.arthsena.drivestats.app.controllers.category;

import io.github.arthsena.drivestats.domain.models.Expense;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryResponse {
    public static class Single{

        public long id;
        public String name;

        public Single(Expense.Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }

    public static class Multiple {
        public List<Single> categories;
        public int count;

        public Multiple(List<Expense.Category> categories){
            this.categories = categories.stream().map(Single::new).collect(Collectors.toList());
            this.count = categories.size();
        }
    }
}