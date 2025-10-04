package Commuting_to_School;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class OriginalPassword {
    private static String Password = "firstPassword";

    public static String getPassword(String password) {
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
        True, False, Cancel;
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
    
    SchoolScanner() {
        this.scanner = new Scanner(System.in, Charset.forName("Shift_JIS"));
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

    enum Mode {
        EditMember, RemoveMember, AdditionVehicle, Logout;
    }
    public boolean InputYesNo(Mode mode) {//入力の際に[y/n]を求める際の処理
        try {
            String input_text = scanner.nextLine().toLowerCase();
            if (input_text.equalsIgnoreCase("y") || input_text.equalsIgnoreCase("yes")) {
                switch (mode) {
                    case EditMember :
                        System.out.println("編集を決定しました.");
                        break;
                    case RemoveMember :
                        System.out.println("削除しました.");
                        break;
                    case AdditionVehicle :
                        System.out.println("追加を継続します.");
                        break;
                    case Logout :
                        System.out.println("ログアウトしました.");
                        break;
                }
                return true;
            } else if (input_text.equalsIgnoreCase("n") || input_text.equalsIgnoreCase("no")) {
                switch (mode) {
                    case EditMember :
                        System.out.println("編集を決定しました.");
                        break;
                    case RemoveMember :
                        System.out.println("削除を中止しました.");
                        break;
                    case AdditionVehicle :
                        System.out.println("通学手段を確定しました.");
                        break;
                    case Logout :
                        System.out.println("ログアウトを中止しました.");
                        break;
                }
                return false;
            } else {
                throw new MismatchYesNoException("""
                        \n[y/n]に対し、["y", "n", "yes", "no", その他これらの大文字または大文字小文字の混合]以外で入力をしています.その入力は無効です.
                        操作を中止します.
                        """);
            }
        } catch (InputMismatchException | MismatchYesNoException e) {
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

    public int InputSelectIndex(int start, int end) {//次直す。0を認識してくれないかも...
        try {
            if (SchoolMember.getMemberSize() == 0) {
                System.out.println("メンバーがいません.中止します.");
                return -2;
            }
            String input_line = scanner.nextLine();
            int input_index = Integer.parseInt(input_line);
            if (input_line.equals("cancel")) return -2;
            boolean judge_scope = false;
            for (int i = start; i <= end; i++) {//選択肢の範囲内なら true にする
                if (input_index == i) judge_scope = true;
            }
            if (judge_scope && (input_index < SchoolMember.getMemberSize() +1)) {//選択肢の範囲内で、 true なら正しい
                //SchoolMember.getMemberSize() +1 としているのは、見掛け上のindexを1始まりにするため
                return input_index;//正しい入力なら値を返す
            } else {//範囲外なら例外
                System.out.println("\n" + start +" ~ " + end + "までの数字を入れてください. その値は無効です.");
                return -1;
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("\nそれは数字ではないです. その値は無効です.");
            return -1;
        }
    }

    public String InputLine() {
        try {
            String input_line = scanner.nextLine();
            return input_line;
        } catch (InputMismatchException e) {
            System.out.println("入力ミスが発生しました.再度入力をお願いします.");
            return null;
        }
    }

    public int InputFigure() {
        try {
            int input_figure = Integer.parseInt(scanner.nextLine());
            if (input_figure < 0) {
                throw new MismatchNormalException("マイナスは使用不可です.");
            }
            return input_figure;
        } catch (InputMismatchException | NumberFormatException | MismatchNormalException e) {
            System.out.println("入力ミスが発生しました.再度入力をお願いします.");
            return -1;
        }
    }

    @Override
    public void close() {
        /*
         * try-with-resorceを使うため、
         * Overrideにより意図的に関数を無効化
         */
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

    enum VehicleKinds {
        Bike, Bicycle, Car, Train, Walk, About;
    }

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
        System.out.println(" |: Car     [" + getType() +"], ["+ getDistance() + "] ," + getMinute() + "分 |");
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

    enum Member {
        OfficeStaff, Teacher, Student;
    }

    enum AddMember {
        New, Existing;
    }
    
    SchoolMember(String name, String position) {//事務員、教員用
        this.name = name;
        this.position = position;
    }
    SchoolMember(String name, int number) {//生徒用
        this.name = name;
        this.number = number;
    }

    private static List<SchoolMember> All_member = new ArrayList<>();//学校全体のメンバーのリスト

    public static List<SchoolMember> getAllMember() {
        return All_member;
    }

    public static SchoolMember getMember(int index) {
        return All_member.get(index);
    }
    public static void setMember(int index, SchoolMember member) {//値の置き換え
        All_member.set(index, member);
    }
    public static int getMemberSize() {
        return All_member.size();
    }
    public static void ShowMemberList() {
        if (All_member.isEmpty()) {
            System.out.println(" | Member has not yet added. |");
        } else {
            for (int i = 0; i < All_member.size(); i++) {
                All_member.get(i).showValue(i +1);//showVaueの見掛けのindexを1からにしたいので、1を足す
            }
        }
    }

    public static void ShowVehicleList(List<Vehicle> MembersVehicle) {
        if (MembersVehicle.isEmpty()) {
            ShowScreen.SeparateScreen(15, " ");
            System.out.println(" | No Data |");
        } else {
            //System.out.println();
            for (int i = 0; i < MembersVehicle.size(); i++) {
                MembersVehicle.get(i).showVehicleValue(15);
            }
        }
        ShowScreen.SeparateScreen(2, " ");
        ShowScreen.SeparateScreen(48, "_", "\n");
    }

    public static void AddMember(SchoolMember member) {//メンバーの追加
        All_member.add(member);
    }

    public static void RemoveMember(SchoolMember member) {//メンバーの削除
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
        System.out.println(index + " - 教師: " + getName() + " | 役職: " + getPosition());
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
    }
    static void SeparateScreen(int count, String separator, String option) {
        for (int i = 0; i < count; i++) System.out.print(separator);
        if (option.equals("\n")) System.out.println();
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
        System.out.println("番号, 名前, 情報~~~~");
        SeparateScreen();
    }

    static void OperateMemberScreen() {
        SeparateScreen();
        System.out.println("""
                名簿 (Member)

                1: メンバーおよび情報の追加
                2: メンバーの削除
                3: メインメニューに戻る
                """);
    }

    static void OperateMemberAddScreen() {
        SeparateScreen();
        System.out.println("""
                情報を入力してください.

                1: 新規で追加
                2: 編集する
                3: 戻る
                """);
    }

    static void OperateMemberAddWhoScreen() {
        SeparateScreen();
        System.out.println("""
                誰を追加しますか？

                1: 事務員
                2: 教員
                3: 生徒
                """);
    }

    static void OperateMemberAddNameScreen() {
        SeparateScreen();
        System.out.println("名前を入力してください.");
    }

    static void OperateMemberAddPositionScreen() {
        SeparateScreen();
        System.out.println("役職を入力してください.");
    }

    static void OperateMemberAddNumberScreen() {
        SeparateScreen();
        System.out.println("生徒番号を入力してください.");
    }

    static void OperateMemberEditSelectScreen() {
        SeparateScreen();
        System.out.println("""
                メンバーを編集します.
                編集したいメンバーのリスト番号(Index)を入力してください.
                戻る場合は[cancel]の入力をお願いします.
                """);
    }

    static void OperateMemberEditConfirmationScreen() {
        System.out.println();
        System.out.println("""
                上記のメンバーを編集します.
                よろしいですか？ [y/n]
                """);
    }

    static void OperateVehicleAddWhitchScreen() {
        SeparateScreen();
        System.out.println("""
                通学手段はどうしますか？

                1: バイク
                2: 自転車
                3: 車
                4: 電車
                5: 徒歩
                6: その他
                7: 通学手段を設定しない
                """);
    }

    static void OperateVehicleAddTypeShow() {
        SeparateScreen();
        System.out.println("タイプを入力してください.");
    }

    static void OperateVehicleAddDistanceShow() {
        SeparateScreen();
        System.out.println("通学距離を入力してください.");
    }

    static void OperateVehicleAddMinuteShow() {
        SeparateScreen();
        System.out.println("通学時間を入力してください.");
    }

    static void OperateVehicleAddConfirmation() {
        SeparateScreen();
        System.out.println("""
                通学手段を追加しますか？ [y/n]
                """);
    }

    static void OperateMemberRemoveScreen() {
        SeparateScreen();
        System.out.println("""
                メンバーを削除します.
                消したいメンバーのリスト番号(Index)を入力してください.
                戻る場合は[cancel]の入力をお願いします.
                """);
    }

    static void OperateMemberRemoveConfirmationScreen() {
        System.out.println();
        System.out.println("""
                上記のメンバーを削除します.
                よろしいですか？ [y/n]
                """);
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
        System.out.print("""
            ログアウトしますか？ You will logout this system.
            Reary?[y/n]
            """);
        System.out.print(": ");
    }

    static void LoggedoutScreen() {
        SeparateScreen();
        System.out.println("""
                ログアウトしました.
                You logged out.

                """);
    }

    static void Error() {
        System.out.println("エラーです.");
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
                case  True:
                    return new Menu();
                case False:
                    throw new MismatchNormalException("""
                    \nパスワードの入力に失敗しました. 入力が無効です. もう一度おねがいします.
                    ホームに戻る場合は[cancel]の入力をお願いします.
                        """);
                case Cancel:
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
            case Zero:
                return this;
            case One:
                return new ShowMember();
            case Two:
                return new OperateMember();
            case Three:
                return new ChangePassword();
            case Four:
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
        SchoolMember.ShowMemberList();
        return new Menu();
    }
}

class OperateMember implements Screen {
    @Override
    public  Screen run(SchoolScanner input) {
        ShowScreen.OperateMemberScreen();
        SchoolScanner.SelectNumber inputResult = input.InputSelectNumber(1, 3);
        switch (inputResult) {
            case Zero:
                return new Menu();
            case One:
                return new OperateMemberAdd();
            case Two:
                return new OperateMemberRemove();
            case Three:
                return new Menu();
            default:
                return new Menu();
        }
    }
}
//---------- ---------- メンバー追加処理 ---------- ----------
class OperateMemberAdd implements Screen {
    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.OperateMemberAddScreen();
        SchoolScanner.SelectNumber inputResult = input.InputSelectNumber(1, 3);
        switch (inputResult) {
            case Zero:
                return this;
            case One:
                return new OperateMemberAddWho();//新規用
            case Two:
                return new OperateMemberEditSelect();//編集用
            case Three:
                return new OperateMember();
            default:
                return new OperateMember();
        }
    }
}

class OperateMemberAddWho implements Screen {//事務員・教員・生徒から選択
    int index = -1;
    OperateMemberAddWho() {}//新規用
    OperateMemberAddWho(int index) {//編集用
        this.index = index;
    }
    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.OperateMemberAddWhoScreen();
        SchoolScanner.SelectNumber inputResult = input.InputSelectNumber(1, 3);
        if (index == -1) {//このとき、新規追加の場合
            switch (inputResult) {
                case Zero:
                    return this;
                case One:
                    return new OperateMemberAddName(SchoolMember.Member.OfficeStaff);
                case Two:
                    return new OperateMemberAddName(SchoolMember.Member.Teacher);
                case Three:
                    return new OperateMemberAddName(SchoolMember.Member.Student);
                default:
                    return this;
            }
        } else {//このとき、編集の場合
            switch (inputResult) {
                case Zero:
                    return this;
                case One:
                    return new OperateMemberAddName(index, SchoolMember.Member.OfficeStaff);
                case Two:
                    return new OperateMemberAddName(index, SchoolMember.Member.Teacher);
                case Three:
                    return new OperateMemberAddName(index, SchoolMember.Member.Student);
                default:
                    return this;
            }
        }
    }
}

class OperateMemberAddName implements Screen {
    int index = -1;
    SchoolMember.Member kinds;
    OperateMemberAddName(SchoolMember.Member kinds) {//新規用
        this.kinds = kinds;
    }
    OperateMemberAddName(int index, SchoolMember.Member kinds) {//編集用
        this.index = index;
        this.kinds = kinds;
    }
    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.OperateMemberAddNameScreen();
        String name = input.InputLine();
        if (index == -1) {//新規の場合
            if (name == null) {
                return this;
            } else {
                return new OperateMemberAddPositionOrNumber(kinds, name);
            }
        } else {//編集の場合
            if (name == null) {
                return this;
            } else {
                return new OperateMemberAddPositionOrNumber(index, kinds, name);
            }
        }
    }
}

class OperateMemberAddPositionOrNumber implements Screen {
    int index = -1;
    SchoolMember.Member kinds;
    String name;
    OperateMemberAddPositionOrNumber(SchoolMember.Member kinds, String name) {
        this.kinds = kinds;
        this.name = name;
    }
    OperateMemberAddPositionOrNumber(int index, SchoolMember.Member kinds, String name) {
        this.index = index;
        this.kinds = kinds;
        this.name = name;
    }
    
    @Override
    public Screen run(SchoolScanner input) {
        SchoolMember member;
        if (index == -1) {//新規用
            switch (kinds) {
                case OfficeStaff:
                case Teacher:
                    ShowScreen.OperateMemberAddPositionScreen();
                    String position = input.InputLine();
                    member = MemberFactory(kinds, name, position);
                    //SchoolMember.AddMember(member);//直す
                    //return new OperateMemberAdd();//直す
                    System.out.println("Member value: "+member);
                    return new OperateVehicleAddWhitch(member);
                case Student:
                    ShowScreen.OperateMemberAddNumberScreen();
                    int number = input.InputFigure();
                    if (number >= 0) {
                        member = MemberFactory(kinds, name, number);
                        //SchoolMember.AddMember(member);//直す
                        //return new OperateMemberAdd();//次直す
                        System.out.println("Member value: "+member);
                        return new OperateVehicleAddWhitch(member);
                    } else {
                        return this;//new OperateMemberAddPositionOrNumber(kinds, name);//再度繰り返す
                    }
                default:
                    ShowScreen.Error();
                    return new OperateMemberAddWho();
            }
        } else {//編集用
            switch (kinds) {
                case OfficeStaff:
                case Teacher:
                    ShowScreen.OperateMemberAddPositionScreen();
                    String position = input.InputLine();
                    member = MemberFactory(kinds, name, position);
                    //SchoolMember.setMember(index, member);//直す
                    //return new OperateMemberAdd();//直す
                    return new OperateVehicleAddWhitch(index);//memberにするかも
                case Student:
                    ShowScreen.OperateMemberAddNumberScreen();
                    int number = input.InputFigure();
                    if (number >= 0) {
                        member = MemberFactory(kinds, name, number);
                        //SchoolMember.setMember(index, member);//直す
                        //return new OperateMemberAdd();//次直す
                        return new OperateVehicleAddWhitch(index);//memberにするかも
                    } else {
                        return this;//new OperateMemberAddPositionOrNumber(kinds, name);//再度繰り返す
                    }
                default:
                    ShowScreen.Error();
                    return new OperateMemberAddWho();
            }
        }
    }

    public static SchoolMember MemberFactory(SchoolMember.Member kinds, String name, String position) {//新規用
        switch (kinds) {
            case OfficeStaff:
                return new OfficeStaff(name, position);
            case Teacher:
                return new Teacher(name, position);
            default:
                return null;
        }
    }
    public static SchoolMember MemberFactory(SchoolMember.Member kinds, String name, int number) {//新規用
        return new Student(name, number);//kinds = SchoolMember.Member.Student;
    }
    /*
    public static SchoolMember MemberFactory(int index, SchoolMember.Member kinds, String name, String position) {//編集用
        switch (kinds) {
            case OfficeStaff:
                return new OfficeStaff(name, position);
            case Teacher:
                return new Teacher(name, position);
            default:
                return null;
        }
    }
    public static SchoolMember MemberFactory(int index, SchoolMember.Member kinds, String name, int number) {//編集用
        return new Student(name, number);
    }
    */
}

//---------- ---------- メンバー編集処理 ---------- ----------
class OperateMemberEditSelect implements Screen {//Removeの際と同じように、編集したいindexを取得.
    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.ShowMemberScreen();
        SchoolMember.ShowMemberList();
        ShowScreen.OperateMemberEditSelectScreen();
        int inputIndex = input.InputSelectIndex(1, SchoolMember.getMemberSize() +1);//SchoolMember.getMemberSize() +1 としているのは、見掛け上のindexを1始まりにするため
        if (inputIndex -1 >= 0) {//入力が正しい場合
            inputIndex--;//All_memberのindex参照を0からではなく1からにしたいため、1を引く;
            ShowScreen.SeparateScreen();
            SchoolMember.getMember(inputIndex).showValue(inputIndex +1);
            return new OperateMemberEditConfirmation(inputIndex);
        } else if (inputIndex == -1) {//入力が間違っている場合
            return this;
        } else if (inputIndex == -2) {//cancelの場合
            return new OperateMemberAdd();
        } else {
            return new OperateMemberAdd();
        }
    }
}

class OperateMemberEditConfirmation implements Screen {//メンバーの編集許可処理
    int index;
    OperateMemberEditConfirmation(int index) {
        this.index = index;
    }
    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.OperateMemberEditConfirmationScreen();
        boolean editConfirmation = input.InputYesNo(SchoolScanner.Mode.EditMember);
        if (editConfirmation) {
            return new OperateMemberAddWho(index);
        } else {
            return new OperateMemberEditSelect();
        }
    }
}

//---------- ---------- 通学手段追加処理 ---------- ----------
class OperateVehicle implements Screen {
    @Override
    public Screen run(SchoolScanner input) {
        return null;
    }
}

class OperateVehicleAddWhitch implements Screen {//通学手段を選択
    int index = -1;
    SchoolMember member;
    OperateVehicleAddWhitch(SchoolMember member) {
        this.member = member;
    }
    OperateVehicleAddWhitch(int index) {
        this.index = index;
    }

    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.OperateVehicleAddWhitchScreen();
        SchoolScanner.SelectNumber inputResult = input.InputSelectNumber(1, 7);
        if (index == -1) {//新規用
            switch (inputResult) {
                case Zero:
                    return this;
                case One:
                    return new OperateVehicleAddType(member, Vehicle.VehicleKinds.Bike);
                case Two:
                    return new OperateVehicleAddType(member, Vehicle.VehicleKinds.Bicycle);
                case Three:
                    return new OperateVehicleAddType(member, Vehicle.VehicleKinds.Car);
                case Four:
                    return new OperateVehicleAddType(member, Vehicle.VehicleKinds.Train);
                case Five:
                    return new OperateVehicleAddType(member, Vehicle.VehicleKinds.Walk);
                case Six:
                    return new OperateVehicleAddType(member, Vehicle.VehicleKinds.About);
                case Seven:
                    SchoolMember.AddMember(member);
                    return new OperateMember();
                default:
                    return new OperateMember();
            }
        } else {//編集用
            switch (inputResult) {
                case Zero:
                    return this;
                case One:
                    return new OperateVehicleAddType(index, Vehicle.VehicleKinds.Bike);
                case Two:
                    return new OperateVehicleAddType(index, Vehicle.VehicleKinds.Bicycle);
                case Three:
                    return new OperateVehicleAddType(index, Vehicle.VehicleKinds.Car);
                case Four:
                    return new OperateVehicleAddType(index, Vehicle.VehicleKinds.Train);
                case Five:
                    return new OperateVehicleAddType(index, Vehicle.VehicleKinds.Walk);
                case Six:
                    return new OperateVehicleAddType(index, Vehicle.VehicleKinds.About);
                case Seven:
                    return new OperateMember();
                default:
                    return new OperateMember();
            }
        }
        
    }
}

