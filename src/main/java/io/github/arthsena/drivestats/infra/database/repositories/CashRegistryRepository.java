package io.github.arthsena.drivestats.infra.database.repositories;

import io.github.arthsena.drivestats.infra.database.entities.CashRegistryEntity;
import io.github.arthsena.drivestats.infra.database.entities.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@ApplicationScoped
public class CashRegistryRepository implements PanacheRepositoryBase<CashRegistryEntity, UUID> {

    public CashRegistryEntity create(UUID ownerId, double initialBalance, double initialMileage) {
        var owner = getEntityManager().find(UserEntity.class, ownerId);
        CashRegistryEntity entity = new CashRegistryEntity(owner, initialBalance, initialMileage);
        persistAndFlush(entity);
        return entity;
    }

    public CashRegistryEntity close(UUID registryId, double earnedBalance, double finalMileage, int totalTrips) {
        var entity = findById(registryId);
        entity.close(earnedBalance, finalMileage, totalTrips);
        entity.persistAndFlush();
        return entity;
    }

    public List<CashRegistryEntity> findByOwnerId(UUID ownerId) {
        return find("owner", getEntityManager().find(UserEntity.class, ownerId)).list();
    }

    public boolean existsById(UUID id) {
        return count("id", id) > 0;
    }
}
