package ssl.ois.timelog.adapter.repository.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.repository.user.UserRepository;

public class MysqlUserRepository implements UserRepository {
    @Autowired
    private MysqlDriverAdapter mysqlDriverAdapter;

    @Override
    public void save(User user) throws DatabaseErrorException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO `user`(`id`) VALUES (?)"
            )) {
                stmt.setString(1, user.getID().toString());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
    }

    @Override
    public User findByUserID(String userID) throws DatabaseErrorException{
        Connection connection = null;
        User user = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM `user` WHERE `id` = ?"
            )) {
                stmt.setString(1, userID);

                ResultSet rs = stmt.executeQuery();

                rs.next();

                user = new User(UUID.fromString(rs.getString("id")));
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
        return user;
    }
}