package io.github.arthsena.drivestats.app.controllers.registry;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class RegistryRequest {

    @Getter
    public static class Create {
        @NotNull(message = "parameter 'initialMileage' must be not null")
        private double initialMileage;
    }

    @Getter
    public static class Close {
        @NotNull(message = "parameter 'earnedBalance' must be not null")
        private double billed;

        @NotNull(message = "parameter 'finalMileage' must be not null")
        private double finalMileage;

        @NotNull(message = "parameter 'totalTrips' must be not null")
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
