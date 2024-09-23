package io.github.arthsena.drivestats.infra.database.repositories;

import io.github.arthsena.drivestats.infra.database.entities.RegistryEntity;
import io.github.arthsena.drivestats.infra.database.entities.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Transactional
@ApplicationScoped
public class RegistryRepository implements PanacheRepositoryBase<RegistryEntity, UUID> {

    public RegistryEntity create(UUID ownerId, double initialMileage) {
        var owner = getEntityManager().find(UserEntity.class, ownerId);
        RegistryEntity entity = new RegistryEntity(owner, initialMileage);
        persistAndFlush(entity);
        return entity;
    }

    public RegistryEntity close(UUID registryId, double billed, double finalMileage, int trips) {
        var entity = findById(registryId);
        entity.close(billed, finalMileage, trips);
        entity.persistAndFlush();
        return entity;
    }

    public RegistryEntity update(UUID registryId, Double billed, Double initialMileage, Double finalMileage, Integer trips) {
        var entity = findById(registryId);

        if(initialMileage != null)     entity.setInitialMileage(initialMileage);
        if(finalMileage != null)       entity.setFinalMileage(finalMileage);
        if(billed != null)             entity.setBilled(billed);
        if(trips != null)              entity.setTrips(trips);

        entity.persistAndFlush();
        return entity;
    }

    public List<RegistryEntity> findByOwnerId(UUID ownerId) {
        return findByOwnerId(ownerId, null, null);
    }
    public List<RegistryEntity> findByOwnerId(UUID ownerId, Integer page, Integer limit) {
        PanacheQuery<RegistryEntity> query = find("owner", Sort.by("createdAt").descending(), getEntityManager().find(UserEntity.class, ownerId));

        if(page != null && limit != null)
            query.page(page, limit);

        return query.list();
    }
    public long countByOwnerId(UUID ownerId) {
        return find("owner", getEntityManager().find(UserEntity.class, ownerId)).count();
    }

    public boolean existsById(UUID id) {
        return count("id", id) > 0;
    }

    public RegistryEntity reopen(UUID registryId) {
        var entity = findById(registryId);
        entity.reopen();
        entity.persistAndFlush();
        return entity;
    }


}
