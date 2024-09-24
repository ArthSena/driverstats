package io.github.arthsena.drivestats.app.controllers.category;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.github.arthsena.drivestats.domain.models.Expense;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CategoryResponse {

    @JsonRootName("category")
    public static class Single{

        public final UUID id;
        public final String name;
        public final String color;

        public Single(Expense.Category category) {
            this.id = category.getId();
            this.name = category.getName();
            this.color = category.getColor();
        }
    }

    @JsonRootName("categories")
    public static class Multiple {
        public final List<Single> list;
        public final int count;

        public Multiple(List<Expense.Category> categories){
            this.list = categories.stream().map(Single::new).collect(Collectors.toList());
            this.count = categories.size();
        }
    }
}