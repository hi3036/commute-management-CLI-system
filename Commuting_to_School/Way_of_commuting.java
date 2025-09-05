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

        public int search_Select_Number(Select_Number Number) {//enum 列挙型のSelect_Numberを安全にint型と結び付ける
            Select_Number box[] = Select_Number.values();
            for (int i = 0; i < box.length; i++) {
                if (box[i] == Number) {
                    return i;
                }
            }
            return 0;//見つからなかったら 0 を返す
        }

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

    public Select_Number Input_Select_Number(int menu_start, int menu_end) {//選択肢を答える入力処理
        Select_Number choices[] = Select_Number.values();
        int input_number;

        try {
            input_number = scanner.nextInt();

            boolean judge_scope = false;
            for (int i = menu_start; i <= menu_end; i++) {//選択肢の範囲内なら true にする
                if (input_number == i) judge_scope = true;
            }
            if (judge_scope || (input_number < choices.length)) {//選択肢の範囲内で、 true なら正しい
                
                return choices[input_number];//正しい入力なら値を返す
            } else {//範囲外なら例外
                System.out.println("\n" + menu_start +" ~ " + menu_end + "までの数字を入れてください. その値は無効です.");
                //throw new Mismatch_Select_Number_Exception(menu_start+" ~ " + menu_end + "までの数字を入れてください. その値は無効です.");
                return choices[0];
            }

        }catch (InputMismatchException e) {
            System.out.println("\nそれは数字ではないです. その値は無効です.");
            return choices[0];//choices[0] = Zero とし、Zeroは使用しないので例外処理に使う
        }
    }

    @Override
    public void close() {
        /* Overrideにより処理を無くす */
    }

    public void tryClose() {
        scanner.close();
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

class No_result_Exception extends Exception {
    public No_result_Exception(String message) {
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
class Service_operation {
    enum Screen_state {
        Start_Screen,
            Login,
                Menu_Screen,
                    Show_Member,
                        /* メンバーの一覧を表示する処理 */
                        Back_from_Show_Member,//メニュー画面に戻る（表示したらまたメニューに戻るようにすれば必要ない）
                    
                    Operate_Member,
                        Add_Member,
                            /* メンバーの追加処理 */
                            Back_from_Add_Member,//Add or Remove 画面に戻る、またはキャンセル

                        Remove_Member,
                            /* メンバーの削除処理 */
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

    static void Login() {

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

    static void Operate_Member() {
        Separate_screen();
        System.out.println("""
                名簿 (Main menu)

                1: メンバーおよび情報の追加
                2: メンバーの編集
                3: メインメニューに戻る
                """);
    }

    static void Logout() {
        Separate_screen();
        System.out.println("""
                ログアウトしました.
                You logged out.

                """);
    }
}


public class Way_of_commuting {
    static Service_operation.Screen_state Login() {
        try (School_scanner input = new School_scanner(new Scanner(System.in));) {
            School_scanner.Judge_Select input_result = input.Input_password();

            if(input_result == School_scanner.Judge_Select.True) {
                return Service_operation.Screen_state.Menu_Screen;
            } else if (input_result == School_scanner.Judge_Select.Cancel) {
                return Service_operation.Screen_state.Start_Screen;//キャンセル
            } else {//Enter_judge == School_scanner.Judge_Select.False
                throw new Mismatch_Normal_Exception("""

                    パスワードの入力に失敗しました. 入力が無効です. もう一度おねがいします.
                    ホームに戻る場合は[cancel]の入力をお願いします.
                        """);
            }
        } catch (Mismatch_Normal_Exception e) {
            System.out.println(e);
            return Service_operation.Screen_state.Login;//また元の画面に移る
        }
    }

    static Service_operation.Screen_state Menu_Screen() {
        try (School_scanner input = new School_scanner(new Scanner(System.in));) {
            School_scanner.Select_Number input_result = input.Input_Select_Number(1,4);
            switch (input_result) {
                case School_scanner.Select_Number.One:
                    return Service_operation.Screen_state.Show_Member;

                case School_scanner.Select_Number.Two:
                    return Service_operation.Screen_state.Operate_Member;

                case School_scanner.Select_Number.Three:
                    return Service_operation.Screen_state.Change_password;

                case School_scanner.Select_Number.Four:
                    return Service_operation.Screen_state.Logout;

                default:
                        throw new Mismatch_Select_Number_Exception("再度入力してください");
            }
        } catch (Mismatch_Select_Number_Exception e) {
            System.out.println(e);
            return Service_operation.Screen_state.Menu_Screen;
        }
    }
    
    static void run() {
        Service_operation.Screen_state Current_Scrren_state = Service_operation.Screen_state.Start_Screen;
        Current_Scrren_state = Service_operation.Screen_state.Menu_Screen;//テスト用
        boolean Logout_judge = false;
        do {
            switch (Current_Scrren_state) {
                case Service_operation.Screen_state.Start_Screen://スタート画面
                    Service_operation.Start_screen();//スタート画面を出す
                    Current_Scrren_state = Service_operation.Screen_state.Login;
                    break;

                case Service_operation.Screen_state.Login://ログイン画面・パスワード入力画面
                    Current_Scrren_state = Login();
                    break;

                case Service_operation.Screen_state.Menu_Screen://メニュー画面
                    Service_operation.Menu_Screen();
                    Current_Scrren_state = Menu_Screen();
                    break;




                case Service_operation.Screen_state.Logout://ログアウト画面
                    Current_Scrren_state = Service_operation.Screen_state.Start_Screen;
                    Logout_judge = true;
                    break;

                default:
                    throw new AssertionError();
            }
            System.out.println(Current_Scrren_state);
        } while (!Logout_judge);

    }



    public static void main(String[] args) throws Mismatch_Normal_Exception {
        run();
    }
}