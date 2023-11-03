package io.dborrego.service;

import java.util.List;
import java.util.Optional;

import io.dborrego.domain.User;
import io.dborrego.domain.UserRepository;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository usersRepository;

    public List<User> listAll() {
        return usersRepository.listAll();
    }

    @Timed(value = "count.time")
    @Counted(value = "count.find")
    public User findById(final Long idUser) {
        Log.info(String.format("Buscando usuario con id %d", idUser));
        return usersRepository.findById(idUser);
    }

    @Transactional
    @Timed(value = "count.time")
    @Counted(value = "count.create")
    public User create(final User u) {
        Log.info(String.format("Creando nuevo usuario con nombre: %s", u.getFirstName()));
        final User user = new User();
        user.setDni(u.getDni());
        user.setFirstName(u.getFirstName());
        user.setGender(u.getGender());
        user.setLastName(u.getLastName());
        user.setPhone(u.getPhone());
        usersRepository.persist(user);
        return user;
    }

    @Transactional
    @Timed(value = "count.time")
    @Counted(value = "count.update")
    public User update(final User u, final Long idUser) {
        final User user = Optional.ofNullable(idUser)
                .map(this::findById)
                .orElseThrow(() -> {
                    Log.info(String.format("Usuario %d no encontrado", idUser));
                    throw new NotFoundException();
                });
        user.setDni(u.getDni());
        user.setFirstName(u.getFirstName());
        user.setGender(u.getGender());
        user.setLastName(u.getLastName());
        user.setPhone(u.getPhone());
        Log.info(String.format("Actualizando usuario %d", user.getId()));
        usersRepository.persist(user);
        return user;
    }

    @Transactional
    public Boolean deleteById(final Long id) {
        return Optional.ofNullable(id)
                .map(idUser -> usersRepository
                        .deleteById(idUser))
                .orElse(false);
    }

}
