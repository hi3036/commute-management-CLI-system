package Commuting_to_School;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
abstract class Vehicle {
    private String type;
    private int distance;
    private int minute;

    Vehicle(String type, int distance, int minute) {
        this.type = type;
        this.distance = distance;
        this.minute = minute;
    }

    public String getType() { return this.type; }
    public int distance() { return this.distance; }
    public int getMinute() { return this.minute; }
}

class Bike extends Vehicle {
    Bike(String type, int distance, int minute) {
        super(type, distance, minute);
    }
}

class Bicycle extends Vehicle {
    Bicycle(String type, int distance, int minute) {
        super(type, distance, minute);
    }
}

class Car extends Vehicle {
    Car(String type, int distance, int minute) {
        super(type, distance, minute);
    }
}

class Train extends Vehicle {
    Train(String type, int distance, int minute) {
        super(type, distance, minute);
    }
}

class Walk extends Vehicle {
    Walk(String type, int distance, int minute) {
        super(type, distance, minute);
    }
}

class About extends Vehicle {
    About(String type, int distance, int minute) {
        super(type, distance, minute);
    }
}


//---------- ---------- 学校全体の統括 ---------- ----------
abstract class School_Member {//学校全体のメンバー
    private String name;
    private String position;
    private int number;
    
    School_Member(String name, String position) {//事務員、教員用
        this.name = name;
        this.position = position;
    }
    School_Member(String name, int number) {//生徒用
        this.name = name;
        this.number = number;
    }

    private List<School_Member> All_member = new ArrayList<>();//学校全体のメンバーのリスト

    public void Show_MemberList() {
        for (School_Member member : All_member) {
            member.showValue();
        }
    }

    public void AddMembr(School_Member member) {//メンバーの追加
        All_member.add(member);
    }

    public void RemoveMembr(School_Member member) {//メンバーの削除
        All_member.remove(member);
    }

    public String getName() { return this.name; }
    public String getPosition() { return this.position; }
    public int getNumber() { return this.number; }

    abstract void showValue();

}

//---------- ---------- 学校関係者 ---------- ----------
interface Operate_VehicleList {
    public void AddVehicle(Vehicle vehicle);

    public void RemoveVehicle(Vehicle vehicle);
}
class School_office_staff extends School_Member implements Operate_VehicleList {
    private List<Vehicle> Office_staff_vehicles = new ArrayList<>();

    public School_office_staff(String name, String position) {
        super(name, position);
    }

    @Override
    public void showValue() {
        System.out.println("事務員 :" + getName() + " 役職:" + getPosition());
    }

    @Override
    public void AddVehicle(Vehicle vehicle) {
        Office_staff_vehicles.add(vehicle);
    }

    @Override
    public void RemoveVehicle(Vehicle vehicle) {
        Office_staff_vehicles.remove(vehicle);
    }
}

class Teacher extends School_Member implements Operate_VehicleList {
    private List<Vehicle> Teacher_vehicles = new ArrayList<>();

    public Teacher(String name, String position) {
        super(name, position);
    }

    @Override
    public void showValue() {
        System.out.println(" 教師 :" + getName() + " 役職:" + getPosition());
    }

    @Override
    public void AddVehicle(Vehicle vehicle) {
       Teacher_vehicles.add(vehicle);
    }

    @Override
    public void RemoveVehicle(Vehicle vehicle) {
        Teacher_vehicles.remove(vehicle);
    }
}

class Student extends School_Member implements Operate_VehicleList {
    private List<Vehicle> Student_vehicles = new ArrayList<>();

    public Student(String name, int number) {
        super(name, number);
    }

    @Override
    public void showValue() {
        System.out.println(" 生徒 :" + getName() + " 生徒番号:" + getNumber());
    }

    @Override
    public void AddVehicle(Vehicle vehicle) {
        Student_vehicles.add(vehicle);
    }

    @Override
    public void RemoveVehicle(Vehicle vehicle) {
        Student_vehicles.remove(vehicle);
    }
}

//---------- ---------- メニュー画面 ---------- ----------
class Menu_operation {
    enum Screen_state {
        Start_Screen,
            Login,
                Menu_Screen,
                    Show_Member,
                        /* メンバーの一覧を表示する処理 */
                        Back_from_Show_Member,//メニュー画面に戻る（表示したらまたメニューに戻るようにすれば必要ない）
                    
                    Add_Remove_Member,
                        Add_Member,
                            /* メンバーの追加処理 */
                            Back_from_Add_Member,//Add or Remove 画面に戻る、またはキャンセル

                        Remove_Member,
                            Back_from_Remove_Member,

                        Back_from_Add_Remove_Member,//メニュー画面に戻る
                    
                    Change_password,
                        Input_password,
                        Back_from_Change_password,//メニュー画面に戻る
                    
                    Logout,
    }

    static void Separate_screen() {
        System.out.println();
        for (int i = 0; i < 30; i++) System.out.print("-");
        System.out.println();
    }
    static void Separate_screen(int count) {
        System.out.println();
        for (int i = 0; i < count; i++) System.out.print("-");
        System.out.println();
    }
    
    static void Start_screen() {//最初のスタート画面
        Separate_screen();
        System.out.print("""
            ようこそ. これは通学、通勤手段の簡易管理システムです
            Welcome to System of School's Commuting.
            Please Password.
            """);
        System.out.print(": ");
    }

    static void Logout_from_system() {
        Separate_screen();
        System.out.println("""
            ログアウトしますか？ You will logout this system.
            Reary?[y/n]
            :
            """);
    }

    static void Menu_Screen() {//軸となるメニュー画面
        Separate_screen();
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
        Separate_screen();
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
            Menu_operation.Menu_Screen();
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

        //Menu_operation.Menu_Screen();
        Main_menu_service();
        System.out.println("次回はここから！");
    }



    public static void main(String[] args) throws Mismatch_Normal_Exception {
        run();
    }
}