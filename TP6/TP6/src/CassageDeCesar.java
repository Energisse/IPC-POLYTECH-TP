public class CassageDeCesar {
    public static String textChiffreChoisi(Cesar c){
        char dechiffre = c.dechiffrer("Z").charAt(0);
        return 'Z' - dechiffre + "";
    }

    public static String textClairChoisi(Cesar c){
        char chiffre = c.chiffrer("A").charAt(0);
        return chiffre - 'A' + "";
    }

    public static String textClairConnu(Cesar c, String textChiffre, String textClair){
        int decalage = textChiffre.charAt(0) - textClair.charAt(0);
        return decalage + "";
    }
}
