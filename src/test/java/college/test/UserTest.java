package college.test;

import college.book.Book;
import college.book.BookList;
import college.book.BorrowedBook;
import college.library.Library;
import college.users.Position;
import college.users.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<BookList> testLists = new ArrayList<>();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        User user1 = new User("Roboute", "0013", Position.SS2);
        User user2 = new User("Konrad", "0008", Position.JS3);
        User user3 = new User("Khan", "0005", Position.Teacher);
        users.addAll(List.of(user1, user2, user3));


        Book book1 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book2 = new Book("Arrow of God", "Chinua Achebe", "Historical Fiction", "23223", true);
        Book book5 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book9 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book10 = new Book("Arrow of God", "Chinua Achebe", "Historical Fiction", "23223", true);

        ArrayList<Book> bookList = new ArrayList<>();
        bookList.addAll(List.of(book1, book2, book5, book9, book10));
        Library.getInstance().initialize(bookList);

        BookList bk1 = new BookList(book1, book5, book9);
        testLists.add(bk1);
        BookList bk2 = new BookList(book2, book10);
        testLists.add(bk2);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        users.clear();
    }

    @Test
    void getName() {
        User u = users.get(0);
        assertEquals(u.getName(), "Roboute");
    }

    @Test
    void getRegNo() {
        User u = users.get(0);
        assertEquals(u.getRegNo(), "0013");
    }

    @Test
    void getPos() {
        User u = users.get(0);
        assertEquals(u.getPos(), Position.SS2);
    }

    @Test
    void getBorrowedBooks() {
        User u = users.get(2);
        Book b = testLists.get(0).getCopy();
        BorrowedBook bb = new BorrowedBook(b);
        u.borrowBook(bb);
        assertEquals(u.getBorrowedBooks().get(0), bb);

    }

    @Test
    void checkAlreadyBorrowed() {
        User u = users.get(2);
        Book book5 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book9 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);

        BorrowedBook bb = new BorrowedBook(book5);
        u.borrowBook(bb);
        BorrowedBook cc = new BorrowedBook(book9);
        assertEquals(u.checkAlreadyBorrowed(cc.getISBN()), true);
    }


    @Test
    void returnBook() {

        User u = users.get(2);
        int bk = testLists.get(0).getAvailableCopies();
        Book b = testLists.get(0).getCopy();
        BorrowedBook bb = new BorrowedBook(b);
        u.borrowBook(bb);
        u.returnBook(bb.getISBN());
        int ck = testLists.get(0).getAvailableCopies();
        assertEquals(ck, bk);
    }

    @Test
    void borrowBook() {
        User u = users.get(2);
        int bk = testLists.get(0).getAvailableCopies();
        Book b = testLists.get(0).getCopy();
        BorrowedBook bb = new BorrowedBook(b);
        u.borrowBook(bb);
        int ck = testLists.get(0).getAvailableCopies();
        assertEquals(bk, ck);
    }

    @Test
    void testEquals() {
        User u = users.get(2);
        User user3 = new User("Khan", "0005", Position.Teacher);
        assertEquals(u, user3);
    }
}