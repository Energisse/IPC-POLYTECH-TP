import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class Controle implements Runnable {

    /**
     * Liste des utilisateurs
     */
    private static final HashMap<String, String> users = new HashMap<String, String>();

    /**
     * Initialisation des utilisateurs
     */
    static {
        users.put("anonymous", "");
        users.put("toto", "palword");
        users.put("Alban", "leMéchant");
    }

    /**
     * Socket du client
     */
    private Socket client;

    /**
     * Input stream
     */
    private BufferedInputStream in = null;

    /**
     * Output stream
     */
    private BufferedOutputStream out = null;

    /**
     * Répertoire courant
     */
    private String currentDirectory = ".\\Ressources";

    /**
     * Utilisateur courant
     */
    private String user;

    /**
     * Socket de transfert
     */
    private Object transferSocket;
    public Controle(Socket client) throws IOException {
        this.client = client;
        in = new BufferedInputStream(client.getInputStream());
        out = new BufferedOutputStream(client.getOutputStream());
    }

    /**
     * Envoi d'un message
     *
     * @param message
     * @throws IOException
     */
    private void send(String message) throws IOException {
        out.write(message.getBytes(), 0, message.length());
        out.flush();
    }

    /**
     * Liste les fichiers du répertoire courant
     *
     * @throws IOException erreur
     */
    private void list() throws IOException {
        StringBuilder response = new StringBuilder();
        System.out.println("Liste des fichiers");
        for (File file : Objects.requireNonNull(new File(currentDirectory).listFiles())) {
            if (file.isDirectory()) {
                response.append(" - d ");
            } else {
                response.append(" - f ");
            }
            response.append("\t").append(file.getName()).append("\t").append(file.getTotalSpace()).append("\n");
        }
        send(Code.succes.OK + "\n" + response.toString());
    }

    /**
     * Changement de répertoire
     * @Deprecated ca n'a jamais fonctionné avec les sous-dossiers et je n'ai pas eu le temps de le corriger car je suis etudiant et j'ai d'autres cours à suivre et des devoirs à rendre et des examens à préparer
     * @param path chemin du répertoire
     * @throws IOException erreur
     */
    private void cwd(String path) throws IOException {
        File file = new File(currentDirectory + "/" + path);
        var choosen = file.getCanonicalPath();
        var current = Paths.get("").toAbsolutePath().toString()+"\\Ressources";

        System.out.printf("Choosen : %s\n", choosen);
        System.out.printf("Current : %s\n", current);
        if (!file.exists()) {
            send(Code.erreurs.SYNTAXE_ERROR);
            return;
        }

        if (!choosen.startsWith(current)) {
            send(Code.erreurs.ERROR);
            return;
        }


        var currentPath = choosen.replace(current,"");
        System.out.println(currentPath);
        var userFolder =currentPath.replace("\\","\\\\").split("\\\\",2)[1].replace("\\","");

        System.out.printf("UserFolder : %s\n", userFolder);

        //si c'est anonymous
        if (user.equals("anonymous")) {
            //le dossier ne doit pas être un dossier privé
            if (users.containsKey(userFolder)) {
                send(Code.erreurs.USER_ERROR);
                return;
            }
        }
        //C'est un utilisateur et le dossier est doit être le sien
        else if (!user.equals(userFolder)) {
            send(Code.erreurs.USER_ERROR);
            return;
        }

        currentDirectory = choosen;
        send(Code.succes.OK + "Répertoire courant : " +currentPath );
    }

    private void dir() throws IOException {
        var choosen = new File("").getCanonicalPath().toString();
        var current = Paths.get("").toAbsolutePath().toString() + "\\Ressources";

        var currentPath = choosen.replace(current,"");
        var userFolder =currentPath.replace("\\","\\\\").split("\\\\",2)[1].replace("\\","");

        send(Code.succes.OK + " " + currentPath );
    }

    /**
     * Ouvre un port pour le transfert
     * @throws IOException
     */
    private void pasv() throws IOException {
        if(transferSocket != null) {
            if(transferSocket instanceof ServerSocket) {
                ((ServerSocket) transferSocket).close();
            } else {
                ((Socket) transferSocket).close();
            }
        }
        ServerSocket serverSocket = new ServerSocket(0);
        int port = serverSocket.getLocalPort();
        var address = serverSocket.getInetAddress().getHostAddress().replace(".", ",")+","+port/256+","+port%256;
        send(Code.succes.OK + " " + address);
        transferSocket = serverSocket;
        System.out.println("En attente de connexion");
        transferSocket = serverSocket.accept();
        System.out.println("Connexion établie");
    }

    /**
     * Ouverture d'un port pour le transfert
     * @param port
     * @throws IOException
     */
    private void port(String port) throws IOException {
        if(transferSocket != null) {
            if(transferSocket instanceof ServerSocket) {
                ((ServerSocket) transferSocket).close();
            } else {
                ((Socket) transferSocket).close();
            }
        }
        String[] ports = port.split(",");
        int a = Integer.parseInt(ports[0]);
        int b = Integer.parseInt(ports[1]);
        int c = Integer.parseInt(ports[2]);
        int d = Integer.parseInt(ports[3]);
        int e = Integer.parseInt(ports[4]);
        int f = Integer.parseInt(ports[5]);
        transferSocket = new Socket(Inet4Address.getByAddress(new byte[]{(byte) a, (byte) b, (byte) c, (byte) d}), e * 256 + f);
        send(Code.succes.OK);
    }
    @Override
    public void run() {
        try {
            System.out.println("Nouveau thread : " + Thread.currentThread().getName());

            while (!client.isClosed()) {
                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);
                String message = new String(buffer, 0, bytesRead);

                var splited = message.split(" ", 2);
                String user;

                //Login
                if (!(splited.length > 1 && splited[0].equals("USER"))) {
                    send(Code.erreurs.SYNTAXE_ERROR);
                    continue;
                }
                if (!users.containsKey(splited[1])) {
                    send(Code.erreurs.USER_ERROR);
                    continue;
                }

                user = splited[1];
                out.write(Code.succes.OK.getBytes(), 0, Code.succes.OK.length());
                out.flush();

                bytesRead = in.read(buffer);
                message = new String(buffer, 0, bytesRead);
                splited = message.split(" ", 2);

                //Password
                if (!(splited.length > 1 && splited[0].equals("PASS"))) {
                    send(Code.erreurs.SYNTAXE_ERROR);
                    continue;
                }
                if (!users.get(user).equals(splited[1])) {
                    send(Code.erreurs.PASSWORD_ERROR);
                    continue;
                }

                out.write(Code.succes.OK.getBytes(), 0, Code.succes.OK.length());
                out.flush();

                this.user = user;
                break;
            }

            while (!client.isClosed()) {
                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);
                String message = new String(buffer, 0, bytesRead);
                var splited = message.split(" ", 2);

                switch (splited[0]) {
                    case "LIST" -> list();
                    case "CWD" -> {
                        if (splited.length < 2) {
                            send(Code.erreurs.SYNTAXE_ERROR);
                            continue;
                        }
                        cwd(splited[1]);
                    }
                    case "PORT" ->  {
                        if (splited.length < 2) {
                            send(Code.erreurs.SYNTAXE_ERROR);
                            continue;
                        }
                        port(splited[1]);
                    }
                    case "DIR" -> dir();
                    case "PASV" ->  pasv();
                    case "RETR" -> send("200 OK\n");

                    default -> send(Code.erreurs.SYNTAXE_ERROR);
                }
            }


        } catch (SocketException e) {
            System.out.println("Connexion fermée" + e.getMessage());

        } catch (IOException e) {
            System.out.println("Erreur lors de la communication : " + e.getMessage());
            throw new RuntimeException(e);

        }
    }
}