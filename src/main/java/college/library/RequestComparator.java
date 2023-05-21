package college.library;

import java.util.Comparator;

public class RequestComparator implements Comparator<Request> {
    public int compare(Request r1, Request r2) {
        int a = r1.getUser().getPos().ordinal();
        int b = r2.getUser().getPos().ordinal();
        if (a < b) return 1;
        if (a > b) return -1;
        return 0;
    }

}