class OperateVehicleAddType implements Screen {
    int index = -1;
    SchoolMember member;
    Vehicle.VehicleKinds kinds;
    OperateVehicleAddType(SchoolMember member, Vehicle.VehicleKinds kinds) {
        this.member = member;
        this.kinds = kinds;
    }
    OperateVehicleAddType(int index, Vehicle.VehicleKinds kinds) {
        this.index = index;
        this.kinds = kinds;
    }

    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.OperateVehicleAddTypeShow();
        String type = input.InputLine();
        if (index == -1) {//新規の場合
            if (type == null) {
                return this;
            } else {
                return new OperateVehicleAddDistance(member, kinds, type);
            }
        } else {//編集の場合
            if (type == null) {
                return this;
            } else {
                return new OperateVehicleAddDistance(index, kinds, type);
            }
        }
    }
}

class OperateVehicleAddDistance implements Screen {
    int index = -1;
    SchoolMember member;
    Vehicle.VehicleKinds kinds;
    String type;
    OperateVehicleAddDistance(SchoolMember member, Vehicle.VehicleKinds kinds, String type) {
        this.member = member;
        this.kinds = kinds;
        this.type = type;
    }
    OperateVehicleAddDistance(int index, Vehicle.VehicleKinds kinds, String type) {
        this.index = index;
        this.kinds = kinds;
        this.type = type;
    }

    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.OperateVehicleAddDistanceShow();
        int distance = input.InputFigure();
        if (index == -1) {//新規の場合
            if (distance >= 0) {
                return new OperateVehicleAddMinute(member, kinds, type, distance);
            } else {
                return this;
            }
        } else {//編集の場合
            if (distance >= 0) {
                return new OperateVehicleAddMinute(index, kinds, type, distance);
            } else {
                return this;
            }
        }
    }
}

