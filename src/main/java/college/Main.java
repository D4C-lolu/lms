package college;

import college.LMS.LMS;
import college.book.Book;
import college.book.BookList;
import college.book.BorrowedBook;
import college.library.Lender;
import college.library.Library;
import college.library.Request;
import college.users.Position;
import college.users.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static Scanner sc;

    private static void run() {
        LMS.getInstance().checkOverDue();
        Lender.getInstance().processRequests();
    }

    public static void displayMenu() {
        logger.info("Enter '1' to see the list of available books.");
        logger.info("Enter '2' to see the list of books in your library.");
        logger.info("Enter '3' to the books still pending.");
        logger.info("Enter '4' to request a book.");
        logger.info("Enter '5' to cancel a request. ");
        logger.info("Or enter 0 to end the program");
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
                    logger.info("\nExiting program");
                    System.exit(0);
                }
                user = LMS.getInstance().findUser(input);
                if (user == null) {
                    logger.error("The number above does not match any user");
                    logger.info("Please enter a valid user id: ");
                } else {
                    break;
                }
            } catch (final NumberFormatException e) {
                logger.warn("\nPlease enter a valid number: ");
            } catch (final NullPointerException e) {
                logger.error("The number above does not match any user");
                logger.info("Please enter a valid user id: ");
            }
        }
        while (!input.equals(endChar));

        return user;
    }

    public static void getAvailableBooks() {
        ArrayList<BookList> bookList = Library.getInstance().getAvailableBooks();

        if (bookList.size() != 0) {
            logger.info("The currently available books in the library are: ");
            logger.info("\n=====================================");
            bookList.forEach(bk -> {
                int n = bk.getAvailableCopies();
                Book b = bk.getCopy();
                logger.info("\n{} by {}. Subject: {} with ISBN: {} .({} copies available)", b.getBookName(), b.getAuthorName(), b.getSubject(), b.getISBN(), n);
            });
        }
    }

    public static void getBorrowedBooks(User user) {
        ArrayList<BorrowedBook> bk = user.getBorrowedBooks();
        logger.info("You currently have {} books in your library.", bk.size());
        if (bk.size() != 0) {
            logger.info("\nYour library currently contains: ");
            logger.info("\n=====================================");
            bk.forEach(b -> {
                logger.info("\n{} by {}.Subject: {}", b, b.getAuthorName(), b.getSubject());
            });
            logger.info("\n=====================================");
        } else {
            logger.info("\nYou don't currently have any books in your library. ");
        }
    }

    public static void viewPendingBooks(User user) {
        ArrayList<Book> pendingBooks = Lender.getInstance().getPendingBooks(user);
        if (pendingBooks.size() != 0) {
            logger.info("\nYou are currently requesting for: ");
            logger.info("\n=====================================");
            pendingBooks.forEach(pb -> {
                logger.info("\n{} by {}.Subject: {}", pb.getBookName(), pb.getAuthorName(), pb.getSubject());
            });
            logger.info("\n=====================================");
        } else {
            logger.info("\nYou don't currently have any pending requests. ");
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
                        logger.info("Apologies but it seems we're all oyt of copies of that book");
                        logger.info("The book will be added to your library when it becomes available");
                    } else {
                        logger.info("The book has been added to your library for the next 7 days. PLease enjoy");
                    }
                    break;
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
                    logger.info("The request has been removed from your list");
                } catch (Exception e) {
                    logger.info("That book is not part of your current requests");
                }
                break;

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
                    logger.info("The book has been removed from your library");
                } catch (Exception e) {
                    logger.info("That book is not part of your current requests");
                }
                break;

            } while (!input.equals(escapeChar));
        } catch (Exception e) {
            logger.error("Something went wrong.");
            logger.error("Please try again.");

        }
    }

    public static void main(String[] args) {

        setUp();
        //Run code every 10 seconds
        ScheduledExecutorService executorService;
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(Main::run, 0, 10, TimeUnit.SECONDS);
        logger.info("\nWelcome to LMS.");
        User currUser = logIn();
        logger.info("Good day {}. Rank: ({}).", currUser.getName(), currUser.getPos());
        sc = new Scanner(System.in);
        int choice = -1;
        int end = 0;
        do {
            logger.info("======================================");
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

    public static void setUp() {
        logger.info("\nInitialization...........................");

        //Create Dummy Books
        Book book1 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book2 = new Book("Arrow of God", "Chinua Achebe", "Historical Fiction", "23223", true);
        Book book3 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book4 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book5 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book6 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book7 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);
        Book book8 = new Book("Things fall apart", "Chinua Achebe", "Historical Fiction", "10505", true);

        ArrayList<Book> bookList = new ArrayList<>();
        bookList.addAll(List.of(book1, book2, book3, book4, book5, book6, book7, book8));
        Library.getInstance().initialize(bookList);

        User user1 = new User("Roboute", "0013", Position.SS2);
        User user2 = new User("Konrad", "0008", Position.JS3);
        User user3 = new User("Khan", "0005", Position.Teacher);
        ArrayList<User> users = new ArrayList<>();
        users.addAll(List.of(user1, user2, user3));
        LMS.getInstance().initialize(users);


        Request r1 = new Request("23223", user2);
        Request r2 = new Request("23223", user3);
        ArrayList<Request> requests = new ArrayList<>();
        requests.addAll(List.of(r1, r2));
        Lender.getInstance().addRequests(requests);

        logger.info("\nDone");

    }
}