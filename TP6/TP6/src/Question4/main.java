package Question4;


public class main {
    public static void main(String[] args) {
        Vigenere c = new Vigenere();
        String text = "Maitre Corbeau, sur un arbre perche,Tenait en son bec un fromage.Maotre Renard, par l'odeur alleche,Lui tint à peu pres ce langage :Et bonjour, Monsieur du Corbeau.Que vous etes joli ! que vous me semblez beau !Sans mentir, si votre ramageSe rapporte à votre plumage,Vous etes le Phenix des hôtes de ces bois.À ces mots, le Corbeau ne se sent pas de joie ;Et pour montrer sa belle voix,Il ouvre un large bec, laisse tomber sa proie.Le Renard s'en saisit, et dit : Mon bon Monsieur,Apprenez que tout flatteurVit aux depens de celui qui l'ecoute.Cette leçon vaut bien un fromage, sans doute.Le Corbeau honteux et confusJura, mais un peu tard, qu'on ne l'y prendrait plus." ;
        String chiffrer = c.chiffrer(text);
        System.out.println(chiffrer);
        System.out.println(c.dechiffrer(chiffrer));
        System.out.println(c.key);
        System.out.println(CasageDeVigenere.textChiffreChoisi(c));
        System.out.println(CasageDeVigenere.textClairChoisi(c));
        System.out.println(CasageDeVigenere.textClairConnu(c, c.chiffrer("A"), "A"));


    }
}
