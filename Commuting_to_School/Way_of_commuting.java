package Commuting_to_School;

import java.util.InputMismatchException;
import java.util.Scanner;

class Original_Password {
    private static String Password = "firstPassword";

    public static String getPassword(String password) {
        //System.out.println("getPasswordが呼び出された");
        if (password.equals(Password)) {
            return Password;
        } else {
            return null;
        }
    }

    public static void changePassword(String password) {
        if (password.equals(Password)) {
            
            System.out.println();
        } else {
            System.out.println();
        }
    }
}

class School_scanner implements AutoCloseable {
    enum Judge_Select {
        True, False, Cancel
    }

    enum Select_Number {
        Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten;

        public void getSelect_Number(int number) {

        }
    }
    
    private Scanner scanner;
    
    School_scanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Judge_Select Input_password() {//パスワード入力処理
        String input_text = scanner.nextLine();

        if (input_text.equals(Original_Password.getPassword(input_text))) {
            return Judge_Select.True;
        } else if (input_text.equals("cancel")) {
            return Judge_Select.Cancel;
        } else {
            return Judge_Select.False;
        }
    }

    public boolean Input_Yes_No() throws Mismatch_Yes_No_Exception{//入力の際に[y/n]を求める際の処理
        String input_text = scanner.nextLine();

        if (!input_text.equals("y") || !input_text.equals("n") || !input_text.equals("yes") || !input_text.equals("no")) {
            throw new Mismatch_Yes_No_Exception("""
                    [y/n]に対し、["y", "n", "yes", "no", その他これらの大文字または大文字小文字の混合]で入力をしています.その入力は無効です.
                    再度入力をしてください.
                    """);
        }
        return true;
    }

    public Select_Number Input_Select_Number(int menu_start, int menu_end) throws Mismatch_Select_Number_Exception{//選択肢を答える入力処理
        Select_Number choices[] = Select_Number.values();
        int input_number;

        try {
            input_number = scanner.nextInt();

            boolean judge_scope = false;
            for (int i = menu_start; i <= menu_end; i++) {//選択肢の範囲内なら true 
                if (input_number == i) judge_scope = true;
            }
            if (!judge_scope || !(input_number < choices.length)) {//選択肢の範囲内になくて、 false のままなら、例外、また、用意していない数字も例外
                throw new Mismatch_Select_Number_Exception("\n数字の範囲が違います. その値は無効です.");
            }
            

            return choices[input_number];//正しい入力なら値を返す

        } catch (NumberFormatException e) {
            System.out.println(menu_start+" ~ " + menu_end + "までの数字を入れてください. その値は無効です.");
            return choices[0];//choices[0] = Zero とし、Zeroは使用しないので例外処理に使う
        } catch (InputMismatchException e) {
            System.out.println(menu_start+" ~ " + menu_end + "までの数字を入れてください. その値は無効です.");
            return choices[0];//choices[0] = Zero とし、Zeroは使用しないので例外処理に使う
        }
    }

    @Override
    public void close() {
        //scanner.close();
    }
}


//---------- ---------- 特定入力例外 ---------- ----------
class Mismatch_Yes_No_Exception extends Exception {
    public Mismatch_Yes_No_Exception(String message) {
        super(message);
    }
}

class Mismatch_Normal_Exception extends Exception {
    public Mismatch_Normal_Exception(String message) {
        super(message);
    }
}

class Mismatch_Select_Number_Exception extends Exception {
    public Mismatch_Select_Number_Exception(String message) {
        super(message);
    }
}


//---------- ---------- 通学方法 ---------- ----------
class Vehicle {

}

class Bike {
    
}

class Bicycle {

}

class Car {

}

class Train {

}

class Walk {

}

class About_way {

}


//---------- ---------- 学校関係者 ---------- ----------
class School_office_staff {

}

class Student {

}

class Teacher {

}


//---------- ---------- メニュー画面 ---------- ----------
class Menu_operation {
    /*
    private static String[] Login_TEXT = {
        "Welcome to System of School's Commuting.",
        "Please Password.",
        ":",
    };
    */
    
    static void Start_screen() {
        System.out.println("""
            ようこそ. これは通学、通勤手段の簡易管理システムです
            Welcome to System of School's Commuting.
            Please Password.
            :
            """);

    }

    static void Logout_from_system() {
        System.out.println("""
            ログアウトしますか？ You will logout this system.
            Reary?[y/n]
            :
            """);
    }

    static void Open_service() {
        System.out.println("""
                メインメニュー (Main menu) 
                1: 名簿表示 (Show member)
                2: 名簿管理（追加 / 削除）(add / remove)
                3: パスワードの変更 (Change the password)
                4: ログアウト (Logout)
                番号を選んでください.
                """);
    }

    static void Human_List_service() {
        System.out.println("""
                名簿 (Main menu) 
                1: メンバーおよび情報の追加
                2: メンバーの編集
                3: メインメニューに戻る
                """);
    }
}


public class Way_of_commuting {

    public static boolean Login() throws Mismatch_Normal_Exception {
        boolean Enter = false;
        do { 
            try (School_scanner input = new School_scanner(new Scanner(System.in))) {
                School_scanner.Judge_Select Enter_judge =  input.Input_password();

                if(Enter_judge == School_scanner.Judge_Select.True) {
                    Enter = true;
                } else if (Enter_judge == School_scanner.Judge_Select.Cancel) {
                    return false;//キャンセル
                } else {//Enter_judge == School_scanner.Judge_Select.False
                    throw new Mismatch_Normal_Exception("""

                        パスワード入力に失敗しました. 入力が無効です. もう一度おねがいします.
                        ホームに戻る場合は[cancel]の入力をお願いします.
                            """);
                }
            } catch (Mismatch_Normal_Exception e) {
                System.out.println(e);
                //e.printStackTrace();
                Enter = false;
            }
        } while (!Enter);
        //System.out.println("Watch Enter value :"+Enter);
        System.out.println("パスワード入力に成功しました.");
        return true;
    }

    public static boolean Login_service() throws Mismatch_Normal_Exception {//ログイン状態ですることをまとめる
        Menu_operation.Start_screen();
        boolean return_Start_screen = Login();
        if(!return_Start_screen) {//ログインせず、スタート画面にもどる処理
            return false;
        }
        return true;
    }

    public static void Main_menu_service() {
        boolean Enter_Main_menu = false;
        do { 
            Menu_operation.Open_service();
            School_scanner.Select_Number Enter_choice = null;
            try (School_scanner input = new School_scanner(new Scanner(System.in))) {
                Enter_choice = input.Input_Select_Number(1,4);
                
                System.out.println("You select " + Enter_choice + ".");
                /*
                 * 2025年9月3日からの作業
                 */
                Enter_Main_menu = true;
            } catch (Mismatch_Select_Number_Exception e) {
                System.out.println("エラーです.\n"+e);
                Enter_Main_menu = false;
            }
            if (Enter_choice == School_scanner.Select_Number.Zero) Enter_Main_menu = false;
        } while (!Enter_Main_menu);
    }

    public static void run() throws Mismatch_Normal_Exception {
        boolean Login_service_return;
        do {
            Login_service_return = Login_service();//ログインしつつ、falseが帰ってきたら最初に戻り、trueならば終了する
        } while (!Login_service_return);

        //Menu_operation.Open_service();
        Main_menu_service();
        System.out.println("次回はここから！");
    }



    public static void main(String[] args) throws Mismatch_Normal_Exception {
        run();
    }
}