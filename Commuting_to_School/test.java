package Commuting_to_School;

public class test {
    static enum judge_select {
        yes, no, middle
    }
    public static void main(String[] args) {
        System.out.println("""
            Welcome to System of School's Commuting.
            Please Password.
            :"""
        );

        boolean judge = false;

        judge_select value = judge_select.middle;

        if (value == judge_select.middle) {
            System.out.println("middle");
        }

        test_case1 test = new test_case1("神");
        System.out.println(test);

        test_enum fluit;
        fluit = test_enum.apple;
        fluit.showMessage();
    }
}

class test_case1 {
    String message;
    test_case1(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return message+" と言います！";
    }
}

enum test_enum {
    apple("これはリンゴです。"),
    banana("これはバナナです。"),
    orange("これはオレンジです。");

    String message;

    test_enum(String message) {
        this.message = message;
    }

    public void showMessage() {
        System.out.println(this.message);
    }
}