class OperateVehicleAddMinute implements Screen {
    int index = -1;
    SchoolMember member;
    Vehicle.VehicleKinds kinds;
    String type;
    int distance;
    OperateVehicleAddMinute(SchoolMember member, Vehicle.VehicleKinds kinds, String type, int distance) {
        this.member = member;
        this.kinds = kinds;
        this.type = type;
        this.distance = distance;
    }
    OperateVehicleAddMinute(int index, Vehicle.VehicleKinds kinds, String type, int distance) {
        this.index = index;
        this.kinds = kinds;
        this.type = type;
        this.distance = distance;
    }
    
    @Override
    public Screen run(SchoolScanner input) {//個々の処理を次回実装する。
        Vehicle vehicle;
        ShowScreen.OperateVehicleAddMinuteShow();
        int minute = input.InputFigure();
        if (index == -1) {//新規の場合
            if (minute >= 0) {
                vehicle = VehicleFactory(kinds, type, distance, minute);
                member.AddVehicle(vehicle);
                System.out.println("エラー来るぞ！！！！");
                return new OperateVehicleAddConfirmation(member);
            } else {
                return this;
            }
        } else {//編集の場合
            if (minute >= 0) {
                vehicle = VehicleFactory(kinds, type, distance, minute);
                SchoolMember.getMember(index).AddVehicle(vehicle);
                return new OperateVehicleAddConfirmation(index);
            } else {
                return this;
            }
        }
    }

