package com.skytecgames.core;

import com.skytecgames.core.AbstractEntity;

import java.util.Optional;

public interface EntityService<ID, Entity extends AbstractEntity<ID>> {
    Optional<Entity> get(ID id);
    ID save(Entity entity);
}
