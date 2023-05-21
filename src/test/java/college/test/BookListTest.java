package college.test;

import college.book.Book;
import college.book.BookList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BookListTest {

    private ArrayList<BookList> testLists = new ArrayList<>();


    @BeforeEach
    void setUp() {
        Book book1 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book2 = new Book("Arrow of God", "Chinua Achebe", "Historical Fiction", "23223", true);
        Book book5 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book9 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book10 = new Book("Arrow of God", "Chinua Achebe", "Historical Fiction", "23223", true);


        BookList bk1 = new BookList(book1, book5, book9);
        testLists.add(bk1);
        BookList bk2 = new BookList(book2, book10);
        testLists.add(bk2);


    }

    @AfterEach
    void tearDown() {
        testLists.clear();
    }

    @Test
    void addCopies() {
        BookList bk1 = testLists.get(0);
        int n = bk1.getAvailableCopies();
        ArrayList<Book> b = new ArrayList<>();
        b.add(new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true));
        bk1.addCopies(b);
        int o = bk1.getAvailableCopies();
        assertTrue(o > n);
    }

    @Test
    void addCopy() {
        BookList bk1 = testLists.get(0);
        int a = bk1.getAvailableCopies();
        bk1.addCopy(new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true));
        BookList bk2 = testLists.get(0);
        int b = bk2.getAvailableCopies();
        assertTrue(a + 1 == b);

    }

    @Test
    void getAvailableCopies() {
        BookList bk1 = testLists.get(0);
        int n = bk1.getAvailableCopies();
        assertTrue(n == 3);
    }

    @Test
    void checkIfAvailable() {
        BookList bk1 = testLists.get(0);
        assertTrue(bk1.checkIfAvailable() == true);
    }

    @Test
    void getNumOfCopies() {
        BookList bk1 = testLists.get(0);
        bk1.addCopy(new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", false));
        assertTrue(bk1.getNumOfCopies() == 4);
    }

    @Test
    void getNumofBorrowedCopies() {
        BookList bk1 = testLists.get(0);
        bk1.addCopy(new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", false));
        assertTrue(bk1.getNumofBorrowedCopies() == 1);
    }

    @Test
    void getCopy() {
        BookList bk1 = testLists.get(0);
        Book b1 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", false);
        Book b2 = bk1.getCopy();
        assertTrue(b1.equals(b2));
    }

    @Test
    void getReturnedCopy() {
        BookList bk1 = testLists.get(0);
        bk1.addCopy(new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", false));
        Book b = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", false);
        assertTrue(bk1.getReturnedCopy().equals(b));
    }
}