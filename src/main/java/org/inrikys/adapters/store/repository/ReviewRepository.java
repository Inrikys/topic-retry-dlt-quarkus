package org.inrikys.adapters.store.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.inrikys.adapters.store.entities.ReviewEntity;
import org.inrikys.adapters.store.entities.UserEntity;

import java.util.Optional;

@ApplicationScoped
public class ReviewRepository implements PanacheRepository<ReviewEntity> {
}
