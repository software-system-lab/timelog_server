package ssl.ois.timelog.service.repository;

import ssl.ois.timelog.model.user.User;

import java.util.UUID;

public interface UserRepository {
    public void save(User user);
    public User findByUserID(String userID);
}
