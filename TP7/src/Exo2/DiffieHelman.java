package Exo2;

import Exo1.Calcul;

public class DiffieHelman {

    private int g;
    private int p;
    private int a;

    public DiffieHelman(int g, int p, int a) {
        this.g = g;
        this.p = p;
        this.a = a;
    }

    public long encode() {
        return Calcul.expMod(g, a, p);
    }

    public long decode(int b) {
        return Calcul.expMod(g, a*b, p);
    }
}
