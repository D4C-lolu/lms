package college.book;

import java.util.Objects;
import java.util.UUID;

/**
 * A class meant to represent a book object in the library
 */
public class Book {
    private final String serialNo;
    private final String bookName;
    private final String authorName;
    private  final String subject;
    private final String ISBN;
    private boolean isAvailable;


    /**
     *
     * @param bookName string representing the name of the book
     * @param authorName string containing the name of the book's author
     * @param subject string containing the subject title
     * @param ISBN A string to uniquely identify a type of book
     * @param isAvailable boolean indicating whether this copy of the book is available
     */
    public Book(String bookName, String authorName, String subject, String ISBN, boolean isAvailable) {
        this.serialNo = generateSerialNo();
        this.bookName = bookName;
        this.authorName = authorName;
        this.subject = subject;
        this.ISBN = ISBN;
        this.isAvailable = isAvailable;
    }

    /**
     * @return serialNo the serial number of the book
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     *
     * @return a UUID string representing the serial number of the book
     */
    private String generateSerialNo() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     *
     * @return the name of the book
     */
    public String getBookName() {
        return bookName;
    }

    /**
     *
     * @return the name of the book's author
     */
    public String getAuthorName() {
        return authorName;
    }



    /**
     *
     * @return the subject title of the book
     */
    public String getSubject() {
        return subject;
    }

    public String getISBN() {
        return ISBN;
    }

    /**
     *
     * @return a boolean indicating if the book is available or not
     */

    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     *
     * @param available a boolean indicating if the book is available
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        return Objects.equals(ISBN, other.ISBN);
    }
}
