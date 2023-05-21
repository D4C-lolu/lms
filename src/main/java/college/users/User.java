package college.users;

import college.book.*;
import college.library.Lender;
import college.library.Request;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;

public class User {

    private final String name;

    private final String regNo;
    private final Position pos;
    private ArrayList<BorrowedBook> borrowedBooks;

    private static AtomicLong idCounter = new AtomicLong();

    public User(String name, String regNo, Position pos) {
        this.name = name;
        this.pos = pos;
        this.regNo = regNo;
        this.borrowedBooks = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public String getRegNo() {
        return regNo;
    }

    public Position getPos() {
        return pos;
    }

    public ArrayList<BorrowedBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public boolean checkAlreadyBorrowed(String ISBN) {
        for (BorrowedBook bk : this.borrowedBooks) {
            if (bk.getISBN().equals(ISBN)) {
                return true;
            }
        }
        return false;
    }

    public void submitRequest(String ISBN) {
        Request request = new Request(ISBN, this);
        if (Lender.getInstance().getQueuedRequests().contains(request)) {
            System.out.println("A request for this book has already been made!");
            return;
        }
        Lender.getInstance().addRequests(request);
    }

    public void cancelRequest(String ISBN) {
        Lender.getInstance().removeRequest(ISBN, this);
    }

    public void removeDueBooks() {

        for (BorrowedBook bk : this.borrowedBooks) {
            if (bk.checkIfDue()) {
                bk.setAvailable(true);
                this.borrowedBooks.remove(bk);
            }
        }
    }

    public void borrowBook(BorrowedBook book) {
        this.borrowedBooks.add(book);
    }


}
