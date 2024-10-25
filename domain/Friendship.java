package domain;

public class Friendship extends Entity<Long> {
    private Long id1;
    private Long id2;

    public Friendship() {}

    public Friendship(Long id, Long id1, Long id2) {
        super(id);
        this.id1 = id1;
        this.id2 = id2;
    }

    public Long getId1() {
        return id1;
    }

    public Long getId2() {
        return id2;
    }

    @Override
    public String toString() {
        return "Friendship {" + "id1=" + id1 + ", id2=" + id2 + '}';
    }
}
