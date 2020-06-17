package ssl.ois.timelog.adapter.repository.mysql;

import org.springframework.beans.factory.annotation.Autowired;

import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.repository.UserRepository;

public class MysqlUserRepository implements UserRepository {
    @Autowired
    private MysqlDriverAdapter mysqlDriverAdapter;

    @Override
    public void save(User user) {
        // TODO Auto-generated method stub

    }

    @Override
    public User findByUserID(String userID) {
        // TODO Auto-generated method stub
        return null;
    }
}