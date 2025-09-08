package Commuting_to_School;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class OriginalPassword {
    private static String Password = "firstPassword";

    public static String getPassword(String password) {
        //System.out.println("getPasswordが呼び出された");
        if (password.equals(Password)) {
            System.out.println("パスワードが通りました.");
            return Password;
        } else {
            return null;
        }
    }

    public static void changePassword(String newPassword) {
        Password = newPassword;
        System.out.println("新規パスワードに変更しました.");
    }
}

class SchoolScanner implements AutoCloseable {
    enum JudgeSelect {
        True, False, Cancel
    }

    enum SelectNumber {
        Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten;

        public int searchSelectNumber(SelectNumber Number) {//enum 列挙型のSelect_Numberを安全にint型と結び付ける
            SelectNumber box[] = SelectNumber.values();
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
    
    SchoolScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public JudgeSelect InputPassword() {//パスワード入力処理
        String input_text = scanner.nextLine();

        if (input_text.equals(OriginalPassword.getPassword(input_text))) {
            return JudgeSelect.True;
        } else if (input_text.equals("cancel")) {
            return JudgeSelect.Cancel;
        } else {
            return JudgeSelect.False;
        }
    }

    public String InputChangesPasswordPossible() {
        
        try {
            String newPassword = scanner.nextLine();
            if (newPassword.equals("cancel")) {
                return null;
            } else {
                return newPassword;
            }
        } catch (InputMismatchException e) {
            System.out.println("無効な入力です. 再度入力をお願いします.");
            return null;
        }
    }

    public boolean InputYesNo() {//入力の際に[y/n]を求める際の処理
        try {
            String input_text = scanner.nextLine().toLowerCase();
            if (input_text.equalsIgnoreCase("y") || input_text.equalsIgnoreCase("yes")) {
                System.out.println("ログアウトしました.");
                return true;
            } else if (input_text.equalsIgnoreCase("n") || input_text.equalsIgnoreCase("no")) {
                System.out.println("ログアウトを中止しました.");
                return false;
            } else {
                throw new MismatchYesNoException("""
                        \n[y/n]に対し、["y", "n", "yes", "no", その他これらの大文字または大文字小文字の混合]で入力をしています.その入力は無効です.
                        ログアウトを中止します.
                        """);
            }
        } catch (MismatchYesNoException e) {
            System.out.println(e);
            return false;
        } catch (InputMismatchException e) {
            System.out.println(e);
            return false;
        }
        
    }

    public SelectNumber InputSelectNumber(int menu_start, int menu_end) {//選択肢を答える入力処理
        SelectNumber choices[] = SelectNumber.values();
        try {
            int input_number = Integer.parseInt(scanner.nextLine());

            boolean judge_scope = false;
            for (int i = menu_start; i <= menu_end; i++) {//選択肢の範囲内なら true にする
                if (input_number == i) judge_scope = true;
            }
            if (judge_scope && (input_number < choices.length)) {//選択肢の範囲内で、 true なら正しい
                
                return choices[input_number];//正しい入力なら値を返す
            } else {//範囲外なら例外
                System.out.println("\n" + menu_start +" ~ " + menu_end + "までの数字を入れてください. その値は無効です.");
                return choices[0];
            }

        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("\nそれは数字ではないです. その値は無効です.");
            return choices[0];//choices[0] = Zero とし、Zeroは使用しないので例外処理に使う
        }
    }

    @Override
    public void close() {
        /* Overrideにより意図的に関数を無効化 */
    }

    public void tryClose() {
        scanner.close();
    }
}


//---------- ---------- 特定入力例外 ---------- ----------
class MismatchYesNoException extends Exception {
    public MismatchYesNoException(String message) {
        super(message);
    }
}

class MismatchNormalException extends Exception {
    public MismatchNormalException(String message) {
        super(message);
    }
}

class MismatchSelectNumberException extends Exception {
    public MismatchSelectNumberException(String message) {
        super(message);
    }
}

class NoResultException extends Exception {
    public NoResultException(String message) {
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

    abstract void showVehicleValue(int blankLength);

    public String getType() { return this.type; }
    public int getDistance() { return this.distance; }
    public int getMinute() { return this.minute; }
}

class Bike extends Vehicle {
    Bike(String type, int distance, int minute) {
        super(type, distance, minute);
    }

    @Override
    public void showVehicleValue(int blankLength) {
        ShowScreen.SeparateScreen(blankLength, " ");
        System.out.println(" |: Bike    [" + getType() +"], ["+ getDistance() + "] ," + getMinute() + "分 |");
    }
}

class Bicycle extends Vehicle {
    Bicycle(String type, int distance, int minute) {
        super(type, distance, minute);
    }

