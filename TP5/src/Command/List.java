package Command;

import Server.Mail;
import Server.SousServeur;
import Server.State;

import java.io.IOException;

/**
 * List command
 * <a href="https://datatracker.ietf.org/doc/html/rfc1939#page-6">RFC 1939</a>
 */
public class List extends Commande {
    /**
     * Create a new command
     */
    public List() {
        super("LIST");
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


        if(!arg.isEmpty()){
            try {
                int index = Integer.parseInt(arg);
                if(ss.getMailManager().isDeletedMail(index)){
                    ss.send("-ERR message " + index + " is deleted");
                    return;
                }

                Mail mail = ss.getMailManager().getMail(index);

                if(mail == null){
                    ss.send("-ERR no such message");
                    return;
                }

                ss.send("+OK " + index + " " + mail.file().length());

            } catch (NumberFormatException ignored) {
                ss.send("-ERR no such message");
            }
        }
        else {
            Mail[] mails = ss.getMailManager().getMails();
            String[] message = new String[mails.length+1];
            long size = 0;
            for(int i = 0; i < mails.length; i++){
                message[i+1] = mails[i].index()+1 + " " + mails[i].file().length();
                size += mails[i].file().length();
            }
            message[0] = "+OK " + mails.length + " messages (" + size  + " octets)";

            ss.send(message);
        }
    }
}
