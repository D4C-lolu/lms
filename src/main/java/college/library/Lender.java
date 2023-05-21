package college.library;

import college.book.Book;
import college.book.BookList;
import college.book.BorrowedBook;
import college.users.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Lender {
    private static Lender _instance;

    static {
        try {
            _instance = new Lender();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating singleton instance");
        }
    }

    private PriorityQueue<Request> queuedRequests;

    private Lender() {
        this.queuedRequests = new PriorityQueue<>(10, new RequestComparator());
    }

    public static Lender getInstance() {
        return _instance;
    }

    public PriorityQueue<Request> getQueuedRequests() {
        return queuedRequests;
    }

    public ArrayList<Request> getUserRequests(User user) {
        ArrayList<Request> requests = new ArrayList<>(this.queuedRequests);
        String num = user.getRegNo();
        Predicate<Request> byRegNo = r1 -> r1.getUser().getRegNo().equals(num);
        return requests.stream().filter(byRegNo).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Book> getPendingBooks(User user) {
        ArrayList<Request> userRequests = getUserRequests(user);

        ArrayList<Book> pendingBooks = new ArrayList<>();
        userRequests.forEach(r -> {
            Book bk = Library.getInstance().getBooks(r.getISBN()).getCopy();
            pendingBooks.add(bk);
        });

        return pendingBooks;
    }

    public void addRequests(Request... request) {
        //Add to PQ
        for (int i = 0; i < request.length; i++) {
            queuedRequests.add(request[i]);
        }
        processRequests();
    }

    public void removeRequest(String ISBN, User user) {
        this.queuedRequests.remove(new Request(ISBN, user));
    }

    public void processRequests() {

        Request[] requests = this.queuedRequests.toArray(new Request[0]);
        Arrays.sort(requests, new RequestComparator());
        for (Request r : requests) {
            //Check if book is available
            Boolean available = Library.getInstance().checkAvailability(r.getISBN());
            //check if user currently has a copy
            if (r.getUser().checkAlreadyBorrowed(r.getISBN())) {
                System.out.println("List of Borrowed books already contains this book!");
                continue;
            }
            if (available) {
                //Get a copy from the booklist and mark it as taken
                BookList bk = Library.getInstance().getBooks(r.getISBN());
                Book b = bk.getCopy();
                //Set Availability to false
                b.setAvailable(false);
                //Create new BorrowedBook object and assign to user
                BorrowedBook borrowedCopy = new BorrowedBook(b);
                User user = r.getUser();
                user.borrowBook(borrowedCopy);
                //remove rq from Priority Queue
                this.queuedRequests.remove(r);
            }

        }
    }
}
