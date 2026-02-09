package org.inrikys.adapters.store.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.inrikys.adapters.store.entities.DltEntity;

@ApplicationScoped
public class DltRepository implements PanacheRepository<DltEntity> {
}
