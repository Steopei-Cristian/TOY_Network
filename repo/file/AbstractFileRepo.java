package repo.file;

import domain.Entity;
import repo.memory.InMemoryRepo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public abstract class AbstractFileRepo<ID extends Comparable<ID>, E extends Entity<ID>> extends InMemoryRepo<ID, E> {
    protected Path path;

    public AbstractFileRepo(Path path) throws IOException {
        this.path = path;
        read();
    }

    protected abstract void read() throws IOException;
    protected abstract void write() throws IOException;

    @Override
    public void add(E entity) {
        super.add(entity);
        try {
            write();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file");
        }
    }

    @Override
    public E delete(ID id) throws ClassNotFoundException {
        E res = super.delete(id);
        try {
            write();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file");
        }
        return res;
    }

    @Override
    public E update(ID id, E entity) throws ClassNotFoundException {
        E res = super.update(id, entity);
        try {
            write();
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file");
        }
        return res;
    }
}
