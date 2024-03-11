package Exo3;

import Exo1.Calcul;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RSA {

    public long private_key;
    public long public_key;

    private long p;

    private long q;

    private  long e;

    public RSA() {
        ArrayList<Integer> premiers = Calcul.generateurPremier(10000);
        this.p = premiers.get(new Random().nextInt(premiers.size()));
        this.q = premiers.get(new Random().nextInt(premiers.size()));
        this.public_key = this.p*this.q;
        this.e = Calcul.diviseursPremier((this.p-1) * (this.q-1)).get(0);
        this.private_key = Calcul.euclideEtendu(this.e, (this.p-1) * (this.q-1)).get(1);
    }


    public long encode(long m) {
        return Calcul.expMod(m, this.e,this.public_key);
    }

    public long decode(long c) {
        return Calcul.expMod(c, this.private_key,this.public_key);
    }

    public static long encode(long m, long e, long n) {
        return Calcul.expMod(m, e, n);
    }

    public List<Long> getPublicKey(){
        List<Long> keys = new ArrayList<>();
        keys.add(this.public_key);
        keys.add(this.e);
        return keys;
    }
}
