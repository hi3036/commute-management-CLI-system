package Commuting_to_School;

import java.util.Scanner;

class Original_Password {
    private static String Password = "testPassword";

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

class School_scanner {
    enum Judge_Select {
        True, False, Cancel
    }
    
    private Scanner scanner;
    
    School_scanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean Input_password() {//パスワード入力処理
        String input_text = scanner.nextLine();
        if (input_text.equals(Original_Password.getPassword(input_text))) {
            return true;
        } else {
            if (input_text.equals("cancel"));//キャンセル処理 使う予定なし
            return false;
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

}

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
                メインメニュー (Main menue) 
                1: 名簿表示 (Show member)
                2: 名簿管理（追加 / 削除）(add / remove)
                3: パスワードの変更 (Change the password)
                4: ログアウト (Logout)
                """);
    }
}


public class Way_of_commuting {
    public static void main(String[] args) throws Mismatch_Normal_Exception {
        
        

    }

    public static void run() {

    }

    public static void Login_service() throws Mismatch_Normal_Exception {//ログイン状態ですることをまとめる
        Menu_operation.Start_screen();
        Login();

        Menu_operation.Open_service();
    }

    public static void Login() throws Mismatch_Normal_Exception {
        boolean Enter = false;
        do { 
            try {
                School_scanner input = new School_scanner(new Scanner(System.in));
                boolean Enter_judge =  input.Input_password();

                if(Enter_judge) {
                    Enter = true;
                } else {//Enter_judge = false
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
    }


}