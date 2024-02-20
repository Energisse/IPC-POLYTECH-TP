package Command;

import Server.Mail;
import Server.SousServeur;
import Server.State;

/**
 * The rset command
 * <a href="https://datatracker.ietf.org/doc/html/rfc1939#page-9">RFC 1939</a>
 */
public class Rset extends Commande{
    public Rset() {
        super("RSET");
    }

    @Override
    public void run(String arg, SousServeur ss) throws Exception {
        if(ss.getState() != State.TRANSACTION) {
            ss.send("-ERR must be logged in to run this command");
            return;
        }

        ss.getMailManager().resetDeletedMails();

        Mail[] mails = ss.getMailManager().getMails();

        long size = 0;
        for (Mail mail : mails) {
            size += mail.file().length();
        }

        ss.send("+OK maildrop has " + mails.length + " messages (" + size + " octets)");
    }
}
