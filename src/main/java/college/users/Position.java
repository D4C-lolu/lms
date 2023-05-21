package college.users;

public enum Position {
    JS1(0),
    JS2(1),
    JS3(2),
    SS1(3),
    SS2(4),
    SS3(5),
    Teacher(6);

    final int val;

    Position(int val) {
        this.val = val;
    }
}
