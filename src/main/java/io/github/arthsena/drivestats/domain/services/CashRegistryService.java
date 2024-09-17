package io.github.arthsena.drivestats.domain.services;

import io.github.arthsena.drivestats.app.controllers.registry.RegistryRequest;
import io.github.arthsena.drivestats.domain.exceptions.NotFoundException;
import io.github.arthsena.drivestats.domain.exceptions.UnauthorizedException;
import io.github.arthsena.drivestats.domain.models.Registry;
import io.github.arthsena.drivestats.domain.models.User;
import io.github.arthsena.drivestats.infra.database.entities.RegistryEntity;
import io.github.arthsena.drivestats.infra.database.repositories.UserRepository;
import io.github.arthsena.drivestats.infra.exception.ExceptionType;
import io.github.arthsena.drivestats.infra.security.Subject;
import io.github.arthsena.drivestats.infra.database.repositories.RegistryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CashRegistryService {

    @Inject UserRepository users;
    @Inject RegistryRepository registries;

    public Registry create(Subject subject, RegistryRequest.Create request) {
        if(!users.existsId(subject.getId())) throw new NotFoundException(ExceptionType.INVALID_SUBJECT);
        return new Registry(registries.create(subject.getId(), request.getInitialMileage()));
    }

    public List<Registry> all(Subject subject) {
        if (!users.existsId(subject.getId())) throw new NotFoundException(ExceptionType.INVALID_SUBJECT);
        return registries.findByOwnerId(subject.getId()).stream().map(Registry::new).sorted(Comparator.comparing(Registry::getCreatedAt).reversed()).toList();
    }

    public List<Registry> searchByDate(Subject subject, String minPeriod, String maxPeriod) {
        if (!users.existsId(subject.getId())) throw new NotFoundException(ExceptionType.INVALID_SUBJECT);

        return registries.findByOwnerId(subject.getId()).stream()
                .map(Registry::new)
                .filter(registry -> {
                    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    var createdAt = registry.getCreatedAt();
                    var isMinPeriod = minPeriod == null || LocalDate.parse(minPeriod, formatter).atStartOfDay().isBefore(createdAt);
                    var isMaxPeriod = maxPeriod == null || LocalDate.parse(maxPeriod, formatter).plusDays(1).atStartOfDay().isAfter(createdAt);
                    return isMinPeriod && isMaxPeriod;
                }).toList();
    }

    public List<Registry> searchMonthly(Subject subject) {
        if(!users.existsId(subject.getId())) throw new NotFoundException(ExceptionType.INVALID_SUBJECT);

        return registries.findByOwnerId(subject.getId()).stream()
                .map(Registry::new)
                .filter(registry -> LocalDate.now().withDayOfMonth(1).atStartOfDay().isBefore(registry.getCreatedAt()))
                .toList();
    }

    public Registry close(Subject subject, UUID registryId, RegistryRequest.Close request) {
        validatedEntity(subject, registryId);

        return new Registry(registries.close(registryId, request.getBilled(), request.getFinalMileage(), request.getTrips()));
    }

    public Registry reopen(Subject subject, UUID registryId) {
        validatedEntity(subject, registryId);

        return new Registry(registries.reopen(registryId));
    }

    public Registry update(Subject subject, UUID registryId, RegistryRequest.Update request) {
        validatedEntity(subject, registryId);

        return new Registry(registries.update(registryId, request.getBilled(), request.getInitialMileage(), request.getFinalMileage(), request.getTrips()));
    }
    public Registry getById(Subject subject, UUID registryId) {
        return new Registry(validatedEntity(subject, registryId));
    }

    public void delete(Subject subject, UUID registryId) {
        registries.delete(validatedEntity(subject, registryId));
    }

    private RegistryEntity validatedEntity(Subject subject, UUID registryId) {
        if(!users.existsId(subject.getId()))
            throw new NotFoundException(ExceptionType.INVALID_SUBJECT);

        if(!registries.existsById(registryId))
            throw new NotFoundException(ExceptionType.ENTITY_NOT_FOUND.withPattern("Registry '%s'".formatted(registryId)));

        var entity = registries.findById(registryId);

        if(!entity.getOwner().getId().equals(subject.getId()) || subject.getRole() == User.Role.ADMIN)
            throw new UnauthorizedException(ExceptionType.UNAUTHORIZED.withPattern("Current subject '%s'".formatted(subject.getId())).withBody("%s is not authorized to access this registry"));

        return entity;
    }


}
