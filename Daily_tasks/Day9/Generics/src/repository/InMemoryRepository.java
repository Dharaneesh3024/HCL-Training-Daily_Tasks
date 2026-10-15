package repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class InMemoryRepository<T, ID> implements CrudRepository<T, ID> {

    private final Map<ID, T> store = new LinkedHashMap<>();
    private final Function<T, ID> idOf;

    public InMemoryRepository(Function<T, ID> idOf) {
        this.idOf = idOf;
    }

    public void saveAll(Iterable<? extends T> entities) {
        for (T entity : entities) {
            save(entity);
        }
    }

    @Override
    public T save(T entity) {
        store.put(idOf.apply(entity), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsById(ID id) {
        return store.containsKey(id);
    }

    @Override
    public boolean deleteById(ID id) {
        return store.remove(id) != null;
    }

    @Override
    public long count() {
        return store.size();
    }
}