    @Override
    public void showVehicleValue(int blankLength) {
        ShowScreen.SeparateScreen(blankLength, " ");
        System.out.println(" |: Bicycle [" + getType() +"], ["+ getDistance() + "] ," + getMinute() + "分 |");
    }
}

class Car extends Vehicle {
    Car(String type, int distance, int minute) {
        super(type, distance, minute);
    }

    @Override
    public void showVehicleValue(int blankLength) {
        ShowScreen.SeparateScreen(blankLength, " ");
        System.out.println(" |: Bicycle [" + getType() +"], ["+ getDistance() + "] ," + getMinute() + "分 |");
    }
}

class Train extends Vehicle {
    Train(String type, int distance, int minute) {
        super(type, distance, minute);
    }

    @Override
    public void showVehicleValue(int blankLength) {
        ShowScreen.SeparateScreen(blankLength, " ");
        System.out.println(" |: Train   [" + getType() +"], ["+ getDistance() + "] ," + getMinute() + "分 |");
    }
}

class Walk extends Vehicle {
    Walk(String type, int distance, int minute) {
        super(type, distance, minute);
    }

    @Override
    public void showVehicleValue(int blankLength) {
        ShowScreen.SeparateScreen(blankLength, " ");
        System.out.println(" |: Walk    [" + getType() +"], ["+ getDistance() + "] ," + getMinute() + "分 |");
    }
}

class About extends Vehicle {
    About(String type, int distance, int minute) {
        super(type, distance, minute);
    }

    @Override
    public void showVehicleValue(int blankLength) {
        ShowScreen.SeparateScreen(blankLength, " ");
        System.out.println(" |: About   [" + getType() +"], ["+ getDistance() + "] ," + getMinute() + "分 |");
    }
}


//---------- ---------- 学校全体の統括 ---------- ----------
abstract class SchoolMember implements OperateVehicleList {//学校全体のメンバー
    private String name;
    private String position;
    private int number;
    
    SchoolMember(String name, String position) {//事務員、教員用
        this.name = name;
        this.position = position;
    }
    SchoolMember(String name, int number) {//生徒用
        this.name = name;
        this.number = number;
    }

    private static List<SchoolMember> All_member = new ArrayList<>();//学校全体のメンバーのリスト

    public static SchoolMember getMember(int index) {
        return All_member.get(index);
    }
    public static void ShowMemberList() {
        /*
        for (SchoolMember member : All_member) {
            member.showValue();
        }
        */
        for (int i = 0; i < All_member.size(); i++) {
            All_member.get(i).showValue(i);
        }
    }
    public static void ShowVehicleList(List<Vehicle> MembersVehicle) {
        if (MembersVehicle.isEmpty()) {
            System.out.println(" | No Data |");
        } else {
            System.out.println();
            for (int i = 0; i < MembersVehicle.size(); i++) {
                MembersVehicle.get(i).showVehicleValue(15);
            }
        }
    }

    public static void AddMembr(SchoolMember member) {//メンバーの追加
        All_member.add(member);
    }

    public static void RemoveMembr(SchoolMember member) {//メンバーの削除
        All_member.remove(member);
    }

    public String getName() { return this.name; }
    public String getPosition() { return this.position; }
    public int getNumber() { return this.number; }

    abstract void showValue(int index);

}

//---------- ---------- 学校関係者 ---------- ----------
interface OperateVehicleList {
    public void AddVehicle(Vehicle vehicle);

    public void RemoveVehicle(Vehicle vehicle);
}

class OfficeStaff extends SchoolMember {
    private List<Vehicle> OfficeStaffVehicles = new ArrayList<>();

    public OfficeStaff(String name, String position) {
        super(name, position);
    }

    @Override
    public void showValue(int index) {
        System.out.println(index+ " - 事務員: " + getName() + " | 役職: " + getPosition());
        ShowVehicleList(OfficeStaffVehicles);
    }

    @Override
    public void AddVehicle(Vehicle vehicle) {
        OfficeStaffVehicles.add(vehicle);
    }

    @Override
    public void RemoveVehicle(Vehicle vehicle) {
        OfficeStaffVehicles.remove(vehicle);
    }
}

class Teacher extends SchoolMember {
    private List<Vehicle> TeacherVehicles = new ArrayList<>();

    public Teacher(String name, String position) {
        super(name, position);
    }

    @Override
    public void showValue(int index) {
        System.out.print(index + " - 教師: " + getName() + " | 役職: " + getPosition());
        ShowVehicleList(TeacherVehicles);
    }

    @Override
    public void AddVehicle(Vehicle vehicle) {
       TeacherVehicles.add(vehicle);
    }

