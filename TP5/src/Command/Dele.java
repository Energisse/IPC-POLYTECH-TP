package Command;

import Server.SousServeur;
import Server.State;

/**
 * The Dele command
 * <a href="https://datatracker.ietf.org/doc/html/rfc1939#page-8">RFC 1939</a>
 */
public class Dele extends Commande{
    public Dele() {
        super("DELE");
    }

    @Override
    public void run(String arg, SousServeur ss) throws Exception {
        if(ss.getState() != State.TRANSACTION) {
            ss.send("-ERR must be logged in to run this command");
            return;
        }

        int index;
        try {
            index = Integer.parseInt(arg);
        } catch (NumberFormatException ignored) {
            ss.send("-ERR no such message");
            return;
        }

        if(ss.getMailManager().getMail(index) == null) {
            ss.send("-ERR no such message");
            return;
        }

        if(ss.getMailManager().isDeletedMail(index)) {
            ss.send("-ERR message " + index + " already deleted");
            return;
        }

        ss.send("+OK message " + index + " deleted");
        ss.getMailManager().deleteMail(index);
    }
}
