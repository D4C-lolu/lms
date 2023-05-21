package college.book;

import java.util.Date;

public class BorrowedBook extends Book {

    private final Date dateBorrowed;
    private final Date dueDate;

    //7 days in ms
    private static final long allowedTime = 604800000;

    public BorrowedBook(String bookName, String authorName, String subject, String ISBN, boolean isAvailable) {
        super(bookName, authorName, subject, ISBN, isAvailable);
        this.dateBorrowed = new Date();
        long dueTime = this.dateBorrowed.getTime() + allowedTime;
        this.dueDate = new Date(dueTime);
    }

    public BorrowedBook(Book book) {
        super(book.getBookName(), book.getAuthorName(), book.getSubject(), book.getISBN(), book.isAvailable());
        this.dateBorrowed = new Date();
        long dueTime = this.dateBorrowed.getTime() + allowedTime;
        this.dueDate = new Date(dueTime);
    }

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getTimeLeft() {
        // Calculate time difference
        // in milliseconds
        Date now = new Date();
        long difference_In_Time
                = now.getTime() - getDueDate().getTime();

        // Calculate time difference in
        // seconds, minutes, hours, years,
        // and days
        long difference_In_Seconds
                = (difference_In_Time
                / 1000)
                % 60;

        long difference_In_Minutes
                = (difference_In_Time
                / (1000 * 60))
                % 60;

        long difference_In_Hours
                = (difference_In_Time
                / (1000 * 60 * 60))
                % 24;

        long difference_In_Years
                = (difference_In_Time
                / (1000l * 60 * 60 * 24 * 365));

        long difference_In_Days
                = (difference_In_Time
                / (1000 * 60 * 60 * 24))
                % 365;

        // Print the date difference in
        // years, in days, in hours, in
        // minutes, and in seconds

        System.out.print(
                "Difference "
                        + "between two dates is: ");

        System.out.println(
                difference_In_Years
                        + " years, "
                        + difference_In_Days
                        + " days, "
                        + difference_In_Hours
                        + " hours, "
                        + difference_In_Minutes
                        + " minutes, "
                        + difference_In_Seconds
                        + " seconds");

        return new Date(difference_In_Time);
    }

    public boolean checkIfDue() {
        Date currDate = new Date();
        return currDate.after(this.getDueDate());

    }

    @Override
    public String toString() {
        return "BorrowedBook{" +
                "dateBorrowed=" + dateBorrowed +
                ", dueDate=" + dueDate +
                '}';
    }
}
