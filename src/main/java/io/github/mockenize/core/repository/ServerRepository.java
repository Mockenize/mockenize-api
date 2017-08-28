package io.github.mockenize.core.repository;

import io.github.mockenize.core.domain.ServerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServerRepository extends CrudRepository<ServerEntity, UUID> {

}
