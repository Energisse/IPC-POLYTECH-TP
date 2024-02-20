package Command;

import Server.SousServeur;
import Server.State;
import Server.User;
import Server.UserManager;

import java.io.IOException;

public class Pass extends Commande {
    /**
     * Create a new command
     */
    public Pass() {
        super("PASS");
    }

    /**
     * Run the command
     * @param password the argument of the command
     */
    @Override
    public synchronized void run(String password, SousServeur ss) throws IOException {
        if(ss.getState() != State.AUTHORIZATION) {
            ss.send("-ERR already logged in");
            return;
        }
        if(ss.getUser().isEmpty()){
            ss.send("-ERR no user given");
            return;
        }

        User user = UserManager.getUser(ss.getUser());

        if(user.getPassword().equals(password)){
            if(user.isLogged()){
                ss.send("-ERR unable to lock maildrop");
                return;
            }
            user.setLogged(true);
            ss.send("+OK");
            ss.setState(State.TRANSACTION);
        } else {
            ss.send("-ERR invalid password");
        }
    }
}
