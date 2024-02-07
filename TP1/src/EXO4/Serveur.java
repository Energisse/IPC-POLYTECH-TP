package EXO4;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Date;

public class Serveur {

    /**
     * Port d'écoute du serveur
     */
    private static final int PORT = 9876;

    public static void main(String[] args) {
        try {
            var serveur = new ServerSocket(PORT);
            System.out.println("EXO4.Serveur démarré sur le port " + PORT);

            while (true) {
                try {
                    var client = serveur.accept();
                    System.out.println("Nouvelle connexion");

                    var out = new BufferedOutputStream(client.getOutputStream());
                    var in = new BufferedInputStream(client.getInputStream());

                    while (!client.isClosed()) {

                        byte[] buffer = new byte[1024];
                        int bytesRead = in.read(buffer);
                        if(bytesRead == -1) {
                            client.close();
                            System.out.println("Connexion fermée");
                            break;
                        }
                        String message = new String(buffer, 0, bytesRead);
                        System.out.println("Message reçu : " + message);

                        var ms = System.currentTimeMillis();
                        var date = new Date(ms);
                        if (message.equals("DATE")) {
                            var messageDate = date.getYear() + "y" + date.getMonth() + "m" + date.getDay() + "d";
                            out.write(messageDate.getBytes(), 0, messageDate.length());
                            out.flush();
                        } else if (message.equals("HOUR")) {
                            var messageDate = date.getHours() + "h" + date.getMinutes() + "m" + date.getSeconds() + "s";
                            out.write(messageDate.getBytes(), 0, messageDate.length());
                            out.flush();
                        } else if (message.equals("FULL")) {
                            var messageDate = date.toString() + " => " + date.getHours() + "h" + date.getMinutes() + "m" + date.getSeconds() + "s";
                            out.write(messageDate.getBytes(), 0, messageDate.length());
                            out.flush();
                        } else if (message.equals("QUIT")) {
                            client.close();
                            System.out.println("Connexion fermée");
                        } else {
                            String response = "Tuent sé lyrent !!";
                            out.write(response.getBytes(), 0, response.length());
                            out.flush();
                        }
                    }
                } catch (SocketException e) {
                    System.out.println("Connexion fermée");
                }
            }
        }
        catch (IOException e) {
            System.out.println("Erreur lors de la création du socket : " + e.getMessage());
            e.printStackTrace();
        }

    }
}