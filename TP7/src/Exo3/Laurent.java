package Exo3;

import Exo1.Calcul;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Laurent {

    public static final int PORT = 12340;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Serveur en attente de connexion...");

            Socket arielle = server.accept();
            InputStream inArielle = arielle.getInputStream();
            OutputStream outArielle = arielle.getOutputStream();
            System.out.println("Client connecté");

            RSA rsa = new RSA();

            System.out.println(rsa.decode(rsa.encode(12345)));

            System.out.println("Envoie de la clé RSA : ");
            outArielle.write(rsa.getPublicKey().toString().getBytes());



            byte[] buffer = new byte[1024];
            int bytesRead = inArielle.read(buffer);
            String message = new String(buffer, 0, bytesRead);

            System.out.println("Message reçu : " + message);

            long messageDecoded = rsa.decode(Long.parseLong(message));

            System.out.println("Message décodé : " + messageDecoded);

            arielle.close();


            /*Socket socketBertrand = new Socket("localhost", Bertrand.PORT);
            System.out.println("Connexion au serveur...");

            InputStream inBertrand = socketBertrand.getInputStream();
            OutputStream outBertrand = socketBertrand.getOutputStream();
            System.out.println("Client connecté");

            RSA rsa = new RSA();
            // Envoi de M
            System.out.println("Envoi de la clé RSA : ");
            outArielle.write(rsa.getPublicKey().toString().getBytes());

            buffer = new byte[1024];
            bytesRead = inArielle.read(buffer);
            String messageArrielle = new String(buffer, 0, bytesRead);
            System.out.println("Message reçu : " + messageArrielle);

            long messageArrielleDecoded = RSA.decode(Long.parseLong(messageArrielle), Long.parseLong(key.get(0)), Long.parseLong(key.get(1)));
            System.out.println("Message décodé : " + messageArrielleDecoded);

            outBertrand.write(String.valueOf(rsa.encode(messageArrielleDecoded)).getBytes());

            socketBertrand.close();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
