import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
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
    private static final int PORT = 110;

    /**
     * uri du serveur
     */
    private static final String SERVEUR_URI = "localhost";

    public static void main(String[] args) {
        try {
            var serveur = new Socket(InetAddress.getByName(SERVEUR_URI), PORT);

            var in = new BufferedInputStream(serveur.getInputStream());
            var out = new BufferedOutputStream(serveur.getOutputStream());

            while (!serveur.isClosed()) {

                ByteArrayOutputStream resultBuff = new ByteArrayOutputStream();
                byte[] buff = new byte[1024];
                do {
                    int l = in.read(buff, 0, buff.length);
                    resultBuff.write(buff, 0, l);
                } while (in.available() > 0);

                String message = resultBuff.toString();

                System.out.println("Message reçu : " + message);

                Scanner scanner = new Scanner(System.in);
                System.out.println("Entrez un texte : ");
                String texte = scanner.nextLine()+"\n";
                System.out.println("Texte saisi : " + texte);
                out.write(texte.getBytes(), 0, texte.length());
                out.flush();
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
