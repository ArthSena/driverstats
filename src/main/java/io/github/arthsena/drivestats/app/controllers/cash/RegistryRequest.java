package io.github.arthsena.drivestats.app.controllers.cash;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class RegistryRequest {

    @Getter
    public static class Create {
        @NotNull(message = "parameter 'initialBalance' must be not null")
        private double initialBalance;

        @NotNull(message = "parameter 'initialMileage' must be not null")
        private double initialMileage;
    }

    @Getter
    public static class Close {
        @NotNull(message = "parameter 'earnedBalance' must be not null")
        private double earnedBalance;

        @NotNull(message = "parameter 'finalMileage' must be not null")
        private double finalMileage;

        @NotNull(message = "parameter 'totalTrips' must be not null")
        private int totalTrips;
    }

    @Getter
    public static class Update {
        private Double initialBalance;
        private Double initialMileage;
        private Double earnedBalance;
        private Double finalMileage;
        private Integer totalTrips;
    }
}
