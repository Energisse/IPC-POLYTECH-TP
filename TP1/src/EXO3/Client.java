package EXO3;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.*;
import java.util.Date;

public class Client {

    /**
     * Port d'écoute du serveur
     */
    private  static  final  int  PORT  =  9876;

    /**
     * uri du serveur
     */
    private static final String SERVEUR_URI = "localhost";

    public static void main(String[] args) {
        try{
            var serveur = new Socket(InetAddress.getByName(SERVEUR_URI),PORT);
            System.out.println("EXO3.Client démarré");

            var in = new BufferedInputStream(serveur.getInputStream());

            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            String message = new String(buffer, 0, bytesRead);
            System.out.println("Message reçu : " + message + " ms" + " => " + new Date(Long.parseLong(message)));
            serveur.close();
        }
        catch (UnknownHostException e) {
            System.out.println("Host inconnu : " + e.getMessage());
            e.printStackTrace();
        }
        catch (SocketException e) {
            System.out.println("Erreur lors de la création du socket : " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi du message : " + e.getMessage());
            e.printStackTrace();
        }

    }
}
