import java.nio.charset.Charset;
import java.util.Scanner;

class NeoScanner {
    private Scanner scanner;
    private String box[] = {
        "フリフリ",
        "とろ～ん",
        "ぐるんぐるん",
        "しょんぼり",
        "ぽふぽふ"
    };

    NeoScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean MatchString(String word, String box[]) {
        for (String val : box) {
            if (val.equals(word)) {
                return true;
            }
        }
        return false;
    }

    public String InputText() throws MissMatchException {
        String line = scanner.nextLine();
        line = line.replace('\u301c', '～');//変換しないと文字が対応してくれない
        //line = line.replace('\u301C', '～');
        
        /*/
        for (char c : line.toCharArray()) {
            System.out.println(Integer.toHexString(c));
        }

        System.out.println(box[1]);
        for (char c : box[1].toCharArray()) {
            System.out.println(Integer.toHexString(c));
        }
        */
        

        if (!MatchString(line, box)) {
            throw new MissMatchException();
        }

        return line;
    }

    public void close() {
        scanner.close();
    }

    public String getBox(int index) {
        return box[index];
    }
}

enum TailMode {
    FURIFURI("楽しい"),
    TOROOON("気持ちいい"),
    GURUNGURUN("わん！"),
    SHONBORI("なでてほしい"),
    POFUPOFU("ふわふわ");

    private String message;

    TailMode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

enum KoyoneeMood {
    YASASHII(new String[] {
        "気分よし",
        "どんまい"
        }),
    GENKI(new String[] {
        "げんきすぎてつかれた",
        "げんきやね"
        }),
    NUKUMORI(new String[] {
        "あったけぇ",
        "こころがあったけぇ"
        }),
    SHITTO(new String[] {
        "かまってほしいかも…",
        "はやく～"
        });

    private String messages[];
    KoyoneeMood(String messages[]) {
        this.messages = messages;
    }

    public String getRandomMessage() {
        int index = (int)(Math.random() * messages.length);
        return this.messages[index];
    }
}

class TailFactory {
    public static TailMode ModeChange(String mood) {
        switch(mood) {
            case "フリフリ":
                return TailMode.FURIFURI;
            case "とろ～ん":
                return TailMode.TOROOON;
            case "ぐるんぐるん":
                return TailMode.GURUNGURUN;
            case "しょんぼり":
                return TailMode.SHONBORI;
            case "ぽふぽふ":
                return TailMode.POFUPOFU;
            default:
                return null;
        }
    }
}

class MissMatchException extends Exception {
    MissMatchException() {
        super();
    }
}

public class DogMoodManager2 {
    static String Mood = "";
    public static void main(String[] arge) throws MissMatchException {
        //NeoScanner scanner = new NeoScanner(new Scanner(System.in, "shift-jis"));
        NeoScanner scanner = new NeoScanner(new Scanner(System.in, Charset.forName("Shift_JIS")));
        //String Mood = "";
        boolean goodAnswer = false;
        while(!goodAnswer){
            try {
                System.out.println("今日の気分をおしえて？");
                String[] Comment = {
                    "フリフリ",
                    "とろ～ん",
                    "ぐるんぐるん",
                    "しょんぼり",
                    "ぽふぽふ"
                };

                int i = 1;
                for(String comment : Comment) System.out.printf("%d : %s\n", i++, comment);
                Mood = scanner.InputText();
                goodAnswer = true;
            } catch(MissMatchException e) {
                System.out.println("う～ん……もういっかい");
            }
            
        }
        scanner.close();

        TailMode current = TailFactory.ModeChange(Mood);
        System.out.println("モード：" + current);
        System.out.println("今日の気分メッセージ：" + current.getMessage());

        KoyoneeMood moods[] = KoyoneeMood.values();
        int index = (int)(Math.random() * moods.length);
        KoyoneeMood mood = moods[index];
        System.out.println("気持ち："+mood.getRandomMessage());
    }
}
