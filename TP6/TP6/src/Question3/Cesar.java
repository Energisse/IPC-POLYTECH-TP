package Question3;

import java.util.*;

public class Cesar {
    public final Map<Character,Character> key = new HashMap<>();

    public Cesar() {
        char[] ilfautunnomalaliste = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        List<Character> list = new ArrayList<>();
        for (char c : ilfautunnomalaliste) {
            list.add(c);
        }

        Collections.shuffle(list);
        for (int i = 0; i < 26; i++) {
            key.put(ilfautunnomalaliste[i], list.get(i));
        }
    }

    public Cesar(Map<Character, Character> key) {
        System.out.println(key);
        this.key.putAll(key);
    }


    public String chiffrer(String text){
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase(Locale.ROOT);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                c = key.get(c);
            }
            result.append(c);
        }
        return result.toString();
    }

    public String dechiffrer(String text){
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase(Locale.ROOT);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            for (Map.Entry<Character, Character> entry : key.entrySet()) {
                if (entry.getValue().equals(c)) {
                    c = entry.getKey();
                    break;
                }
            }
            result.append(c);
        }
        return result.toString();
    }
}
