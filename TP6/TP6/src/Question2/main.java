package Question2;

import Question1.Cesar;

public class main {
    public static void main(String[] args) {
        String text = "Hello World!";
        double[] frequence = Frequence.frequence(text);
        for (int i = 0; i < 26; i++) {
            System.out.println((char) ('A' + i) + " : " + frequence[i]);
        }
        System.out.println(Frequence.coincidance("BONJOURLEMONDE"));

        String chiffre = "VK MSQKVO KIKXD MRKXDO DYED VODO CO DBYEFK PYbD NOZYEBFEO AEKXN VK LSCO PED FOXEO";
        frequence = Frequence.frequence(chiffre);
        int max = 0;
        int maxIndex = 0;
        for (int i = 0; i < 26; i++) {
            if(frequence[i] > max){
                max = (int) frequence[i];
                maxIndex = i;
            }
        }

        System.out.println(new Cesar(maxIndex+'A'-'E').dechiffrer(chiffre));

    }
}
