package io.github.arthsena.drivestats.domain.services;

import io.github.arthsena.drivestats.infra.database.repositories.ExpenseCategoryRepository;
import io.github.arthsena.drivestats.infra.database.repositories.ExpenseRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ExpenseService {

    @Inject
    ExpenseRepository expenses;

    @Inject
    ExpenseCategoryRepository categories;

}
