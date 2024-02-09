import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.*;
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


    /**
     * Socket de transfert
     */
    private static Object transferSocket;

    public static void main(String[] args) {
        try {
            var serveur = new Socket(InetAddress.getByName(SERVEUR_URI), PORT);
            System.out.println("Client démarré");

            var in = new BufferedInputStream(serveur.getInputStream());
            var out = new BufferedOutputStream(serveur.getOutputStream());

            while (!serveur.isClosed()) {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Login : ");
                String login = scanner.nextLine();

                out.write(login.getBytes(), 0, login.length());
                out.flush();

                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);
                String message = new String(buffer, 0, bytesRead);

                var splited = message.split(" ", 1);
                if (!splited[0].equals("200")) continue;

                System.out.println("Password : ");
                String password = scanner.nextLine();

                out.write(password.getBytes(), 0, password.length());
                out.flush();

                bytesRead = in.read(buffer);
                message = new String(buffer, 0, bytesRead);
                splited = message.split(" ", 1);

                if (splited[0].equals("200")) {
                    System.out.println("Login réussi");
                    break;
                }
            }

            while (!serveur.isClosed()) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Commande : ");
                String commande = scanner.nextLine();
                if (commande.split(" ", 2)[0].equals("PORT")) {
                    if (transferSocket != null) {
                        if (transferSocket instanceof ServerSocket) {
                            ((ServerSocket) transferSocket).close();
                        } else {
                            ((Socket) transferSocket).close();
                        }
                    }

                    var arg = commande.split(" ", 2)[1].split(",");
                    int a = Integer.parseInt(arg[0]);
                    int b = Integer.parseInt(arg[1]);
                    int c = Integer.parseInt(arg[2]);
                    int d = Integer.parseInt(arg[3]);
                    int e = Integer.parseInt(arg[4]);
                    int f = Integer.parseInt(arg[5]);

                    ServerSocket serverSocket = new ServerSocket(e * 256 + f, 1, Inet4Address.getByAddress(new byte[]{(byte) a, (byte) b, (byte) c, (byte) d}));
                    transferSocket = serverSocket;

                    System.out.println("En attente de connexion");
                    serverSocket.accept();
                    System.out.println("Connexion établie");
                }

                out.write(commande.getBytes(), 0, commande.length());
                out.flush();

                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);
                String message = new String(buffer, 0, bytesRead);
                System.out.println(message);

                if(commande.split(" ", 2)[0].equals("PASV")) {
                    var arg = message.split(" ", 2)[1].split(",");
                    int a = Integer.parseInt(arg[0]);
                    int b = Integer.parseInt(arg[1]);
                    int c = Integer.parseInt(arg[2]);
                    int d = Integer.parseInt(arg[3]);
                    int e = Integer.parseInt(arg[4]);
                    int f = Integer.parseInt(arg[5]);
                    transferSocket = new Socket(Inet4Address.getByAddress(new byte[]{(byte) a, (byte) b, (byte) c, (byte) d}), e * 256 + f);
                }
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