    public static Vehicle VehicleFactory(Vehicle.VehicleKinds kinds, String type, int distance, int minute) {
        switch (kinds) {
            case Bike:
                return new Bike(type, distance, minute);
            case Bicycle:
                return new Bicycle(type, distance, minute);
            case Car:
                return new Car(type, distance, minute);
            case Train:
                return new Train(type, distance, minute);
            case Walk:
                return new Walk(type, distance, minute);
            case About:
                return new About(type, distance, minute);
            default:
                return null;
        }
    }
}

class OperateVehicleAddConfirmation implements Screen {//通学手段をさらに追加するか聞く
    SchoolMember member;
    int index = -1;
    OperateVehicleAddConfirmation(SchoolMember member) {
        this.member = member;
    }
    OperateVehicleAddConfirmation(int index) {
        this.index = index;
    }
    
    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.OperateVehicleAddConfirmation();
        boolean VehicleAdditionConfirmation = input.InputYesNo(SchoolScanner.Mode.AdditionVehicle);
        if (VehicleAdditionConfirmation) {//通学手段を再度追加
            if (index == -1) {//新規用
                return new OperateVehicleAddWhitch(member);
            } else {//編集用
                return new OperateVehicleAddWhitch(index);
            }
        } else {//通学手段を確定
            if (index == -1) {//新規用
                SchoolMember.AddMember(member);
                return new OperateMemberAdd();
            } else {//編集用
                //SchoolMember.setMember(index, member);
                return new OperateMemberAdd();
            }
        }
    }
}

