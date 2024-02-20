package Command;

import Server.SousServeur;
import Server.State;
import Server.UserManager;

import java.io.IOException;

public class User extends Commande {
    /**
     * Create a new command
     */
    public User() {
        super("USER");
    }

    /**
     * Run the command
     * @param arg the argument of the command
     */
    @Override
    public void run(String arg, SousServeur ss) throws IOException {
        if(ss.getState() != State.AUTHORIZATION) {
            ss.send("-ERR already logged in");
            return;
        }

        if(UserManager.getUser(arg) != null){
            ss.send("+OK");
            ss.setUser(arg);
        } else {
            ss.send("-ERR no such user");
        }

    }
}
