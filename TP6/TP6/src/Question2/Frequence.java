package Question2;

public class Frequence {

    public static double[] frequenceFr = {
            7.636,
            0.901,
            3.260,
            3.669,
            14.715,
            1.066,
            0.866,
            0.737,
            7.529,
            0.613,
            0.074,
            5.456,
            2.968,
            7.095,
            5.796,
            2.521,
            1.362,
            6.693,
            7.948,
            7.244,
            6.311,
            1.838,
            0.049,
            0.427,
            0.128,
            0.326
    };
    public static double[] frequence(String text){
        double[] frequence = new double[26];
        int total = 0;
        text = text.toUpperCase();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                frequence[c - 'A']++;
                total++;
            }
        }
        for (int i = 0; i < 26; i++) {
            frequence[i] /= total   ;
            frequence[i] *= 100;
        }
        return frequence;
    }

    public static double coincidance(String text){
        double coincidance = 0;
        for(int i = 'A'; i <= 'Z'; i++){
            int count = 0;
            for(int j = 0; j < text.length(); j++){
                if(text.charAt(j) == i){
                    count++;
                }
            }
            coincidance +=  (double)(count * (count - 1)) / (double)(text.length() * (text.length() - 1));
        }
        return coincidance;
    }

    public static double distance(double[] frequence ){
        double distance = 0;
        for (int i = 0; i < 26; i++) {
            distance += Math.abs(frequence[i] - Frequence.frequenceFr[i]);
        }
        return distance;
    }

}