    @Override
    public void RemoveVehicle(Vehicle vehicle) {
        TeacherVehicles.remove(vehicle);
    }
}

class Student extends SchoolMember {
    private List<Vehicle> StudentVehicles = new ArrayList<>();

    public Student(String name, int number) {
        super(name, number);
    }

    @Override
    public void showValue(int index) {
        System.out.println(index + " - 生徒: " + getName() + " | 生徒番号: " + getNumber());
        ShowVehicleList(StudentVehicles);
    }

    @Override
    public void AddVehicle(Vehicle vehicle) {
        StudentVehicles.add(vehicle);
    }

    @Override
    public void RemoveVehicle(Vehicle vehicle) {
        StudentVehicles.remove(vehicle);
    }
}

//---------- ---------- メニュー画面 ---------- ----------
class ShowScreen {
    static void SeparateScreen() {
        for (int i = 0; i < 50; i++) System.out.print("-");
        System.out.println();
    }
    static void SeparateScreen(int count) {
        for (int i = 0; i < count; i++) System.out.print("-");
        System.out.println();
    }
    static void SeparateScreen(int count, String separator) {
        for (int i = 0; i < count; i++) System.out.print(separator);
        //System.out.println();
    }
    
    static void StartScreen() {//最初のスタート画面
        SeparateScreen();
        System.out.print("""
            通学、通勤手段の簡易管理システム
            System of School's Commuting.
            """);
    }

    static void LoginScreen() {
        SeparateScreen();
        System.out.print("""
            ようこそ. これは通学、通勤手段の簡易管理システムです.
            Welcome to System of School's Commuting.
            Please Password.
            """);
        System.out.print(": ");
    }

    static void MenuScreen() {//軸となるメニュー画面
        SeparateScreen();
        System.out.println("""
                メインメニュー (Main menu)

                1: 名簿表示 (Show member)
                2: 名簿管理（追加 / 削除）(add / remove)
                3: パスワードの変更 (Change the password)
                4: ログアウト (Logout)
                番号を選んでください.
                """);
    }

    static void ShowMemberScreen() {
        System.out.println("\n\n");
        SeparateScreen(17);
        System.out.println("| メンバー 一覧 |");
        SeparateScreen();
        System.out.println("名前");
        SeparateScreen();
    }

    static void OperateMemberScreen() {
        SeparateScreen();
        System.out.println("""
                名簿 (Main menu)

                1: メンバーおよび情報の追加
                2: メンバーの削除
                3: メインメニューに戻る
                """);
        /*
        System.out.println("""
                名簿 (Main menu)

                1: メンバーおよび情報の追加
                2: メンバーの編集
                3: メインメニューに戻る
                """);
        */
    }

    static void ChangePasswordScreen() {
        SeparateScreen();
        System.out.print("""
            現在のパスワードを入力してください. Please current password.
            戻る場合は[cancel]の入力をお願いします.
            """);
        System.out.print(": ");
    }

    static void ChangesPasswordPossibleScreen() {
        System.out.print("""
            新しいパスワードを入力してください. Please new password.
            戻る場合は[cancel]の入力をお願いします.
            ただし、[cancel]の文字列は使えません.
            """);
    }

    static void LogoutScreen() {
        SeparateScreen();
        System.out.println("""
            ログアウトしますか？ You will logout this system.
            Reary?[y/n]
            :
            """);
    }

