package college.users;

import college.book.Book;
import college.book.BookList;
import college.book.BorrowedBook;
import college.library.Lender;
import college.library.Library;
import college.library.Request;

import java.util.ArrayList;
import java.util.Objects;

public class User {

    private final String name;
    private final String regNo;
    private final Position pos;
    private ArrayList<BorrowedBook> borrowedBooks;

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
                this.borrowedBooks.remove(bk);
                BookList booklist = Library.getInstance().getBooks(bk.getISBN());
                //set one of the unavailable books to available
                Book b = booklist.getReturnedCopy();
                b.setAvailable(true);
            }
        }
    }

    public void returnBook(String ISBN) {
        BorrowedBook book = this.borrowedBooks.stream().filter(b ->
                b.getISBN().equals(ISBN)
        ).findFirst().orElse(null);
        //Remove from list
        this.borrowedBooks.remove(book);
        BookList bk = Library.getInstance().getBooks(ISBN);
        //set one of the unavailable books to available
        Book b = bk.getReturnedCopy();
        b.setAvailable(true);
    }


    public void borrowBook(BorrowedBook book) {
        this.borrowedBooks.add(book);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return Objects.equals(regNo, other.getRegNo());
    }

}
