package Question3;

import Question2.Frequence;

import java.util.*;

public class CassageDeCesar {
    public static String textChiffreChoisi(Cesar c){
        char dechiffre = c.dechiffrer("Z").charAt(0);
        return 'Z' + dechiffre + "";
    }

    public static String textClairChoisi(Cesar c){
        char chiffre = c.chiffrer("A").charAt(0);
        return chiffre + 'A' + "";
    }

    public static String textChiffreSeul(Cesar c, String textChiffre){
        ArrayList<Double> freq = new ArrayList<>();
        for (double f: Frequence.frequence(textChiffre)){
            freq.add(f);
        }

        ArrayList<Double> freqfr = new ArrayList<>();
        for (double f: Frequence.frequenceFr){
            freqfr.add(f);
        }

        Map<Character,Character> key= new HashMap<>();

        System.out.println(freq);

        System.out.println(freqfr);

        for(int i = 0; i < 26; i++){
            double max = 0;
            int index = 0;
            for(int j = 0; j < freq.size(); j++){
                if(freq.get(j) == null)
                    continue;
                if(freq.get(j) > max){
                    max = freq.get(j);
                    index = j;
                }
            }
            freq.set(index,null);

            double maxfr = 0;
            int indexfr = 0;

            for(int j = 0; j < freqfr.size(); j++){
                if(freqfr.get(j) == null)
                    continue;
                if(freqfr.get(j) > maxfr){
                    maxfr = freqfr.get(j);
                    indexfr = j;
                }
            }
            freqfr.set(indexfr,null);

            key.put((char) (index + 'A'), (char) (indexfr + 'A'));
        }

        return new Cesar(key).dechiffrer(textChiffre);
    }

    public static String textClairConnu(Cesar c, String textChiffre, String textClair){
        int decalage = textChiffre.charAt(0) + textClair.charAt(0);
        return decalage + "";
    }
}
