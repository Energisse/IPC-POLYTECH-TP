package Question4;

import Question2.Frequence;

public class CasageDeVigenere {
    public static String textChiffreChoisi(Vigenere c){

        String textChiffre = "Maitre Corbeau, sur un arbre perche,Tenait en son bec un fromage.Maotre Renard, par l'odeur alleche,Lui tint à peu pres ce langage :Et bonjour, Monsieur du Corbeau.Que vous etes joli ! que vous me semblez beau !Sans mentir, si votre ramageSe rapporte à votre plumage,Vous etes le Phenix des hôtes de ces bois.À ces mots, le Corbeau ne se sent pas de joie ;Et pour montrer sa belle voix,Il ouvre un large bec, laisse tomber sa proie.Le Renard s'en saisit, et dit : Mon bon Monsieur,Apprenez que tout flatteurVit aux depens de celui qui l'ecoute.Cette leçon vaut bien un fromage, sans doute.Le Corbeau honteux et confusJura, mais un peu tard, qu'on ne l'y prendrait plus." ;

        double distance = Double.MAX_VALUE;
        int maxIndex = 0;
        String text = "";
        for (int i = 0; i < textChiffre.length(); i++) {
            text+= textChiffre.charAt(i);
            double coincidance = Frequence.coincidance(c.chiffrer(text));
            if(Math.abs(0.07-coincidance) < distance){
                distance = Math.abs(0.07-coincidance);
                maxIndex = i;
            }
        }

        return  maxIndex + "";

    }

    public static String textClairChoisi(Vigenere c){
        char chiffre = c.chiffrer("A").charAt(0);
        return chiffre - 'A' + "";
    }

    public static String textClairConnu(Vigenere c, String textChiffre, String textClair){
        int decalage = textChiffre.charAt(0) - textClair.charAt(0);
        return decalage + "";
    }
}
