package college.library;

import college.users.User;

import java.util.Objects;

public class Request {
    private final String ISBN;
    private final User user;

    public Request(String ISBN, User user) {
        this.ISBN = ISBN;
        this.user = user;
    }

    public String getISBN() {
        return ISBN;
    }

    public User getUser() {
        return user;
    }

    @Override
    public int hashCode() {
        String hash = ISBN + user.getRegNo();
        return Objects.hash(hash);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Request other = (Request) obj;
        String s1 = ISBN + user.getRegNo();
        String s2 = other.getISBN() + other.getUser().getRegNo();
        return Objects.equals(s1, s2);
    }
}
