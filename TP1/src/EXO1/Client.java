package EXO1;

import java.io.IOException;
import java.net.*;

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
            var client = new DatagramSocket();
            System.out.println("EXO1.Client démarré");

            String message = "Hello World";
            var dp = new DatagramPacket(message.getBytes(),message.length(), InetAddress.getByName(SERVEUR_URI),PORT);
            client.send(dp);
            System.out.println("Message envoyé");

            byte[] buffer = new byte[1024];
            var dp2 = new DatagramPacket(buffer, buffer.length);
            client.receive(dp2);
            System.out.println("Message reçu : " + new String(dp2.getData(),0,dp2.getLength()));

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
