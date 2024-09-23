package io.github.arthsena.drivestats.domain.services;

import io.github.arthsena.drivestats.app.controllers.expense.ExpenseRequest;
import io.github.arthsena.drivestats.domain.exceptions.NotFoundException;
import io.github.arthsena.drivestats.domain.exceptions.UnauthorizedException;
import io.github.arthsena.drivestats.domain.models.Expense;
import io.github.arthsena.drivestats.domain.models.User;
import io.github.arthsena.drivestats.infra.database.entities.ExpenseEntity;
import io.github.arthsena.drivestats.infra.database.repositories.ExpenseRepository;
import io.github.arthsena.drivestats.infra.database.repositories.UserRepository;
import io.github.arthsena.drivestats.infra.exception.ExceptionType;
import io.github.arthsena.drivestats.infra.security.Subject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ExpenseService {

    @Inject
    UserRepository users;

    @Inject
    ExpenseRepository expenses;

    public Expense create(Subject subject, ExpenseRequest.Create request) {
        if(!users.existsId(subject.getId())) throw new NotFoundException(ExceptionType.INVALID_SUBJECT);
        return new Expense(expenses.create(subject.getId(), request.getCategoryId(), request.getAmount(), request.getDescription()));
    }

    public List<Expense> all(Subject subject, Integer page, Integer limit) {
        if (!users.existsId(subject.getId())) throw new NotFoundException(ExceptionType.INVALID_SUBJECT);
        return expenses.findByOwnerId(subject.getId(), page, limit).stream().map(Expense::new).toList();
    }

    public Expense update(Subject subject, UUID expenseId, ExpenseRequest.Update request) {
        validatedEntity(subject, expenseId);
        return new Expense(expenses.update(expenseId, request.getCategoryId(), request.getDescription(), request.getAmount()));
    }

    public void delete(Subject subject, UUID expenseId) {
        expenses.delete(validatedEntity(subject, expenseId));
    }

    private ExpenseEntity validatedEntity(Subject subject, UUID expenseId) {
        if(!users.existsId(subject.getId()))
            throw new NotFoundException(ExceptionType.INVALID_SUBJECT);

        if(!expenses.existsById(expenseId))
            throw new NotFoundException(ExceptionType.ENTITY_NOT_FOUND.withPattern("Registry '%s'".formatted(expenseId)));

        var entity = expenses.findById(expenseId);

        if(!entity.getOwner().getId().equals(subject.getId()) || subject.getRole() == User.Role.ADMIN)
            throw new UnauthorizedException(ExceptionType.UNAUTHORIZED.withPattern("Current subject '%s'".formatted(subject.getId())).withBody("%s is not authorized to access this registry"));

        return entity;
    }
}
