package college.test;

import college.book.Book;
import college.book.BookList;
import college.library.Library;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

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

    @Test
    void getInstance() {
        Library instance1 = Library.getInstance();
        Library instance2 = Library.getInstance();

        int hashOne = instance1.hashCode();
        int hashTwo = instance2.hashCode();

        assertTrue(hashOne == hashTwo);
    }

    @Test
    void initialize() {

        Library.getInstance().initialize(testList);
        assertNotEquals(Library.getInstance().getAvailableBooks().size(), 0);
    }

    @Test
    void checkAvailability() {
        boolean av = Library.getInstance().checkAvailability("010101");
        assertEquals(av, true);
    }

    @Test
    void getAvailableBooks() {
        ArrayList<BookList> bks = Library.getInstance().getAvailableBooks();
        assertNotEquals(bks.size(), 0);
    }

    @Test
    void getBooks() {
        BookList bk = Library.getInstance().getBooks("010101");
        assertEquals(bk.getCopy().getISBN(), "010101");
    }
}