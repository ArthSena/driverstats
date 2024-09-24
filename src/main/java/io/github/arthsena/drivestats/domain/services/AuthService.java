package io.github.arthsena.drivestats.domain.services;

import io.github.arthsena.drivestats.app.controllers.auth.AuthRequest;
import io.github.arthsena.drivestats.domain.exceptions.BadRequestException;
import io.github.arthsena.drivestats.domain.exceptions.NotFoundException;
import io.github.arthsena.drivestats.domain.exceptions.UnauthorizedException;
import io.github.arthsena.drivestats.domain.models.User;
import io.github.arthsena.drivestats.infra.database.repositories.UserRepository;
import io.github.arthsena.drivestats.infra.exception.ExceptionType;
import io.github.arthsena.drivestats.infra.security.Subject;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Objects;

@ApplicationScoped
public class AuthService {

    @Inject UserRepository users;

    public User login(AuthRequest.Login request) {
        var entity = users.findByEmail(request.getEmail()).map(User::new).orElse(null);

        if(entity == null) {
            throw new NotFoundException(ExceptionType.PARAM_NOT_FOUND.withPattern("Email"));
        }

        if(!BcryptUtil.matches(request.getPassword(), entity.getPassword())){
            throw new UnauthorizedException(ExceptionType.INVALID_PASSWORD);
        }

        return entity;
    }

    public User register(AuthRequest.Registration request) {
        if(users.existsEmail(request.getEmail())) {
            throw new NotFoundException(ExceptionType.PARAM_ALREADY_EXISTS.withPattern("Email"));
        }

        if(!Objects.equals(request.getPassword(), request.getConfirmPassword())){
            throw new BadRequestException(ExceptionType.PASSWORDS_NOT_MATCH);
        }

        String hashedPassword = BcryptUtil.bcryptHash(request.getPassword());
        return new User(users.create(request.getName(), request.getEmail(), hashedPassword));
    }

    public User refresh(Subject subject) {
        if(!users.existsId(subject.getId()))
            throw new NotFoundException(ExceptionType.INVALID_SUBJECT);

        return new User(users.findById(subject.getId()));
    }
}
