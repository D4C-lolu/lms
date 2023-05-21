package college.LMS;

import college.users.User;

import java.util.ArrayList;

public class LMS {

    private static LMS _instance;

    static {
        try {
            _instance = new LMS();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating singleton instance");
        }
    }

    private ArrayList<User> users;

    private LMS() {

    }

    public static LMS getInstance() {
        return _instance;
    }

    public void initialize(ArrayList<User> users) {
        this.users = users;

    }

    public void checkOverDue() {
        this.users.forEach(user -> {
            user.removeDueBooks();
        });
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User findUser(String regNo) {
        return this.users.stream().filter(u -> u.getRegNo().equals(regNo)).findFirst().orElse(null);
    }


}
