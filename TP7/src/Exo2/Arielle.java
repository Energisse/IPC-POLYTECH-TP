package Exo2;

import Exo1.Calcul;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Arielle {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost",Bertrand.PORT);
            System.out.println("Connexion au serveur...");

            int p = 23;
            int g = Calcul.generateurZpZ(p);

            InputStream in =  socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            System.out.println("Client connecté");

            int a =  new Random().nextInt(100);
            System.out.println("a : " + a);

            DiffieHelman dh = new DiffieHelman(g, p, a);
            long A = dh.encode();
            // Envoi de A
            System.out.println("Envoi de A : " + A);
            out.write((int) A);

            // Réception de B
            int B = in.read();
            System.out.println("Réception de B : " + B);

            // Calcul de la clé
            long key = Calcul.expMod(B, a, p);

            System.out.println("Clé partagée : " + key);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
