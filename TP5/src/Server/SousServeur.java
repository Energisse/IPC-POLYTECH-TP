package Server;

import Command.*;
import Command.List;
import Command.User;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;


public class SousServeur implements Runnable {

    /**
     * Socket du client
     */
    private final Socket client;

    /**
     * Input stream
     */
    private BufferedInputStream in = null;

    /**
     * Output stream
     */
    private BufferedOutputStream out = null;

    /**
     * Utilisateur courant
     */
    private String user = "";

    /**
     * Timeout
     * <a href="https://datatracker.ietf.org/doc/html/rfc1939#section-3">Min timeout</a>
     */
    private static final int timeout = 10 * 60 * 1000;

    private final HashMap<String, Commande> commandes = new HashMap<>();

    private State state = State.AUTHORIZATION;

    private final MailManager mailManager = new MailManager(this);

    public SousServeur(Socket client) throws IOException {
        this.client = client;
        client.setSoTimeout(timeout);
        in = new BufferedInputStream(client.getInputStream());
        out = new BufferedOutputStream(client.getOutputStream());

        Commande cmd;

        cmd = new User();
        commandes.put(cmd.getName(), cmd);
        cmd = new Pass();
        commandes.put(cmd.getName(), cmd);

        cmd = new Stat();
        commandes.put(cmd.getName(), cmd);
        cmd = new List();
        commandes.put(cmd.getName(), cmd);
        cmd = new Top();
        commandes.put(cmd.getName(), cmd);
        cmd = new Retr();
        commandes.put(cmd.getName(), cmd);

        cmd = new Quit();
        commandes.put(cmd.getName(), cmd);

        cmd = new Dele();
        commandes.put(cmd.getName(), cmd);
        cmd = new Rset();
        commandes.put(cmd.getName(), cmd);
        cmd = new Noop();
        commandes.put(cmd.getName(), cmd);
    }

    /**
     * Envoi d'un message
     *
     * @param message le message à envoyer
     * @throws IOException si une erreur survient lors de l'envoi
     */
    public void send(String message) throws IOException {
        message += "\r\n";
        out.write(message.getBytes(), 0, message.length());
        out.flush();
    }

    /**
     * Envoi d'un message multiligne
     * @param messages les messages à envoyer
     * @throws IOException si une erreur survient lors de l'envoi
     */
    public void send(String[] messages) throws IOException {
        StringBuilder message = new StringBuilder();
        for (String s : messages) {
            message.append(s).append("\r\n");
        }
        message.append(".");
        out.write(message.toString().getBytes(), 0, message.length());
        out.flush();
    }

    @Override
    public void run() {
        try {
            System.out.println("Nouveau thread : " + Thread.currentThread().getName());

            send("+OK POP3 server ready");

            while (!client.isClosed()) {
                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);
                String message = new String(buffer, 0, bytesRead);
                var splited = message.split(" ", 2);
                String arg = "";
                if(splited.length > 1) {
                    arg = splited[1];
                }
                if(!commandes.containsKey(splited[0])){
                    send("-ERR Unknown command");
                    continue;
                }
                commandes.get(splited[0]).run(arg, this);
            }


        } catch (SocketException e) {
            System.out.println("Connexion fermée" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erreur lors de la communication : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        finally {
            if(!user.isEmpty()) UserManager.getUser(user).setLogged(false);

            try {
                in.close();
                out.close();
                client.close();
            } catch (IOException e) {
                System.out.println("Erreur lors de la fermeture des flux : " + e.getMessage());
            }
        }
    }

    public State getState() {
       return this.state;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void close() throws IOException {
        in.close();
        out.close();
        client.close();
    }

    public MailManager getMailManager(){
        return this.mailManager;
    }
}