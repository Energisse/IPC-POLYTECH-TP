package Command;

import java.io.IOException;

import Server.Mail;
import Server.SousServeur;
import Server.State;

/**
 * Stat command
 * <a href="https://datatracker.ietf.org/doc/html/rfc1939#page-6">RFC 1939</a>
 */
public class Stat extends Commande {
    /**
     * Create a new command
     */
    public Stat() {
        super("STAT");
    }

    /**
     * Run the command
     *
     * @param arg the argument of the command
     */
    @Override
    public void run(String arg, SousServeur ss) throws IOException {
        if (ss.getState() != State.TRANSACTION) {
            ss.send("-ERR must be logged in to run this command");
            return;
        }

        Mail[] mails = ss.getMailManager().getMails();

        long size = 0;

        for (Mail mail : mails) {
            size += mail.file().length();
        }

        ss.send("+OK " + mails.length + " " + size);
    }
}
