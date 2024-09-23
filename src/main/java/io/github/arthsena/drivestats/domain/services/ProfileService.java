package io.github.arthsena.drivestats.domain.services;

import io.github.arthsena.drivestats.app.controllers.profile.ProfileRequest;
import io.github.arthsena.drivestats.domain.exceptions.ConflictException;
import io.github.arthsena.drivestats.domain.exceptions.ForbiddenException;
import io.github.arthsena.drivestats.domain.exceptions.NotFoundException;
import io.github.arthsena.drivestats.domain.models.User;
import io.github.arthsena.drivestats.infra.database.repositories.UserRepository;
import io.github.arthsena.drivestats.infra.exception.ExceptionType;
import io.github.arthsena.drivestats.infra.security.Subject;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProfileService {

    @Inject
    UserRepository users;

    public User update(Subject subject, ProfileRequest.Update request) {
        if(!users.existsId(subject.getId()))
            throw new NotFoundException(ExceptionType.INVALID_SUBJECT);

        var user = users.findById(subject.getId());

        if(!BcryptUtil.matches(request.getCurrentPassword(), user.getPassword()))
            throw new ForbiddenException(ExceptionType.INVALID_PASSWORD);

        if(request.getEmail() != null)
            if (users.existsEmail(request.getEmail()))
                throw new ConflictException(ExceptionType.PARAM_ALREADY_EXISTS.withPattern("Email"));

        String newPassword = null;

        if(request.getNewPassword() != null && request.getConfirmPassword() != null) {
            if(request.getNewPassword().equals(request.getConfirmPassword()))
                newPassword = BcryptUtil.bcryptHash(request.getNewPassword());
            else
                throw new ForbiddenException(ExceptionType.PASSWORDS_NOT_MATCH);
        }

        return new User(users.update(subject.getId(), request.getName(), request.getEmail(), newPassword));
    }


}
