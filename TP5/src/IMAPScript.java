import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class IMAPScript {

    private static String read(BufferedInputStream in,int iterator) throws IOException {
        String result = "";
        byte[] buff = new byte[2048];
        String[] lines;
        do {
            int l = in.read(buff);
            result += new String(buff,0,l);
            lines = result.split("\n");
        } while (!lines[lines.length-1].startsWith(String.valueOf(iterator)));

        return result;
    }

    public static void main(String[] args) {
        String server = "localhost";
        int port = 143;
        String user = "toto@etu.univ-lyon1.fr";
        String password = "toto";
        int iterator = 1;
        boolean isMailUnread = false;
        try (Socket socket = new Socket(server, port)) {
            BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());

            //lecture de la bannière
            byte[] buffer = new byte[1024]; // adapter la taille du buffer en fonction du nombre de mail à lire
            int bytesRead = in.read(buffer);
            String message = new String(buffer, 0, bytesRead);
            System.out.println("Message reçu : " + message);


            //envoi de la commande LOGIN
            String login = iterator + " LOGIN " + user + " " + password + "\r\n";
            out.write(login.getBytes(), 0, login.length());
            out.flush();

            //lecture de la réponse
            message = read(in,iterator);
            System.out.println("Message reçu : " + message);
            iterator++;

            //envoi de la commande SELECT INBOX
            String select = iterator + " SELECT INBOX\r\n";
            out.write(select.getBytes(), 0, select.length());
            out.flush();

            //lecture de la réponse
            message = read(in,iterator);
            System.out.println("Message reçu : " + message);
            iterator++;

            //envoi de la commande SEARCH UNSEEN
            String search = iterator + " SEARCH UNSEEN\r\n";
            out.write(search.getBytes(), 0, search.length());
            out.flush();

            //lecture de la réponse
            message = read(in,iterator);
            System.out.println("Message reçu : " + message);
            iterator++;

            //split de la réponse par ligne
            String[] lines = message.split("\\r?\\n");
            System.out.println(message);
            for (String line : lines) {
                if (line.startsWith("* SEARCH") && line.length() > "* SEARCH".length()) {
                    isMailUnread = true;

                    //extraction des numéro de séquence qui sont les numéros des mails non lus
                    String subString = line.substring("* SEARCH ".length());
                    String[] sequences = subString.split(" ");
                    for (String sequence : sequences) {
                        System.out.println("Mail non lu : " + sequence);
                    }
                    //pour chaque numéro de séquence, on envoie une commande FETCH pour récupérer le mail
                    for (String sequence : sequences) {
                        //envoi de la commande FETCH
                        String fetch = iterator + " FETCH " + sequence + " BODY[]\r\n";
                        out.write(fetch.getBytes(), 0, fetch.length());
                        out.flush();

                        //lecture de la réponse
                        message = read(in,iterator);
                        System.out.println("Message reçu : " + message);
                        iterator++;

                        //suppression du mail
                        String delete = iterator + " STORE " + sequence + " +FLAGS (\\Deleted)\r\n";
                        out.write(delete.getBytes(), 0, delete.length());
                        out.flush();

                        message = read(in,iterator);
                        System.out.println("Message reçu : " + message);
                        iterator++;
                    }
                    break;
                }
            }

            if (!isMailUnread) {
                System.out.println("Aucun mail non lu");
            }
            //envoie de la commande CLOSE
            String close = iterator + " CLOSE\r\n";
            out.write(close.getBytes(), 0, close.length());
            out.flush();

            //lecture de la réponse
            message = read(in,iterator);
            System.out.println("Message reçu : " + message);

            //envoi de la commande LOGOUT
            String logout = iterator + " LOGOUT\r\n";
            out.write(logout.getBytes(), 0, logout.length());
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

