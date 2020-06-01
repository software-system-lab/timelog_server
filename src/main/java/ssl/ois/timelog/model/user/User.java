package ssl.ois.timelog.model.user;

import java.util.Map;
import java.util.UUID;

public class User {
    private UUID userID;
    private String name;
    private Map<String, ActivityType> activities;

    public User(String name) {
        this.userID = UUID.randomUUID();
        this.name = name;
    }

    public void newActivity(String name) {
        this.activities.put(name, new ActivityType(name));
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
