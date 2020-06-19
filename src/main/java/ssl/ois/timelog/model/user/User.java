package ssl.ois.timelog.model.user;

import java.util.UUID;

public class User {

    private UUID id;


    public User(UUID id) {
        this.id = id;
    }

    public UUID getID() {
        return id;
    }
}
