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

    enum Mode {
        RemoveMember, Logout;
    }
    public boolean InputYesNo(Mode mode) {//入力の際に[y/n]を求める際の処理
        try {
            String input_text = scanner.nextLine().toLowerCase();
            if (input_text.equalsIgnoreCase("y") || input_text.equalsIgnoreCase("yes")) {
                if (mode == Mode.RemoveMember) {
                    System.out.println("削除しました.");
                } else if (mode == Mode.Logout) {
                    System.out.println("ログアウトしました.");
                }
                return true;
            } else if (input_text.equalsIgnoreCase("n") || input_text.equalsIgnoreCase("no")) {
                if (mode == Mode.RemoveMember) {
                    System.out.println("削除を中止しました.");
                } else if (mode == Mode.Logout) {
                    System.out.println("ログアウトしました.");
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
            System.out.println(" | No Data |");
        } else {
            System.out.println();
            for (int i = 0; i < MembersVehicle.size(); i++) {
                MembersVehicle.get(i).showVehicleValue(15);
            }
        }
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
        /*
        System.out.println("""
                名簿 (Main menu)

                1: メンバーおよび情報の追加
                2: メンバーの編集
                3: メインメニューに戻る
                """);
        */
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

    static void OperateMemberAddNewScreen() {
        SeparateScreen();
        System.out.println("""
                誰を追加しますか？

                1: 事務員
                2: 教員
                3: 生徒
                """);
    }

    static void OperateMemberAddNewNameScreen() {
        SeparateScreen();
        System.out.println("名前を入力してください.");
    }

    static void OperateMemberAddNewPositionScreen() {
        SeparateScreen();
        System.out.println("役職を入力してください.");
    }

    static void OperateMemberAddNewNumberScreen() {
        SeparateScreen();
        System.out.println("生徒番号を入力してください.");
    }

    static void OperateMemberAddSelect() {

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

class OperateMemberAdd implements Screen {
    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.OperateMemberAddScreen();
        SchoolScanner.SelectNumber inputResult = input.InputSelectNumber(1, 3);
        switch (inputResult) {
            case Zero:
                return this;
            case One:
                return new OperateMemberAddNew();
            case Two:
                return null;//次直す
            case Three:
                return new OperateMember();
            default:
                return new OperateMember();
        }
    }
}

class OperateMemberAddNew implements Screen {
    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.OperateMemberAddNewScreen();
        SchoolScanner.SelectNumber inputResult = input.InputSelectNumber(1, 3);
        switch (inputResult) {
            case Zero:
                return this;
            case One:
                return new OperateMemberAddNewName(SchoolMember.Member.OfficeStaff);
            case Two:
                return new OperateMemberAddNewName(SchoolMember.Member.Teacher);
            case Three:
                return new OperateMemberAddNewName(SchoolMember.Member.Student);
                default:
                return this;
        }
    }
}

class OperateMemberAddNewName implements Screen {
    SchoolMember.Member kinds;
    OperateMemberAddNewName(SchoolMember.Member kinds) {
        this.kinds = kinds;
    }
    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.OperateMemberAddNewNameScreen();
        String name = input.InputLine();
        if (name == null) {
            return this;
        } else {
            return new OperateMemberAddNewPosition(kinds, name);
        }
    }
}

class OperateMemberAddNewPosition implements Screen {
    SchoolMember.Member kinds;
    String name;
    OperateMemberAddNewPosition(SchoolMember.Member kinds, String name) {
        this.kinds = kinds;
        this.name = name;
    }
    
    @Override
    public Screen run(SchoolScanner input) {
        SchoolMember member;
        switch (kinds) {
            case OfficeStaff:
            case Teacher:
                ShowScreen.OperateMemberAddNewPositionScreen();
                String position = input.InputLine();
                member = MemberFactory(kinds, name, position);
                SchoolMember.AddMember(member);
                return new OperateMemberAdd();
            case Student:
                ShowScreen.OperateMemberAddNewNumberScreen();
                int number = input.InputFigure();
                if (number >= 0) {
                    member = MemberFactory(kinds, name, number);
                    SchoolMember.AddMember(member);
                    return new OperateMemberAdd();//次直す
                } else {
                    return new OperateMemberAddNewPosition(kinds, name);//再度繰り返す
                }
            default:
                System.out.println("エラーです.");
                return new OperateMemberAddNew();
        }
    }

    public static SchoolMember MemberFactory(SchoolMember.Member kinds, String name, String position) {
        switch (kinds) {
            case OfficeStaff:
                return new OfficeStaff(name, position);
            case Teacher:
                return new Teacher(name, position);
            default:
                return null;
        }
    }
    public static SchoolMember MemberFactory(SchoolMember.Member kinds, String name, int number) {
        return new Student(name, number);
    }
}

class OperateMemberAddExisting implements Screen {
    @Override
    public Screen run(SchoolScanner input) {
        return null;
    }
}

class OperateMemberRemove implements Screen {
    @Override
    public Screen run(SchoolScanner input) {
        ShowScreen.ShowMemberScreen();
        SchoolMember.ShowMemberList();
        ShowScreen.OperateMemberRemoveScreen();
        int inputIndex = input.InputSelectIndex(1, SchoolMember.getMemberSize() +1);//SchoolMember.getMemberSize() +1 としているのは、見掛け上のindexを1始まりにするため
        if (inputIndex -1 >= 0) {
            inputIndex--;//All_memberのindex参照を0からではなく1空にしたいため、1を引く;
            ShowScreen.SeparateScreen();
            SchoolMember.getMember(inputIndex).showValue(inputIndex +1);
            return new OperateMemberRemoveConfirmation(inputIndex);
        } else if (inputIndex == -1) {
            return new OperateMember();
        } else if (inputIndex == -2) {
            return new OperateMember();
        } else {
            return new OperateMember();
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
    private static void MainRun() {
        SchoolScanner input = new SchoolScanner(new Scanner(System.in));
        Screen ScreenStatus = new Menu();
        do { 
            ScreenStatus = ScreenStatus.run(input);
        } while (!(ScreenStatus == null));
        input.tryClose();
    }
    public static void main(String[] args) throws MismatchNormalException {
        MainRun();
        /*
        SchoolMember Member = new Teacher("坂本浩二", "一般教員");
        SchoolMember.AddMember(Member);
        Member.AddVehicle(new Bike("日産", 40, 50));
        Member.AddVehicle(new Bike("トヨタ", 15, 30));
        SchoolMember Member1 = new Teacher("吉田輝元", "一般教員");
        SchoolMember.AddMember(Member1);
        Member1.AddVehicle(new Bicycle("ブリヂストン", 40, 50));
        Member1.AddVehicle(new Walk("徒歩", 3, 45));
        SchoolMember.AddMember(new Teacher("児玉健斗", "一般教員"));
        SchoolMember.RemoveMember(SchoolMember.getMember(2));
        SchoolMember.ShowMemberList();
        */
    }
}
//次直す
        
/*
        if (kinds == SchoolMember.Member.OfficeStaff || kinds == SchoolMember.Member.Teacher) {
            ShowScreen.OperateMemberAddNewPositionScreen();
            String position = input.InputLine();
            member = MemberFactory(kinds, name, position);
            SchoolMember.AddMember(member);
            return new OperateMemberAdd();//次直す
        } else if (kinds == SchoolMember.Member.Student) {
            ShowScreen.OperateMemberAddNewNumberScreen();
            int number = input.InputFigure();
            if (number >= 0) {
                member = MemberFactory(kinds, name, number);
                SchoolMember.AddMember(member);
                return new OperateMemberAdd();//次直す
            } else {
                return new OperateMemberAddNewPosition(kinds, name);//再度繰り返す
            }
            
        } else {
            System.out.println("エラーです.");
            return new OperateMemberAddNew();
        }
        */