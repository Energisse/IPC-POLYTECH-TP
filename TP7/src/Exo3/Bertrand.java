package Exo3;

import Exo1.Calcul;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Bertrand {

    public static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            /*ServerSocket server = new ServerSocket(PORT);
            System.out.println("Serveur en attente de connexion...");

            Socket socket = server.accept();
            InputStream in =  socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            System.out.println("Client connecté");

            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            String message = new String(buffer, 0, bytesRead);

            String replace = message.replace("[","").replace("]","").replace(" ","");
            List<String> key = new ArrayList<String>(Arrays.asList(replace.split(",")));

            System.out.println("Clé reçue : " + key.get(0) + " " + key.get(1));

            bytesRead = in.read(buffer);
            String messageLaurent = new String(buffer, 0, bytesRead);
            System.out.println("Message reçu : " + messageLaurent);

            long messageLaurentDecoded = RSA.decode(Long.parseLong(messageLaurent), Long.parseLong(key.get(0)), Long.parseLong(key.get(1)));
            System.out.println("Message décodé : " + messageLaurentDecoded);

            socket.close();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
