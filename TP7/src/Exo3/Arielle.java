package Exo3;

import Exo1.Calcul;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class Arielle {

    public static void main(String[] args) {
        try {
            RSA rs2 = new RSA();
            System.out.println(rs2.decode(rs2.encode(12345)));

            Socket socket = new Socket("localhost", Laurent.PORT);
            System.out.println("Connexion au serveur...");


            InputStream in =  socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            System.out.println("Client connecté");

            RSA rsa = new RSA();
            // Envoi de M
            System.out.println("Reception de la clé RSA : ");
            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            String message = new String(buffer, 0, bytesRead);

            String replace = message.replace("[","").replace("]","").replace(" ","");
            String[] key = replace.split(",");

            System.out.println("Clé reçue : " + key[0] + " " + key[1]);
            long encoded = RSA.encode(12345, Long.parseLong(key[1]), Long.parseLong(key[0]));
            System.out.println("Message encode : " + encoded);
            out.write(String.valueOf(encoded).getBytes());

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
