package college.book;

import java.time.LocalDate;

public class BorrowedBook extends Book {

    private final LocalDate dateBorrowed;
    private final LocalDate dueDate;

    //7 days in ms


    public BorrowedBook(String bookName, String authorName, String subject, String ISBN, boolean isAvailable) {
        super(bookName, authorName, subject, ISBN, isAvailable);
        this.dateBorrowed = LocalDate.now();
        this.dueDate = LocalDate.now().plusDays(7);
    }

    public BorrowedBook(Book book) {
        super(book.getBookName(), book.getAuthorName(), book.getSubject(), book.getISBN(), book.isAvailable());
        this.dateBorrowed = LocalDate.now();
        this.dueDate = LocalDate.now().plusDays(7);
    }

    public LocalDate getDateBorrowed() {
        return dateBorrowed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean checkIfDue() {
        LocalDate currDate = LocalDate.now();
        return currDate.isAfter(this.getDueDate());

    }

    @Override
    public String toString() {
        return this.getBookName() + "{ - borrowed on "
                + dateBorrowed +
                ", dueDate=" + dueDate +
                "}\n";
    }
}
