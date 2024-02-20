package Command;

import Server.SousServeur;
import Server.State;

import java.io.IOException;

/**
 * Quit command
 * <a href="https://datatracker.ietf.org/doc/html/rfc1939#page-5">RFC 1939</a>
 */
public class Quit extends Commande{
    public Quit() {
        super("QUIT");
    }

    @Override
    public void run(String arg, SousServeur ss) throws IOException {
        //Go in update state if in transaction state
        //https://datatracker.ietf.org/doc/html/rfc1939#section-6
        if(ss.getState() == State.TRANSACTION) {
            ss.setState(State.UPDATE);
        }

        ss.getMailManager().deleteMails();

        ss.send("+OK");
        ss.close();
    }
}
