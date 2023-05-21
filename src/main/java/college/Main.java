package college;

import college.LMS.LMS;
import college.book.Book;
import college.book.BookList;
import college.book.BorrowedBook;
import college.library.Lender;
import college.library.Library;
import college.users.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static Scanner sc;

    public static void displayMenu() {
        logger.trace("Enter '1' to see the list of available books.");
        logger.trace("Enter '2' to see the list of books in your library.");
        logger.trace("Enter '3' to the books still pending.");
        logger.trace("Enter '4' to request a book.");
        logger.trace("Enter '5' to cancel a request. ");
        logger.trace("Or enter 0 to end the program");
    }

    public static User logIn() {
        sc = new Scanner(System.in);
        logger.info("\nPlease enter a valid user ID to log in or 'x' to exit : ");
        String input = "";
        String endChar = "x";
        User user = null;
        do {
            try {
                input = sc.nextLine();
                if (input.equals(endChar)) {
                    logger.trace("\nExiting program");
                    System.exit(0);
                }
                user = LMS.getInstance().findUser(input);

            } catch (final NumberFormatException e) {
                logger.warn("\nPlease enter a valid number: ");
            } catch (final NullPointerException e) {
                logger.error("The number above does not match any user");
                logger.info("Please enter a valid user id: ");

            }
        }
        while (!input.equals(endChar) || user == null);

        return user;
    }

    public static void getAvailableBooks() {
        ArrayList<BookList> bookList = Library.getInstance().getAvailableBooks();

        if (bookList.size() != 0) {
            logger.trace("The currently available books in the library are: ");
            logger.trace("\n=====================================");
            bookList.forEach(bk -> {
                int n = bk.getNumofBorrowedCopies();
                Book b = bk.getCopy();
                logger.trace("\n{} by {}. Subject:{} with ISBN: {} .({} copies available)", b.getBookName(), b.getAuthorName(), b.getSubject(), b.getISBN(), n);
            });
        }
    }

    public static void getBorrowedBooks(User user) {
        ArrayList<BorrowedBook> bk = user.getBorrowedBooks();
        logger.trace("You currently have {} books in your library.", bk.size());
        if (bk.size() != 0) {
            logger.trace("\nYour library currently contains: ");
            logger.trace("\n=====================================");
            bk.forEach(b -> {
                logger.trace("\n{} by {}.Subject: {}", b, b.getAuthorName(), b.getSubject());
            });
            logger.trace("\n=====================================");
        } else {
            logger.trace("\nYou don't currently have any books in your library. ");
        }
    }

    public static void viewPendingBooks(User user) {
        ArrayList<Book> pendingBooks = Lender.getInstance().getPendingBooks(user);
        if (pendingBooks.size() != 0) {
            logger.trace("\nYou are currently requesting for: ");
            logger.trace("\n=====================================");
            pendingBooks.forEach(pb -> {
                logger.trace("\n{} by {}.Subject: {}", pb.getBookName(), pb.getAuthorName(), pb.getSubject());
            });
            logger.trace("\n=====================================");
        } else {
            logger.trace("\nYou don't currently have any books in your library. ");
        }
    }

    public static void requestBook(User user) {
        try {
            String escapeChar = "/";
            String input = "";
            sc = new Scanner(System.in);
            do {
                System.out.println("Enter the ISBN of the book you wish to borrow: ");
                input = sc.nextLine();
                //Exit if '/' is entered
                if (input.equals(escapeChar)) {
                    break;
                }
                //Search for book
                BookList bk = Library.getInstance().getBooks(input);
                if (bk == null) {
                    logger.error("Invalid ISBN. No such book exists in our library yet.");
                    logger.info("Please enter a valid ISBN or " + escapeChar + " to return to the main menu");
                } else {
                    Boolean available = bk.checkIfAvailable();
                    //reserve a copy
                    user.submitRequest(input);
                    if (!available) {
                        logger.trace("Apologies but it seems we're all oyt of copies of that book");
                        logger.trace("The book will be added to your library when it becomes available");
                    } else {
                        logger.trace("The book has been added to your library for the next 7 days. PLease enjoy");
                    }
                }
            } while (!input.equals(escapeChar));
        } catch (Exception e) {
            logger.error("Something went wrong.");
            logger.error("Please try again.");

        }

    }

    public static void cancelRequest(User user) {
        try {
            String escapeChar = "/";
            String input = "";
            sc = new Scanner(System.in);
            do {
                System.out.println("Enter the ISBN of the request you wish to cancel: ");
                input = sc.nextLine();
                //Exit if '/' is entered
                if (input.equals(escapeChar)) {
                    break;
                }
                //Check if ISBN exists
                BookList bk = Library.getInstance().getBooks(input);
                if (bk == null) {
                    logger.error("Invalid ISBN. No such book exists in our library yet.");
                    logger.info("Please enter a valid ISBN or " + escapeChar + " to return to the main menu");
                    continue;
                }
                //Remove the request
                try {
                    user.cancelRequest(input);
                    logger.trace("The request has been removed from your list");
                } catch (Exception e) {
                    logger.trace("That book is not part of your current requests");
                }

            } while (!input.equals(escapeChar));
        } catch (Exception e) {
            logger.error("Something went wrong.");
            logger.error("Please try again.");

        }
    }

    public static void returnBook(User user) {
        try {
            String escapeChar = "/";
            String input = "";
            sc = new Scanner(System.in);
            do {
                System.out.println("Enter the ISBN of the book you wish to return: ");
                input = sc.nextLine();
                //Exit if '/' is entered
                if (input.equals(escapeChar)) {
                    break;
                }
                //Check if ISBN exists
                BookList bk = Library.getInstance().getBooks(input);
                if (bk == null) {
                    logger.error("Invalid ISBN. No such book exists in our library yet.");
                    logger.info("Please enter a valid ISBN or " + escapeChar + " to return to the main menu");
                    continue;
                }
                //Return the book
                try {
                    user.returnBook(input);
                    logger.trace("The book has been removed from your library");
                } catch (Exception e) {
                    logger.trace("That book is not part of your current requests");
                }

            } while (!input.equals(escapeChar));
        } catch (Exception e) {
            logger.error("Something went wrong.");
            logger.error("Please try again.");

        }
    }

    public static void main(String[] args) {


        logger.trace("\nWelcome to LMS.");
        User currUser = logIn();
        logger.trace("Good day {} .", currUser.getName());
        sc = new Scanner(System.in);
        int choice = -1;
        int end = 0;
        do {
            logger.trace("======================================");
            displayMenu();
            logger.info("\nEnter your response here: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 0:
                        logger.info("Exiting Program...");
                        System.exit(0);
                    case 1:
                        getAvailableBooks();
                        break;
                    case 2:
                        getBorrowedBooks(currUser);
                        break;
                    case 3:
                        viewPendingBooks(currUser);
                        break;
                    case 4:
                        requestBook(currUser);
                        break;
                    case 5:
                        cancelRequest(currUser);
                        break;
                    case 6:
                        returnBook(currUser);
                        break;
                    default:
                        logger.warn("\nPlease enter a number between 0 and 5 ");
                }
            } catch (final NumberFormatException e) {
                logger.warn("\nPlease enter a valid number: ");
            }
        }
        while (choice != end);

//
    }

    public void setUp() {
        logger.trace("\nInitialization...........................");

        //Create Dummy Books

    }
}