package io.github.arthsena.drivestats.infra.database.repositories;

import io.github.arthsena.drivestats.infra.database.entities.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional
@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<UserEntity, UUID> {

    public boolean existsId(UUID id) {
        return count("id", id) > 0;
    }

    public boolean existsEmail(String email) {
        return count("upper(email)", email.toUpperCase().trim()) > 0;
    }

    public Optional<UserEntity> findByEmail(String email) {
        return find("upper(email)", email.toUpperCase().trim()).firstResultOptional();
    }

    public UserEntity create(String username, String email, String password) {
        UserEntity entity = new UserEntity(username, email, password);
        persist(entity);

        return entity;
    }

    public UserEntity update(UUID userId, String name, String email, String newPassword) {
        var entity = findById(userId);

        if(name != null)        entity.setName(name);
        if(email != null)       entity.setEmail(email);
        if(newPassword != null) entity.setPassword(newPassword);

        entity.persistAndFlush();
        return entity;
    }
}
