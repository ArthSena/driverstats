package io.github.arthsena.drivestats.domain.services;

import io.github.arthsena.drivestats.app.controllers.cash.RegistryRequest;
import io.github.arthsena.drivestats.domain.exceptions.NotFoundException;
import io.github.arthsena.drivestats.domain.exceptions.UnauthorizedException;
import io.github.arthsena.drivestats.domain.models.CashRegistry;
import io.github.arthsena.drivestats.domain.models.User;
import io.github.arthsena.drivestats.infra.database.entities.CashRegistryEntity;
import io.github.arthsena.drivestats.infra.database.repositories.UserRepository;
import io.github.arthsena.drivestats.infra.exception.ExceptionType;
import io.github.arthsena.drivestats.infra.security.Subject;
import io.github.arthsena.drivestats.infra.database.repositories.CashRegistryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CashRegistryService {

    @Inject UserRepository users;
    @Inject CashRegistryRepository registries;

    public CashRegistry create(Subject subject, RegistryRequest.Create request) throws NotFoundException {
        if(!users.existsId(subject.getId())) throw new NotFoundException(ExceptionType.INVALID_SUBJECT);
        return new CashRegistry(registries.create(subject.getId(), request.getInitialBalance(), request.getInitialMileage()));
    }

    public List<CashRegistry> searchByDate(Subject subject, String minPeriod, String maxPeriod) {
        if (!users.existsId(subject.getId())) throw new NotFoundException(ExceptionType.INVALID_SUBJECT);

        return registries.findByOwnerId(subject.getId()).stream()
                .map(CashRegistry::new)
                .filter(registry -> {
                    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    var createdAt = registry.getCreatedAt();
                    var isMinPeriod = minPeriod == null || LocalDate.parse(minPeriod, formatter).atStartOfDay().isBefore(createdAt);
                    var isMaxPeriod = maxPeriod == null || LocalDate.parse(maxPeriod, formatter).plusDays(1).atStartOfDay().isAfter(createdAt);
                    return isMinPeriod && isMaxPeriod;
                }).toList();
    }

    public List<CashRegistry> searchMonthly(Subject subject) {
        if(!users.existsId(subject.getId())) throw new NotFoundException(ExceptionType.INVALID_SUBJECT);

        return registries.findByOwnerId(subject.getId()).stream()
                .map(CashRegistry::new)
                .filter(registry -> LocalDate.now().withDayOfMonth(1).atStartOfDay().isBefore(registry.getCreatedAt()))
                .toList();
    }

    public CashRegistry close(Subject subject, UUID registryId, RegistryRequest.Close request) {
        validatedEntity(subject, registryId);

        return new CashRegistry(registries.close(registryId, request.getEarnedBalance(), request.getFinalMileage(), request.getTotalTrips()));
    }

    public CashRegistry update(Subject subject, UUID registryId, RegistryRequest.Update request) {
        var entity = validatedEntity(subject, registryId);

        if(request.getInitialBalance()  != null)    entity.setInitialBalance(request.getInitialBalance());
        if(request.getInitialMileage() != null)     entity.setInitialMileage(request.getInitialMileage());
        if(request.getEarnedBalance() != null)      entity.setEarnedBalance(request.getEarnedBalance());
        if(request.getFinalMileage() != null)       entity.setFinalMileage(request.getFinalMileage());
        if(request.getTotalTrips() != null)         entity.setTotalTrips(request.getTotalTrips());

        return new CashRegistry(entity);
    }
    public CashRegistry getById(Subject subject, UUID registryId) {
        return new CashRegistry(validatedEntity(subject, registryId));
    }

    public void delete(Subject subject, UUID registryId) {
        registries.delete(validatedEntity(subject, registryId));
    }

    private CashRegistryEntity validatedEntity(Subject subject, UUID registryId) {
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
