package EXO3;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;

public class Serveur {

    /**
     * Port d'écoute du serveur
     */
    private  static  final  int  PORT  =  9876;

    public static void main(String[] args) {
        try{
            var serveur = new ServerSocket(PORT);
            System.out.println("EXO3.Serveur démarré sur le port " + PORT);

            while(true){
                var client = serveur.accept();
                System.out.println("Nouvelle connexion");

                var out = new BufferedOutputStream(client.getOutputStream());

                Long ms = System.currentTimeMillis();
                var message = ms.toString();
                out.write(message.getBytes(),0,message.length());
                System.out.println("Message envoyé : " + message + " ms" + " => " + new Date(ms));
                out.flush();
                client.close();
                System.out.println("Connexion fermée");
            }
        }
        catch (IOException e) {
            System.out.println("Erreur lors de la création du socket : " + e.getMessage());
            e.printStackTrace();
        }

    }
}
