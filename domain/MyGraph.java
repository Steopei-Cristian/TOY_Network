package domain;

import java.security.KeyPair;
import java.util.*;
import java.util.function.Predicate;

public class MyGraph<ID extends Comparable<ID>, E extends Entity<ID>> {
    private HashMap<E, List<E>> G;
    private Iterable<E> all;
    private int compCount;

    public MyGraph(Iterable<E> all, List<List<E>> links) {
        G = new HashMap<>();
        refresh(all, links);
    }

    public void refresh(Iterable<E> all, List<List<E>> links) {
        this.all = all;
        links.forEach(link -> {
            Predicate<E> added = x -> G.containsKey(x);
            if(!added.test(link.getFirst()))
                G.put(link.getFirst(), new ArrayList<>());
            if(!added.test(link.get(1)))
                G.put(link.get(1), new ArrayList<>());
            G.get(link.getFirst()).add(link.get(1));
            G.get(link.get(1)).add(link.getFirst());
        });
    }

    public int getCompCount() {
        countComps();
        return compCount;
    }

    public void setAll(Iterable<E> newAll) {
        all = newAll;
    }

    public List<E> getNeighbours(E e){
        return G.get(e);
    }

    private void dfs(E node, List<E> seen) {
        seen.add(node);
        G.get(node).forEach(v -> {
            if(!seen.contains(v))
                dfs(v, seen);
        });
    }

    public void countComps(){
        compCount = 0;
        List<E> seen = new ArrayList<>();
        all.forEach(elem -> {
            // System.out.println(elem);
            if(!seen.contains(elem)) {
                if(G.containsKey(elem)) {
                    dfs(elem, seen);
                }
                compCount ++;
            }
        });
    }

    public void unlink(E v1, E v2){
        if(!G.containsKey(v1))
            throw new IllegalArgumentException("No such vertex");
        if(!G.get(v1).contains(v2))
            throw new IllegalArgumentException("No link existent between the 2 vertexes");

        G.get(v1).remove(v2);
        G.get(v2).remove(v1);
    }

    public boolean link(E v1, E v2){
        if(!G.containsKey(v1))
            G.put(v1, new ArrayList<>());
        if(!G.get(v1).contains(v2))
            G.put(v2, new ArrayList<>());

        if(G.get(v1).contains(v2))
            return false;

        G.get(v1).add(v2);
        G.get(v2).add(v1);
        return true;
    }

    public void unlinkEntity(E entity) {
        all.forEach(e -> {
            if(!e.equals(entity)) {
                try{
                    unlink(e, entity);
                } catch (Exception ex) {
                    // continue
                }
            }
        });
    }
}
