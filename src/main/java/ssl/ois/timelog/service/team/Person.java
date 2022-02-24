package ssl.ois.timelog.service.team;

import java.util.UUID;

public class Person {
    private String username;
    private String displayName;
    private UUID userID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public Person () {}

    public Person(String username, String displayName, UUID userID) {
        this.userID = userID;
        this.displayName = displayName;
        this.username = username;
    }
}
