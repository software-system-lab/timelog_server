package ssl.ois.timelog.adapter.repository.mysql;

import org.springframework.beans.factory.annotation.Autowired;

import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.service.repository.UserRepository;
import ssl.ois.timelog.service.user.dto.UserDTO;

public class MysqlUserRepository implements UserRepository {
    @Autowired
    private MysqlDriverAdapter mysqlDriverAdapter;

    @Override
    public void save(UserDTO userDTO) {
        // TODO Auto-generated method stub

    }

    @Override
    public UserDTO findByUserID(String userID) {
        // TODO Auto-generated method stub
        return null;
    }
}