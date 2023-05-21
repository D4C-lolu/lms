package college.test;


import college.book.Book;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookTest {

    private ArrayList<Book> testList = new ArrayList<>();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Book book1 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book2 = new Book("Arrow of God", "Chinua Achebe", "Historical Fiction", "23223", true);
        Book book3 = new Book("The bottled leopard", "ABC", "Fantasy", "010101", true);
        Book book4 = new Book("Americanah", "Chimamanda Adichie", "Fiction", "06504", false);
        testList.addAll(List.of(book1, book2, book3, book4));
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        testList.clear();
    }

    @org.junit.jupiter.api.Test
    void getBookName() {
        String name = testList.get(0).getBookName();
        assert (name.equals("Things fall apart"));
    }

    @org.junit.jupiter.api.Test
    void getAuthorName() {
        String name = testList.get(0).getAuthorName();
        assert (name.equals("Chinua Achebe"));
    }

    @org.junit.jupiter.api.Test
    void getSubject() {
        String name = testList.get(1).getSubject();
        assert (name.equals("Historical Fiction"));
    }

    @org.junit.jupiter.api.Test
    void getISBN() {
        String name = testList.get(3).getISBN();
        assert (name.equals("06504"));
    }

    @org.junit.jupiter.api.Test
    void isAvailable() {
        boolean flag = testList.get(3).isAvailable();
        assertTrue(flag == false);
    }

    @org.junit.jupiter.api.Test
    void setAvailable() {
        testList.get(2).setAvailable(false);
        boolean flag = testList.get(2).isAvailable();
        assertTrue(flag == false);
    }


    @org.junit.jupiter.api.Test
    void testEquals() {
        Book b = new Book("Americanah", "Chimamanda Adichie", "Fiction", "06505", false);
        Book bk = testList.get(3);
        assertFalse(b.equals(bk));

    }
}
