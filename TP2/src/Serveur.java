import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Serveur {

    /**
     * Port d'écoute du serveur
     */
    private static final int PORT = 9876;

    /**
     * Maximum de clients connectés
     */
    private static final int MAX_CLIENTS = 2;

    public static void main(String[] args) {
        try {
            var serveur = new ServerSocket(PORT);
            System.out.println("Serveur démarré sur le port " + PORT);

            ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);
            while (true) {
                var client = serveur.accept();
                System.out.println("Nouvelle connexion");

                pool.execute(new Controle(client));
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la création du socket : " + e.getMessage());
            e.printStackTrace();
        }

    }
}