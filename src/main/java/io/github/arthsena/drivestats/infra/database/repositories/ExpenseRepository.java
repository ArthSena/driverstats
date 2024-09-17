package io.github.arthsena.drivestats.infra.database.repositories;

import io.github.arthsena.drivestats.infra.database.entities.ExpenseCategoryEntity;
import io.github.arthsena.drivestats.infra.database.entities.ExpenseEntity;
import io.github.arthsena.drivestats.infra.database.entities.RegistryEntity;
import io.github.arthsena.drivestats.infra.database.entities.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
@Transactional
@ApplicationScoped
public class ExpenseRepository implements PanacheRepositoryBase<ExpenseEntity, UUID> {
    public ExpenseEntity create(UUID ownerId, UUID categoryId, double amount,  String description) {
        var owner = getEntityManager().find(UserEntity.class, ownerId);
        var category = categoryId != null? getEntityManager().find(ExpenseCategoryEntity.class, categoryId) : null;

        ExpenseEntity entity = new ExpenseEntity(owner, category, amount, description);
        persistAndFlush(entity);
        return entity;
    }

    public List<ExpenseEntity> findByOwnerId(UUID ownerId) {
        return find("owner", getEntityManager().find(UserEntity.class, ownerId)).list();
    }

    public boolean existsById(UUID id) {
        return count("id", id) > 0;
    }

    public ExpenseEntity update(UUID expenseId, UUID categoryId, String description, Double amount) {
        var entity = findById(expenseId);

        if(categoryId != null)          entity.setCategory(getEntityManager().find(ExpenseCategoryEntity.class, categoryId));
        if(description != null)         entity.setDescription(description);
        if(amount != null)              entity.setAmount(amount);

        entity.persistAndFlush();
        return entity;
    }
}
