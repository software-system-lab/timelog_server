package ssl.ois.timelog.adapter.repository.memory;

import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.user.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryUserRepository implements UserRepository {
    private Map<UUID, User> users;

    public MemoryUserRepository() {
        this.users = new HashMap<>();
    }

    @Override
    public void save(User user) {
        this.users.put(user.getUserID(), user);
    }

    @Override
    public User getByUserID(UUID userID) {
        return this.users.get(userID);
    }
}
