package EXO2;

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
            var client = new DatagramSocket();
            System.out.println("EXO2.Client démarré");

            Long T1 = System.currentTimeMillis();
            String message = T1.toString();
            var dp = new DatagramPacket(message.getBytes(),message.length(), InetAddress.getByName(SERVEUR_URI),PORT);
            client.send(dp);
            System.out.println("T1 : " + T1 + " ms" + " => " + new Date(T1));

            byte[] buffer = new byte[1024];
            var dp2 = new DatagramPacket(buffer, buffer.length);
            client.receive(dp2);
            Long T2 = System.currentTimeMillis();
            System.out.println("T2 : " + T2 + " ms" + " => " + new Date(T2));

            String reponse = new String(dp2.getData(),0,dp2.getLength());
            String[] reponseParts = reponse.split(" ");

            Long T1P = Long.parseLong(reponseParts[1]);
            Long T2P = Long.parseLong(reponseParts[2]);

            System.out.println("T1P : " + T1P + " ms" + " => " + new Date(T1P));
            System.out.println("T2P : " + T2P + " ms" + " => " + new Date(T2P));
            System.out.println("δ : " + ((T2 - T1) - (T2P - T1P)) + " ms");
            System.out.println("θ : " + (T1P+T2P-T1-T2) / 2 + " ms");

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
