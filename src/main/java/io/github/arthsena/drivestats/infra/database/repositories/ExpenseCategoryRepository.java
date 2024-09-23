package io.github.arthsena.drivestats.infra.database.repositories;

import io.github.arthsena.drivestats.infra.database.entities.ExpenseCategoryEntity;
import io.github.arthsena.drivestats.infra.database.entities.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@ApplicationScoped
public class ExpenseCategoryRepository implements PanacheRepositoryBase<ExpenseCategoryEntity, UUID> {

    @Inject
    ExpenseRepository expenses;

    public List<ExpenseCategoryEntity> findByOwnerId(UUID ownerId) {
        return find("owner", getEntityManager().find(UserEntity.class, ownerId)).list();
    }

    public ExpenseCategoryEntity create(UUID ownerId, String name) {
        ExpenseCategoryEntity entity = new ExpenseCategoryEntity(getEntityManager().find(UserEntity.class, ownerId), name);
        persistAndFlush(entity);
        return entity;
    }

    public boolean existsById(UUID id) {
        return count("id", id) > 0;
    }

    public ExpenseCategoryEntity update(UUID id, String name) {
        ExpenseCategoryEntity entity = findById(id);
        entity.setName(name);
        entity.persistAndFlush();
        return entity;
    }

    @Override
    public void delete(ExpenseCategoryEntity entity) {
        var exp = expenses.findByCategoryId(entity.getId());

        exp.forEach((e) -> {
            e.setCategory(null);
            e.persistAndFlush();
        });

        PanacheRepositoryBase.super.delete(entity);
    }
}