    static void LoggedoutScreen() {
        SeparateScreen();
        System.out.println("""
                ログアウトしました.
                You logged out.

                """);
    }
}

//---------- ---------- メニュー画面 class群 ---------- ----------
interface Screen {
    public Screen run(SchoolScanner input);
}

class Start implements Screen {
    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.StartScreen();
        return new Login();
    }
}

class Login implements Screen {
    @Override
    public  Screen run(SchoolScanner input) {
        ShowScreen.LoginScreen();
        try {
            SchoolScanner.JudgeSelect inputJudge = input.InputPassword();
            switch (inputJudge) {
                case  SchoolScanner.JudgeSelect.True:
                    return new Menu();
                case SchoolScanner.JudgeSelect.False:
                    throw new MismatchNormalException("""
                    \nパスワードの入力に失敗しました. 入力が無効です. もう一度おねがいします.
                    ホームに戻る場合は[cancel]の入力をお願いします.
                        """);
                case SchoolScanner.JudgeSelect.Cancel:
                    return new Start();
                default:
                    throw new MismatchNormalException("""
                    \nパスワードの入力に失敗しました. 入力が無効です. もう一度おねがいします.
                    ホームに戻る場合は[cancel]の入力をお願いします.
                        """);
            }
        } catch (MismatchNormalException e) {
            System.out.println(e);
            return this;
        }
    }
}

class Menu implements Screen {
    @Override
    public  Screen run(SchoolScanner input) {
        ShowScreen.MenuScreen();
        SchoolScanner.SelectNumber inputResult = input.InputSelectNumber(1, 4);
        switch (inputResult) {
            case SchoolScanner.SelectNumber.Zero:
                return this;
            case SchoolScanner.SelectNumber.One:
                return new ShowMember();
            case SchoolScanner.SelectNumber.Two:
                return new OperateMember();
            case SchoolScanner.SelectNumber.Three:
                return new ChangePassword();
            case SchoolScanner.SelectNumber.Four:
                return new Logout();
            default:
                return this;
        }
    }
}


class ShowMember implements Screen {
    @Override
    public  Screen run(SchoolScanner input) {
        ShowScreen.ShowMemberScreen();
        return null;
    }
}

class OperateMember implements Screen {
    @Override
    public  Screen run(SchoolScanner input) {
        ShowScreen.OperateMemberScreen();
        SchoolScanner.SelectNumber inputResult = input.InputSelectNumber(1, 3);
        switch (inputResult) {
            case SchoolScanner.SelectNumber.Zero:
                return new Menu();
            case SchoolScanner.SelectNumber.One:
                return new OperateMemberAdd();
            case SchoolScanner.SelectNumber.Two:
                return new OperateMemberRemove();
            case SchoolScanner.SelectNumber.Three:
                return new Menu();
            default:
                return new Menu();
        }
    }
}

class OperateMemberAdd implements Screen {
    @Override
    public Screen run(SchoolScanner input) {
        return null;
    }
}

class OperateMemberRemove implements Screen {
    @Override
    public Screen run(SchoolScanner input) {
        return null;
    }
}

class ChangePassword implements Screen {
    @Override
    public  Screen run(SchoolScanner input) {
        ShowScreen.ChangePasswordScreen();
        try {
            SchoolScanner.JudgeSelect inputJudge = input.InputPassword();
            switch (inputJudge) {
                case  SchoolScanner.JudgeSelect.True:
                    return new ChangesPasswordPossible();
                case SchoolScanner.JudgeSelect.False:
                    throw new MismatchNormalException("""
                    \nパスワードの入力に失敗しました. 入力が無効です. もう一度おねがいします.
                    戻る場合は[cancel]の入力をお願いします.
                        """);
                case SchoolScanner.JudgeSelect.Cancel:
                    return new Menu();//メニューに戻る
                default:
                    throw new MismatchNormalException("""
                    \nパスワードの入力に失敗しました. 入力が無効です. もう一度おねがいします.
                    戻る場合は[cancel]の入力をお願いします.
                        """);
            }
        } catch (MismatchNormalException e) {
            System.out.println(e);
            return this;
        }
    }
}

class ChangesPasswordPossible implements Screen {
    @Override
    public  Screen run(SchoolScanner input) {
        ShowScreen.ChangesPasswordPossibleScreen();
        String newPassword = input.InputChangesPasswordPossible();

        if (newPassword == null) {
            return this;
        } else {
            OriginalPassword.changePassword(newPassword);
            return new Menu();
        }
    }
}

class Logout implements Screen {
    @Override
    public  Screen run(SchoolScanner input) {
        ShowScreen.LogoutScreen();
        boolean LogoutConfirm = input.InputYesNo();
        if (LogoutConfirm) {
            return new Start();
        } else {
            return new Menu();
        }
        
    }
}

//---------- ---------- メイン ---------- ----------
public class Way_of_commuting {
    private static void MainRun() {
        SchoolScanner input = new SchoolScanner(new Scanner(System.in));
        Screen ScreenStatus = new Menu();
        do { 
            ScreenStatus = ScreenStatus.run(input);
        } while (!(ScreenStatus == null));
        input.tryClose();
    }
    public static void main(String[] args) throws MismatchNormalException {
        //MainRun();
        SchoolMember Member = new Teacher("坂本浩二", "一般教員");
        SchoolMember.AddMembr(Member);
        Member.AddVehicle(new Bike("日産", 40, 50));
        Member.AddVehicle(new Bike("トヨタ", 15, 30));
        SchoolMember Member1 = new Teacher("吉田輝元", "一般教員");
        SchoolMember.AddMembr(Member1);
        Member1.AddVehicle(new Bicycle("ブリヂストン", 40, 50));
        Member1.AddVehicle(new Walk("徒歩", 3, 45));
        SchoolMember.AddMembr(new Teacher("児玉健斗", "一般教員"));
        SchoolMember.RemoveMembr(SchoolMember.getMember(2));
        SchoolMember.ShowMemberList();
    }
}