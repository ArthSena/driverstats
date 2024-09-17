package io.github.arthsena.drivestats.app.controllers.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class CategoryRequest {
    @Getter
    public static class Create {
        @NotBlank(message = "parameter 'name' must be not blank")
        private String name;
    }
    @Getter
    public static class Update {
        @NotBlank(message = "parameter 'name' must be not blank")
        private String name;
    }
}
