package Server;

public class User {
    /**
     * The name of the user
     */
    private final String name;

    /**
     * The password of the user
     */
    private final String password;

    /**
     * The state of the user
     */
    private boolean isLogged = false;

    /**
     * Create a new user
     * @param name the name of the user
     * @param password the password of the user
     */
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Get the name of the user
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Get the password of the user
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the state of the user
     * @return the state of the user
     */
    public boolean isLogged() {
        return isLogged;
    }

    /**
     * Set the state of the user
     * @param isLogged the state of the user
     */
    public void setLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }
}
