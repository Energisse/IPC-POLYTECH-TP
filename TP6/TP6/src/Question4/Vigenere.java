package Question4;

import java.util.Locale;

public class Vigenere {
    public final String key;

    public Vigenere() {
        char[] text = new char[10];
        for(int i = 0; i < 10; i++){
            text[i] = (char) ( Math.random()*26 + 'A');
        }
        this.key = new String(text);
    }

    public Vigenere(String key) {
        this.key = key;
    }

    public String chiffrer(String text){
        String result = "";
        text = text.toUpperCase(Locale.ROOT);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                c = (char) (((c - 'A' + key.charAt(i % key.length())-'A')  % 26) + 'A');
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
                int decalage = c - key.charAt(i % key.length())+'A' ;
                if(decalage < 'A')
                    decalage += 26;
                c = (char)decalage;
            }
            result += c;
        }
        return result;
    }
}
