package EXO4_MULTI_THREADS;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    /**
     * Port d'écoute du serveur
     */
    private static final int PORT = 9876;

    /**
     * uri du serveur
     */
    private static final String SERVEUR_URI = "localhost";

    public static void main(String[] args) {
        try {
            var serveur = new Socket(InetAddress.getByName(SERVEUR_URI), PORT);
            System.out.println("EXO4.Client démarré");

            var in = new BufferedInputStream(serveur.getInputStream());
            var out = new BufferedOutputStream(serveur.getOutputStream());

            while (!serveur.isClosed()) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Choisissez: \n" +
                        " - DATE : Pour avoir la date \n" +
                        " - HOUR : Pour avoir l'heure \n" +
                        " - FULL : Pour avoir la date complète \n" +
                        " - QUIT : Pour quitter le serveur \n");

                String choix = scanner.nextLine();

                out.write(choix.getBytes(), 0, choix.length());
                out.flush();

                if(choix.equals("QUIT")){
                    serveur.close();
                    System.out.println("Connexion fermée");
                    break;
                }

                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);
                String message = new String(buffer, 0, bytesRead);
                System.out.println("Message reçu : " + message);

            }
        } catch (UnknownHostException e) {
            System.out.println("Host inconnu : " + e.getMessage());
            e.printStackTrace();
        } catch (SocketException e) {
            System.out.println("Erreur lors de la création du socket : " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'envoi du message : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