//---------- ---------- メンバー削除処理 ---------- ----------
class OperateMemberRemove implements Screen {
    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.ShowMemberScreen();
        SchoolMember.ShowMemberList();
        ShowScreen.OperateMemberRemoveScreen();
        int inputIndex = input.InputSelectIndex(1, SchoolMember.getMemberSize() +1);//SchoolMember.getMemberSize() +1 としているのは、見掛け上のindexを1始まりにするため
        if (inputIndex -1 >= 0) {//入力が正しい場合
            inputIndex--;//All_memberのindex参照を0からではなく1からにしたいため、1を引く;
            ShowScreen.SeparateScreen();
            SchoolMember.getMember(inputIndex).showValue(inputIndex +1);
            return new OperateMemberRemoveConfirmation(inputIndex);
        } else if (inputIndex == -1) {//入力が間違っている場合
            return this;
        } else if (inputIndex == -2) {//cancelの場合
            return new OperateMemberAdd();
        } else {
            return new OperateMemberAdd();
        }
    }
}

class OperateMemberRemoveConfirmation implements Screen {
    int inputIndex;

    OperateMemberRemoveConfirmation(int inputIndex) {
        this.inputIndex = inputIndex;
    }

    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.OperateMemberRemoveConfirmationScreen();
        boolean removeConfirmation = input.InputYesNo(SchoolScanner.Mode.RemoveMember);
        if (removeConfirmation) {
            SchoolMember.RemoveMember(SchoolMember.getMember(inputIndex));
            return new OperateMember();
        } else {
            return new OperateMember();
        }
    }
}

