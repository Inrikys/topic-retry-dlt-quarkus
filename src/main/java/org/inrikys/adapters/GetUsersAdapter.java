package org.inrikys.adapters;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import org.inrikys.adapters.store.entities.UserEntity;
import org.inrikys.adapters.store.repository.UserRepository;
import org.inrikys.domain.models.User;
import org.inrikys.domain.ports.GetUsersPort;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class GetUsersAdapter implements GetUsersPort {

    private final UserRepository userRepository;

    public GetUsersAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        PanacheQuery<UserEntity> allUsersEntity = this.userRepository.findAll();
        return allUsersEntity.stream().map(UserEntity::toUser).toList();
    }

    @Override
    public Boolean existsById(Long userId) {
        Optional<UserEntity> possibleUser = this.userRepository.findByIdOptional(userId);
        return possibleUser.isPresent();
    }
}
