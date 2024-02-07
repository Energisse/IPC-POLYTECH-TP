package EXO1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Serveur {

    /**
     * Port d'écoute du serveur
     */
    private  static  final  int  PORT  =  9876;

    public static void main(String[] args) {
        try{
            var serveur = new DatagramSocket(PORT);
            System.out.println("EXO1.Serveur démarré sur le port " + PORT);

            while(true){

                byte[] buffer = new byte[1024];
                var dp = new DatagramPacket(buffer, buffer.length);
                serveur.receive(dp);
                System.out.println("Message reçu : " + new String(dp.getData(),0,dp.getLength()));

                var message = "Bien reçu !";
                var dp2 = new DatagramPacket(message.getBytes(),message.length(), dp.getAddress(), dp.getPort());
                serveur.send(dp2);
                System.out.println("Message envoyé");
            }
        }
        catch (SocketException e) {
            System.out.println("Erreur lors de la création du socket : " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erreur lors de la réception du message : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
