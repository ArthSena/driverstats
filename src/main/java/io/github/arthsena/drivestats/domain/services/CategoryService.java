package io.github.arthsena.drivestats.domain.services;

import io.github.arthsena.drivestats.infra.database.repositories.ExpenseCategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CategoryService {

    @Inject
    ExpenseCategoryRepository categories;

}
