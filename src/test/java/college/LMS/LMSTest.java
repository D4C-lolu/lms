package college.LMS;

import college.users.Position;
import college.users.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LMSTest {

    private ArrayList<User> users = new ArrayList<>();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        User user1 = new User("Roboute", "0013", Position.SS2);
        User user2 = new User("Konrad", "0008", Position.JS3);
        User user3 = new User("Khan", "0005", Position.Teacher);
        users.addAll(List.of(user1, user2, user3));
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        users.clear();
    }

    @Test
    void getInstance() {
        LMS instance1 = LMS.getInstance();
        LMS instance2 = LMS.getInstance();

        int hashOne = instance1.hashCode();
        int hashTwo = instance2.hashCode();

        assertTrue(hashOne == hashTwo);
    }

    @Test
    void initialize() {
        LMS.getInstance().initialize(users);
        assertTrue(LMS.getInstance().getUsers().size() == 3);
    }

    @Test
    void findUser() {

        User u1 = LMS.getInstance().findUser("0013");
        User u2 = users.get(0);
        assertEquals(u1, u2);
    }
}