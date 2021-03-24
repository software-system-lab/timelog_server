package ssl.ois.timelog.service.team;

import java.util.UUID;

public class Person {
    private String username;
    private UUID userID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public Person (){}
    public Person(String username, UUID userID){
        this.userID = userID;
        this.username = username;
    }
}
