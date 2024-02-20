package Command;

import Server.SousServeur;

public abstract class Commande {

    /**
     * The name of the command
     */
    private String name;

    /**
     * Create a new command
     * @param name the name of the command
     */
    public Commande(String name) {
        this.name = name;
    }

    /**
     * Run the command
     * @param arg the argument of the command
     */
    public abstract void run(String arg, SousServeur ss) throws Exception;

    /**
     * Get the name of the command
     * @return the name of the command
     */
    public String getName() {
        return name;
    }

}