//---------- ---------- パスワード変更処理 ---------- ----------
class ChangePassword implements Screen {
    @Override
    public  Screen run(SchoolScanner input) {
        ShowScreen.ChangePasswordScreen();
        try {
            SchoolScanner.JudgeSelect inputJudge = input.InputPassword();
            switch (inputJudge) {
                case  True:
                    return new ChangesPasswordPossible();
                case False:
                    throw new MismatchNormalException("""
                    \nパスワードの入力に失敗しました. 入力が無効です. もう一度おねがいします.
                    戻る場合は[cancel]の入力をお願いします.
                        """);
                case Cancel:
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
        boolean LogoutConfirm = input.InputYesNo(SchoolScanner.Mode.Logout);
        if (LogoutConfirm) {
            return new Start();
        } else {
            return new Menu();
        }
        
    }
}

//---------- ---------- メイン ---------- ----------
public class Way_of_commuting {
    private static void TestCode() {
        // 事務員
        SchoolMember member1 = new OfficeStaff("無名", "平社員");
        member1.AddVehicle(OperateVehicleAddMinute.VehicleFactory(Vehicle.VehicleKinds.Walk, "Sports", 120, 321));
        SchoolMember.AddMember(member1); // 直接リストに追加

        // 教員
        SchoolMember member2 = new Teacher("無名", "会長");
        member2.AddVehicle(OperateVehicleAddMinute.VehicleFactory(Vehicle.VehicleKinds.Train, "chu-o-", 33, 100));
        member2.AddVehicle(OperateVehicleAddMinute.VehicleFactory(Vehicle.VehicleKinds.Car, "VMW", 100, 30));
        SchoolMember.AddMember(member2); // 直接リストに追加

        // 生徒
        SchoolMember member3 = new Student("名無し", 12345);
        member3.AddVehicle(OperateVehicleAddMinute.VehicleFactory(Vehicle.VehicleKinds.Bicycle, "Keio", 340, 144));
        member3.AddVehicle(OperateVehicleAddMinute.VehicleFactory(Vehicle.VehicleKinds.Bike, "TOYOTA", 5, 11));
        SchoolMember.AddMember(member3); // 直接リストに追加
    }

    private static void MainRun() {
        //SchoolScanner input = new SchoolScanner(new Scanner(System.in));
        SchoolScanner input = new SchoolScanner();
        Screen ScreenStatus = new Start();//最初の画面
        //TestCode();//テスト用メンバー表
        do { 
            ScreenStatus = ScreenStatus.run(input);
        } while (!(ScreenStatus == null));
        input.tryClose();
    }
    public static void main(String[] args) throws MismatchNormalException {
        MainRun();
    }
}