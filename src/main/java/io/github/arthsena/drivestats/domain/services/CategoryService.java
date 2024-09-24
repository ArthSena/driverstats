package io.github.arthsena.drivestats.domain.services;

import io.github.arthsena.drivestats.app.controllers.category.CategoryRequest;
import io.github.arthsena.drivestats.domain.exceptions.NotFoundException;
import io.github.arthsena.drivestats.domain.exceptions.UnauthorizedException;
import io.github.arthsena.drivestats.domain.models.Expense;
import io.github.arthsena.drivestats.domain.models.User;
import io.github.arthsena.drivestats.infra.database.entities.ExpenseCategoryEntity;
import io.github.arthsena.drivestats.infra.database.repositories.ExpenseCategoryRepository;
import io.github.arthsena.drivestats.infra.database.repositories.ExpenseRepository;
import io.github.arthsena.drivestats.infra.database.repositories.UserRepository;
import io.github.arthsena.drivestats.infra.exception.ExceptionType;
import io.github.arthsena.drivestats.infra.security.Subject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CategoryService {

    @Inject
    UserRepository users;

    @Inject
    ExpenseCategoryRepository categories;

    @Inject
    ExpenseRepository expenses;

    public List<Expense.Category> all(Subject subject) {
        if (!users.existsId(subject.getId())) throw new NotFoundException(ExceptionType.INVALID_SUBJECT);
        return categories.findByOwnerId(subject.getId()).stream().map(Expense.Category::new).toList();
    }

    public Expense.Category create(Subject subject, CategoryRequest.Create request) {
        if (!users.existsId(subject.getId())) throw new NotFoundException(ExceptionType.INVALID_SUBJECT);
        return new Expense.Category(categories.create(subject.getId(), request.getName(), request.getColor()));
    }

    public Expense.Category update(Subject subject, UUID categoryId, CategoryRequest.Update request) {
        validatedEntity(subject, categoryId);

        return new Expense.Category(categories.update(categoryId, request.getName(), request.getColor()));
    }

    public void delete(Subject subject, UUID categoryId) {
        categories.delete(validatedEntity(subject, categoryId));
    }

    private ExpenseCategoryEntity validatedEntity(Subject subject, UUID categoryId) {
        if(!users.existsId(subject.getId()))
            throw new NotFoundException(ExceptionType.INVALID_SUBJECT);

        if(!categories.existsById(categoryId))
            throw new NotFoundException(ExceptionType.ENTITY_NOT_FOUND.withPattern("Registry '%s'".formatted(categoryId)));

        var entity = categories.findById(categoryId);

        if(!entity.getOwner().getId().equals(subject.getId()) || subject.getRole() == User.Role.ADMIN)
            throw new UnauthorizedException(ExceptionType.UNAUTHORIZED.withPattern("Current subject '%s'".formatted(subject.getId())).withBody("%s is not authorized to access this registry"));

        return entity;
    }

}
