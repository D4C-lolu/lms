package college.test;

import college.book.Book;
import college.book.BookList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class BookListTest {

    private ArrayList<BookList> testLists = new ArrayList<>();
    private ArrayList<Book> b = new ArrayList<>();


    @BeforeEach
    void setUp() {
        Book book1 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book2 = new Book("Arrow of God", "Chinua Achebe", "Historical Fiction", "23223", true);
        Book book5 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book9 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book10 = new Book("Arrow of God", "Chinua Achebe", "Historical Fiction", "23223", true);
        BookList bk1 = testLists.get(0);
        bk1 = new BookList(book1, book5, book9);
        BookList bk2 = testLists.get(1);
        bk2 = new BookList(book2, book10);

    }

    @AfterEach
    void tearDown() {
        testList.clear();
    }

    @Test
    void addCopies() {
        BookList bk1 = testLists.get(0);
        int n = bk1.getAvailableCopies();
        b = new ArrayList<>();
        b.add(new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true));
        bk1.addCopies(b);
        int o =bk1.getAvailableCopies();
        assertTrue (o<n);
    }

    @Test
    void addCopy() {
    }

    @Test
    void getAvailableCopies() {
    }

    @Test
    void checkIfAvailable() {
    }

    @Test
    void getNumOfCopies() {
    }

    @Test
    void getNumofBorrowedCopies() {
    }

    @Test
    void getCopy() {
    }

    @Test
    void getReturnedCopy() {
    }
}