package college.test;

import college.book.BorrowedBook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BorrowedBookTest {

    private ArrayList<BorrowedBook> testList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        BorrowedBook book1 = new BorrowedBook("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        BorrowedBook book2 = new BorrowedBook("Arrow of God", "Chinua Achebe", "Historical Fiction", "23223", true);
        testList.addAll(List.of(book1, book2));

    }

    @AfterEach
    void tearDown() {
        testList.clear();
    }

    @Test
    void getDateBorrowed() {
        BorrowedBook bk = testList.get(0);
        String date = bk.getDateBorrowed().toString();
        assertTrue(date.equals(LocalDate.now().toString()));
    }

    @Test
    void checkIfDue() {
        boolean due = testList.get(1).checkIfDue();
        assertFalse(due == true);
    }


}