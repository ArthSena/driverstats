package io.github.arthsena.drivestats.app.controllers.cash;

import io.github.arthsena.drivestats.app.responses.UserResponse;
import io.github.arthsena.drivestats.domain.models.CashRegistry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RegistryResponse {

    public static class Single{
        public UUID id;
        public UserResponse owner;

        public double initialBalance;
        public double initialMileage;

        public double earnedBalance;
        public double finalMileage;
        public int totalTrips;

        public CashRegistry.State state;

        public String createdAt;
        public String closedAt;

        public Single(CashRegistry registry) {
            this.id = registry.getId();
            this.owner = new UserResponse(registry.getOwner());
            this.initialBalance = registry.getInitialBalance();
            this.initialMileage = registry.getInitialMileage();
            this.earnedBalance = registry.getEarnedBalance();
            this.finalMileage = registry.getFinalMileage();
            this.totalTrips = registry.getTotalTrips();
            this.state = registry.getState();
            if(registry.getCreatedAt() != null) this.createdAt = registry.getCreatedAt().toString();
            if(registry.getClosedAt() != null) this.closedAt = registry.getClosedAt().toString();
        }
    }

    public static class Multiple {
        public List<Single> registries;
        public double totalBalance;
        public double totalMileage;
        public int totalTrips;
        public int count;

        public Multiple(List<CashRegistry> registries){
            this.registries = registries.stream().map(Single::new).collect(Collectors.toList());

            this.registries.forEach(r -> {
                this.totalBalance += r.earnedBalance;
                this.totalMileage += r.finalMileage;
                this.totalTrips += r.totalTrips;
            });

            this.count = registries.size();
        }
    }

}
