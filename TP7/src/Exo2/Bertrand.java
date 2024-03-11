package Exo2;

import Exo1.Calcul;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Bertrand {

    public static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Serveur en attente de connexion...");

            int p = 23;
            int g = Calcul.generateurZpZ(p);

            Socket socket = server.accept();
            InputStream in =  socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            System.out.println("Client connecté");

            int b =  new Random().nextInt(100);
            System.out.println("b : " + b);

            DiffieHelman dh = new DiffieHelman(g, p, b);
            long A = dh.encode();
            // Envoi de A
            System.out.println("Envoi de A : " + A);
            out.write((int) A);


            // Réception de B
            long B = in.read();
            System.out.println("Réception de B : " + B);
            // Calcul de la clé
            long key = Calcul.expMod(B, b, p);

            System.out.println("Clé partagée : " + key);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
