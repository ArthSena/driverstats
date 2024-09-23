package io.github.arthsena.drivestats.infra.database.repositories;

import io.github.arthsena.drivestats.domain.exceptions.NotFoundException;
import io.github.arthsena.drivestats.infra.database.entities.ExpenseCategoryEntity;
import io.github.arthsena.drivestats.infra.database.entities.ExpenseEntity;
import io.github.arthsena.drivestats.infra.database.entities.RegistryEntity;
import io.github.arthsena.drivestats.infra.database.entities.UserEntity;
import io.github.arthsena.drivestats.infra.exception.ExceptionType;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
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
        ExpenseCategoryEntity category = null;

        if (categoryId != null) {
            category = getEntityManager().find(ExpenseCategoryEntity.class, categoryId);
            if (category == null) throw new NotFoundException(ExceptionType.ENTITY_NOT_FOUND.withPattern("Category '" + categoryId + "'"));
        }

        ExpenseEntity entity = new ExpenseEntity(owner, category, amount, description);
        persistAndFlush(entity);
        return entity;
    }

    public List<ExpenseEntity> findByOwnerId(UUID ownerId, Integer page, Integer limit) {
        PanacheQuery<ExpenseEntity> query = find("owner", Sort.by("date").descending(), getEntityManager().find(UserEntity.class, ownerId));

        if(page != null && limit != null)
            query.page(page, limit);

        return query.list();
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

    public List<ExpenseEntity> findByCategoryId(UUID categoryId) {
        var category = getEntityManager().find(ExpenseCategoryEntity.class, categoryId);
        return find("category", category).list();
    }
}
