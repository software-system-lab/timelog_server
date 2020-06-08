package ssl.ois.timelog.model.user;

import java.util.UUID;

public class User {
    private UUID userID;
    private String name;

    public User(UUID userID, String name) {
        this.userID = userID;
        this.name = name;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
