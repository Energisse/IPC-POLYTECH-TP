package Command;

import Server.Mail;
import Server.SousServeur;
import Server.State;

import java.io.IOException;
import java.nio.file.Files;

/**
 * The Top command
 * <a href="https://datatracker.ietf.org/doc/html/rfc1939#page-11">RFC 1939</a>
 */
public class Top extends Commande {
    /**
     * Create a new command
     */
    public Top() {
        super("TOP");
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
        int lines;

        try {
            String[] splited = arg.split(" ",2);
            if(splited.length < 2){
                ss.send("-ERR missing msg or line number");
                return;
            }
            index = Integer.parseInt(splited[0])-1;
            lines = Integer.parseInt(splited[1]);
            if(lines < 0){
                ss.send("-ERR line number cannot be negative");
                return;
            }
        } catch (NumberFormatException e) {
            ss.send("-ERR no such message");
            return;
        }

        Mail mails =  ss.getMailManager().getMail(index);

        if (mails == null) {
            ss.send("-ERR no such message");
            return;
        }

        if(ss.getMailManager().isDeletedMail(index)) {
            ss.send("-ERR message " + index + " already deleted");
            return;
        }

        String message = Files.readString(mails.file().toPath());
        String[] split = message.split("\n|\r\n", lines+1); // Split by line

        lines = Math.min(split.length,lines);

        String[] messages = new String[lines+1];
        System.arraycopy(split, 0, messages, 1, lines);

        messages[0] = "+OK " + message.getBytes().length + " octets";
        ss.send(messages);
    }
}
