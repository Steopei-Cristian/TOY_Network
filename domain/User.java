package domain;

import java.util.Objects;

public class User extends Entity<Long> {
    private String username;
    private String password;

    public User() {}

    public User(Long id, String username, String password) {
        super(id);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User {" +
                "username='" + username + '\'' +
                ", password=" + password +
                '}';
    }
}
