package io.github.arthsena.drivestats.infra.database.repositories;

import io.github.arthsena.drivestats.infra.database.entities.ExpenseEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import java.util.UUID;

public class ExpenseRepository implements PanacheRepositoryBase<ExpenseEntity, UUID> {
}
