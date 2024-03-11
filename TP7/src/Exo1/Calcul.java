package Exo1;

import java.util.ArrayList;
import java.util.List;

public class Calcul {
    public static int inversionModulo(int a, int b) {
        int x = 0, y = 1, lastx = 1, lasty = 0, temp;
        while (b != 0) {
            int q = a / b;
            int r = a % b;
            a = b;
            b = r;
            temp = x;
            x = lastx - q * x;
            lastx = temp;
            temp = y;
            y = lasty - q * y;
            lasty = temp;
        }
        return lastx;
    }

    public static ArrayList<Integer> generateurPremier(int n) {
        ArrayList<Integer> premiers = new ArrayList<Integer>();
        for (int i = 2; i < n; i++) {
            if (estPremier(i)) {
                premiers.add(i);
            }
        }
        return premiers;
    }

    public static boolean estPremier(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Integer> diviseursPremier(long n) {
        ArrayList<Integer> diviseurs = new ArrayList<Integer>();
        for (int i = 2; i <= n; i++) {
            if (n % i == 0 && estPremier(i)) {
                diviseurs.add(i);
            }
        }
        return diviseurs;
    }

    public static long expMod(long a, long b, long n) {
        long r = 1;
        for (long i = 0; i < b; i++) {
            r = (r * a) % n;
        }
        return r;
    }

    public static int generateurZpZ(int p) {
        ArrayList<Integer> diviseurs = diviseursPremier(p - 1);
        for (int i = 2; i < p; i++) {
            boolean generateur = true;
            for (int j = 0; j < diviseurs.size(); j++) {
                if (expMod(i, (p - 1) / diviseurs.get(j), p) == 1) {
                    generateur = false;
                    break;
                }
            }
            if (generateur) {
                return i;
            }
        }
        return -1;
    }

    public static List<Long> euclideEtendu(long a, long b) {
        long r = a;
        long r1 = b;
        long u = 1;
        long v = 0;
        long u1 = 0;
        long v1 = 1;
        long q;
        long rs;
        long us;
        long vs;
        while (r1 != 0) {
            q = r / r1;
            rs = r;
            us = u;
            vs = v;
            r = r1;
            u = u1;
            v = v1;
            r1 = rs - q * r1;
            u1 = us - q * u1;
            v1 = vs - q * v1;
        }
        List<Long> result = new ArrayList<Long>();
        result.add(r);
        result.add(u);
        result.add(v);
        return result;
    }
}
