package EXO2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;

public class Serveur {

    /**
     * Port d'écoute du serveur
     */
    private  static  final  int  PORT  =  9876;

    public static void main(String[] args) {
        try{
            var serveur = new DatagramSocket(PORT);
            System.out.println("EXO2.Serveur démarré sur le port " + PORT);

            while(true){

                byte[] buffer = new byte[1024];
                var dp = new DatagramPacket(buffer, buffer.length);
                serveur.receive(dp);
                Long T1P = System.currentTimeMillis();
                String heure = new String(dp.getData(),0,dp.getLength());
                Long T1 = Long.parseLong(heure);
                System.out.println("Heure reçue : " + T1 + " ms" + " => " + new Date(T1));

                Long T2P = System.currentTimeMillis();
                String message = T1.toString() + " " + T1P.toString() + " " + T2P.toString();
                var dp2 = new DatagramPacket(message.getBytes(),message.length(), dp.getAddress(), dp.getPort());
                serveur.send(dp2);
                System.out.println("Heure envoyée : " + T2P + " ms" + " => " + new Date(T2P));
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
