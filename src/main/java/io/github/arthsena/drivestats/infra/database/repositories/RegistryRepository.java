package io.github.arthsena.drivestats.infra.database.repositories;

import io.github.arthsena.drivestats.infra.database.entities.RegistryEntity;
import io.github.arthsena.drivestats.infra.database.entities.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

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

    public List<RegistryEntity> findByOwnerId(UUID ownerId) {
        return find("owner", getEntityManager().find(UserEntity.class, ownerId)).list();
    }

    public boolean existsById(UUID id) {
        return count("id", id) > 0;
    }
}
