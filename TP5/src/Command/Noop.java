package Command;

import Server.SousServeur;
import Server.State;

import java.io.IOException;

/**
 * The noop command
 * <a href="https://datatracker.ietf.org/doc/html/rfc1939#page-9">RFC 1939</a>
 */
public class Noop extends Commande {
    /**
     * Create a new command
     */
    public Noop() {
        super("NOOP");
    }

    /**
     * Run the command
     * @param password the argument of the command
     */
    @Override
    public synchronized void run(String password, SousServeur ss) throws IOException {
        if(ss.getState() != State.TRANSACTION) {
            ss.send("-ERR must be logged in to run this command");
            return;
        }

        ss.send("+OK");
    }
}
