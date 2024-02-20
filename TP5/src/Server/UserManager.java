package Server;

import java.util.HashMap;

public class UserManager {
    /**
     * The list of users
     */
    private static final HashMap<String, User> users = new HashMap<>();

    static {
        users.put("toto", new User("toto", "toto"));
        users.put("theo", new User("theo", "pierron"));
    }

    private UserManager() {
    }

    /**
     * Get the user with the given name
     *
     * @param name the name of the user
     * @return the user with the given name
     */
    public static User getUser(String name) {
        return users.get(name);
    }
}
