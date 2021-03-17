package dev.slavin.models;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private int id;
    private String userName; //userName must be unique in database.
    private String password;
    private int authLevel;

    public User() {
        super();
    }

    public User(String userName, String password, int authLevel) {
        this.userName = userName;
        this.password = password;
        this.authLevel = authLevel;
    }

    public User(int id, String userName, String password, int authLevel) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.authLevel = authLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAuthLevel() { return authLevel; }

    public void setAuthLevel(int authLevel) { this.authLevel = authLevel; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(authLevel, user.authLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, authLevel);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", authLevel'" + authLevel + '\'' +
                '}';
    }
}
