package io.github.arthsena.drivestats.app.controllers.registry;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.github.arthsena.drivestats.domain.models.Registry;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RegistryResponse {

    @JsonRootName("registry")
    public static class Single{
        public final UUID id;

        public final double initialMileage;
        public final double finalMileage;

        public final double billed;
        public final int trips;

        public final Registry.State state;

        public String createdAt;
        public String closedAt;

        public Single(Registry registry) {
            this.id = registry.getId();
            this.initialMileage = registry.getInitialMileage();
            this.finalMileage = registry.getFinalMileage();
            this.billed = registry.getBilled();
            this.trips = registry.getTrips();
            this.state = registry.getState();
            if(registry.getCreatedAt() != null) this.createdAt = registry.getCreatedAt().toString();
            if(registry.getClosedAt() != null) this.closedAt = registry.getClosedAt().toString();
        }
    }

    @JsonRootName("registries")
    public static class Multiple {
        public final List<Single> list;
        public double totalBalance;
        public double totalMileage;
        public int totalTrips;
        public final int count;

        public Multiple(List<Registry> registries){
            this.list = registries.stream().map(Single::new).collect(Collectors.toList());

            this.list.forEach(r -> {
                this.totalBalance += r.billed;
                this.totalMileage += r.finalMileage;
                this.totalTrips += r.trips;
            });

            this.count = registries.size();
        }
    }

}
