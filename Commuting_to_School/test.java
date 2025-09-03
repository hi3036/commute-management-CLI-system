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

/*
private boolean judge_Yes_No(String input_text) {
        String Low_input_text = input_text.toLowerCase();
        //input_text.equals("y") || input_text.equals("n") || input_text.equals("yes") || input_text.equals("no")
        if (input_text.equals("y")) {
            return true;
        } else if (input_text.equals("n")) {
            return true;
        } else if (input_text.equals("yes")) {
            return true;
        } else if (input_text.equals("no")) {
            return true;
        } else {
            return false;
        }
    }
 */