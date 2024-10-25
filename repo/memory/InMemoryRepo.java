package repo.memory;

import domain.Entity;
import repo.Repo;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepo<ID extends Comparable<ID>, E extends Entity<ID>> implements Repo<ID, E> {
    protected Map<ID, E> elems;
    protected ID lastID;

    public InMemoryRepo() {
        elems = new HashMap<ID, E>();
        lastID = null;
    }

    @Override
    public E find(ID id) {
        if(id == null)
            throw new IllegalArgumentException("Null ID");

        return elems.get(id);
    }

    @Override
    public Iterable<E> getAll() {
        return elems.values();
    }

    @Override
    public void add(E entity) {
        if(entity == null)
            throw new IllegalArgumentException("Null entity");

        System.out.println(lastID + " | " + entity.getID());
        elems.put(entity.getID(), entity);

        if(lastID == null)
            lastID = entity.getID();
        else if(lastID.compareTo(entity.getID()) < 0)
            lastID = entity.getID();
    }

    @Override
    public E delete(ID id) throws ClassNotFoundException {
        if(id == null)
            throw new IllegalArgumentException("Null ID");

        E res = elems.remove(id);
        if(res == null)
            throw new ClassNotFoundException("No such id");

        if(res.getID().equals(lastID)) {
            if(elems.isEmpty())
                lastID = null;
            else
                lastID = elems.keySet().stream().max(ID::compareTo).get();
        }
        return res;
    }

    @Override
    public E update(ID id, E entity) throws ClassNotFoundException {
        if(id == null)
            throw new IllegalArgumentException("Null ID");
        if(entity == null)
            throw new IllegalArgumentException("Null entity");

        E res = elems.replace(id, entity);
        if(res == null)
            throw new ClassNotFoundException("No such id");

        return res;
    }

    public ID getLastID() {
        return lastID;
    }

    public void setLastID(ID lastID) {
        this.lastID = lastID;
    }
}
