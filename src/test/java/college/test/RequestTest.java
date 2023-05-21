package college.test;

import college.library.Request;
import college.users.Position;
import college.users.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestTest {

    private ArrayList<Request> testList = new ArrayList<>();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        User user1 = new User("Roboute", "0013", Position.SS2);
        User user2 = new User("Konrad", "0008", Position.JS3);
        Request r1 = new Request("010101", user1);
        Request r2 = new Request("010136", user2);
        testList.addAll(List.of(r1, r2));
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        testList.clear();
    }

    @Test
    void getISBN() {
        Request r1 = testList.get(0);
        assertEquals(r1.getISBN(), "010101");
    }

    @Test
    void getUser() {
        Request r = testList.get(1);
        assertEquals(r.getUser().getRegNo(), "0008");
    }

    @Test
    void testHashCode() {
        User user2 = new User("Konrad", "0008", Position.JS3);
        Request r1 = testList.get(1);
        Request r2 = new Request("010136", user2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testEquals() {
        User user2 = new User("Konrad", "0008", Position.JS3);
        Request r1 = testList.get(1);
        Request r2 = new Request("010136", user2);
        assertEquals(r1, r2);
    }
}