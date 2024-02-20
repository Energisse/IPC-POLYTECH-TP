package Command;

import Server.Mail;
import Server.SousServeur;
import Server.State;

import java.io.IOException;
import java.nio.file.Files;

/**
 * The Retr command
 * <a href="https://datatracker.ietf.org/doc/html/rfc1939#page-8">RFC 1939</a>
 */
public class Retr extends Commande {
    /**
     * Create a new command
     */
    public Retr() {
        super("RETR");
    }

    /**
     * Run the command
     * @param arg the argument of the command
     */
    @Override
    public void run(String arg, SousServeur ss) throws IOException {
        if(ss.getState() != State.TRANSACTION) {
            ss.send("-ERR must be logged in to run this command");
            return;
        }

        int index;

        try {
            index = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            ss.send("-ERR no such message");
            return;
        }

        if(ss.getMailManager().isDeletedMail(index)) {
            ss.send("-ERR message " + index + " already deleted");
            return;
        }

        Mail mail = ss.getMailManager().getMail(index);

        if(mail == null) {
            ss.send("-ERR no such message");
            return;
        }

        String[] message = new String[2];
        message[0] = "+OK " + mail.file().length() + " octets";
        message[1] = new String(Files.readAllBytes(mail.file().toPath()));
        ss.send(message);
    }
}
