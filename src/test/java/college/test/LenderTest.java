package college.test;

import college.LMS.LMS;
import college.book.Book;
import college.library.Lender;
import college.library.Library;
import college.library.Request;
import college.users.Position;
import college.users.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class LenderTest {

    @BeforeEach
    void setUp() {
        Book book1 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book2 = new Book("Arrow of God", "Chinua Achebe", "Historical Fiction", "23223", true);
        Book book3 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book4 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book5 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book6 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book7 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book8 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);

        ArrayList<Book> bookList = new ArrayList<>();
        bookList.addAll(List.of(book1, book2, book3, book4, book5, book6, book7, book8));
        Library.getInstance().initialize(bookList);

        User user1 = new User("Roboute", "0013", Position.SS2);
        User user2 = new User("Konrad", "0008", Position.JS3);
        User user3 = new User("Khan", "0005", Position.Teacher);
        ArrayList<User> users = new ArrayList<>();
        users.addAll(List.of(user1, user2, user3));
        LMS.getInstance().initialize(users);

        Request r1 = new Request("23223", user2);
        Request r2 = new Request("23223", user3);
        ArrayList<Request> requests = new ArrayList<>();
        requests.addAll(List.of(r1, r2));
        Lender.getInstance().addRequests(requests);

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getInstance() {
        Lender instance1 = Lender.getInstance();
        Lender instance2 = Lender.getInstance();

        int hashOne = instance1.hashCode();
        int hashTwo = instance2.hashCode();

        assertTrue(hashOne == hashTwo);

    }

    @Test
    void getQueuedRequests() {
        PriorityQueue<Request> requests = Lender.getInstance().getQueuedRequests();
        Request r1 = requests.poll();
        Request r2 = requests.poll();
        assertNotNull(r2);
        assertNotNull(r1);

    }

    @Test
    void getUserRequests() {
        User u2 = new User("Konrad", "0008", Position.JS3);
        ArrayList<Request> req = Lender.getInstance().getUserRequests(u2);
        Request r1 = req.get(0);
        assertTrue(r1.getUser().equals(u2));
    }

    @Test
    void getPendingBooks() {
        User u2 = new User("Konrad", "0008", Position.JS3);
        ArrayList<Book> books = Lender.getInstance().getPendingBooks(u2);
        Book b = books.get(0);
        ArrayList<Request> req = Lender.getInstance().getUserRequests(u2);
        Request r1 = req.get(0);
        assertTrue(b.getISBN().equals(r1.getISBN()));
    }

    @Test
    void removeRequest() {
        User u2 = new User("Konrad", "0008", Position.JS3);
        ArrayList<Book> books = Lender.getInstance().getPendingBooks(u2);
        Book b = books.get(0);
        Lender.getInstance().removeRequest(b.getISBN(), u2);
        ArrayList<Request> req = Lender.getInstance().getUserRequests(u2);
        assertEquals(req.size(), 0);
    }

    @Test
    void processRequests() {

        Lender.getInstance().processRequests();
        PriorityQueue<Request> req = Lender.getInstance().getQueuedRequests();
        assertEquals(req.size(), 2);
    }
}