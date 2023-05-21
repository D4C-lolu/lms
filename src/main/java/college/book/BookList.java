package college.book;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a list of copies of a particular book
 */
public class BookList {


    private ArrayList<Book> bookCopies;

    public BookList(ArrayList<Book> books) {
        this.bookCopies = books;
    }

    public BookList(Book... books) {
        this.bookCopies = new ArrayList<>();
        this.bookCopies.addAll(List.of(books));
    }

    public void addCopies(ArrayList<Book> books) {
        this.bookCopies.addAll(books);
    }

    public void addCopy(Book book) {
        this.bookCopies.add(book);
    }

    public int getAvailableCopies() {
        int num = 0;
        for (Book b : this.bookCopies) {
            if (b.isAvailable()) num += 1;
        }

        return num;
    }

    public boolean checkIfAvailable() {
        return this.getAvailableCopies() > 0;
    }

    public int getNumOfCopies() {
        return this.bookCopies.size();
    }

    public int getNumofBorrowedCopies() {
        return this.getNumOfCopies() - this.getAvailableCopies();
    }

    public Book getCopy() {
        return this.bookCopies.stream().filter(b -> b.isAvailable()).findFirst().orElse(null);
    }

    public Book getReturnedCopy() {
        return this.bookCopies.stream().filter(b -> !b.isAvailable()).findFirst().orElse(null);
    }

}
