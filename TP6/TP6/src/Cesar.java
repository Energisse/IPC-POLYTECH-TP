import java.util.Locale;

public class Cesar {
    public final int key;

    public Cesar() {
        this.key = (int) (Math.random()*26);
    }

    public Cesar(int key) {
        this.key = key;
    }

    public String chiffrer(String text){
        String result = "";
        text = text.toUpperCase(Locale.ROOT);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                c = (char) (c +key);
                if(c > 'Z')
                    c -= 26;
            }
            result += c;
        }
        return result;
    }

    public String dechiffrer(String text){
        String result = "";
        text = text.toUpperCase(Locale.ROOT);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                c = (char) (c -key);
                if(c < 'A')
                    c += 26;
            }
            result += c;
        }
        return result;
    }
}
