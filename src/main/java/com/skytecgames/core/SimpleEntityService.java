package com.skytecgames.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimpleEntityService<T extends AbstractEntity<Long>> implements EntityService<Long, T> {

    private final Map<Long, T> store = Collections.synchronizedMap(new HashMap<>());


    @Override
    public Optional<T> get(Long id) {
        if (store.containsKey(id)) {
            return Optional.of(store.get(id));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public synchronized Long save(T entity) {
        Long id = store.size() + 1L;
        entity.setId(id);
        synchronized (store) {
            store.put(id, entity);
        }
        return id;
    }
}
