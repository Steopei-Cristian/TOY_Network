package repo;

import domain.Entity;

public interface Repo<ID extends Comparable<ID>, E extends Entity<ID>> {
    E find(ID id);
    Iterable<E> getAll();

    void add(E entity);
    E delete(ID id) throws ClassNotFoundException;
    E update(ID id, E entity) throws ClassNotFoundException;
}
