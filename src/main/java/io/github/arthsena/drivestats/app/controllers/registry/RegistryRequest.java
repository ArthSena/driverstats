package io.github.arthsena.drivestats.app.controllers.registry;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class RegistryRequest {

    @Getter
    public static class Create {
        @NotNull(message = "Initial Mileage must be not null")
        private double initialMileage;
    }

    @Getter
    public static class Close {
        @NotNull(message = "Billed must be not null")
        private double billed;

        @NotNull(message = "Final Mileage must be not null")
        private double finalMileage;

        @NotNull(message = " Trips must be not null")
        private int trips;
    }

    @Getter
    public static class Update {
        private Double billed;
        private Double initialMileage;
        private Double finalMileage;
        private Integer trips;
    }
}
