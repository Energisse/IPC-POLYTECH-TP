package Question1;

public class main {
    public static void main(String[] args) {
        Cesar c = new Cesar();
        String text = "Hello World!";
        String chiffrer = c.chiffrer(text);
        System.out.println(chiffrer);
        System.out.println(c.dechiffrer(chiffrer));
        System.out.println(c.key);
        System.out.println(CassageDeCesar.textChiffreChoisi(c));
        System.out.println(CassageDeCesar.textClairChoisi(c));
        System.out.println(CassageDeCesar.textClairConnu(c, c.chiffrer("A"), "A"));


    }
}
