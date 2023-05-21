package college.library;

import college.book.Book;
import college.book.BookList;

import java.util.ArrayList;
import java.util.TreeMap;

public class Library {

    private static Library _instance;
    private TreeMap<String, BookList> bookCollection = new TreeMap<String, BookList>();
    ;

    static {
        try {
            _instance = new Library();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating singleton instance");
        }
    }

    private Library() {

    }

    public void initialize(ArrayList<Book> books) {
        books.forEach(bk -> {
            //Create new book
            String name = bk.getBookName();
            String authorName = bk.getAuthorName();
            String subject = bk.getSubject();
            String ISBN = bk.getISBN();
            Boolean isAvailable = bk.isAvailable();
            Book b = new Book(name, authorName, subject, ISBN, isAvailable);
            //Add to Book Collection in the appropriate BookList
            this.bookCollection.computeIfAbsent(ISBN, k -> new BookList()).addCopy(b);
        });
    }

    public boolean checkAvailability(String ISBN) {
        BookList bk = this.getBooks(ISBN);
        if (bk == null) {
            System.out.println("That book does not exist in our catalogue!");
            return false;
        }
        return bk.checkIfAvailable();
    }

    public BookList getBooks(String ISBN) {
        return this.bookCollection.get(ISBN);
    }


    public static Library getInstance() {
        return _instance;
    }
}
