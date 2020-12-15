package pro.sb2.todojdbc.store;

import java.util.Collection;

public interface CommonRepository<T> {
    public T save(T t);
    public Iterable<T> save(Collection<T> ts);
    public void delete(T t);
    public T findById(String id);
    public Iterable<T> findAll();
}
