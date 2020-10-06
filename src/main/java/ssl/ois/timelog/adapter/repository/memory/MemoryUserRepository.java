package ssl.ois.timelog.adapter.repository.memory;

import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.repository.user.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserRepository implements UserRepository {
    private Map<String, User> users;

    public MemoryUserRepository() {
        this.users = new HashMap<>();
    }

    @Override
    public void save(User user) {
        this.users.put(user.getID().toString(), user);
    }

    @Override
    public void addActivityType(User user) {
        this.save(user);
    }

    @Override
    public void updateActivityType(User user) {
        this.save(user);
    }

    @Override
    public void deleteActivityType(User user)  {
        this.save(user);
    }

    @Override
    public User findByUserID(String userID) {
        return this.users.get(userID);
    }
}
