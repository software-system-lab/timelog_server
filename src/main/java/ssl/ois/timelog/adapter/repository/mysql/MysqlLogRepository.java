package ssl.ois.timelog.adapter.repository.mysql;

import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.log.LogRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.UUID;

public class MysqlLogRepository implements LogRepository {
    @Override
    public void save(Log log) {
        try {
            Connection connection = MysqlDriverAdapter.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO `log`" +
                            "(`id`, `user_id`, `title`, `start_time`, `end_time`, `description`, `activity_type`) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, log.getLogID().toString());
            stmt.setString(2, log.getUserID().toString());
            stmt.setString(3, log.getTitle());
            stmt.setTime(4, new Time(log.getStartTime().getTime()));
            stmt.setTime(5, new Time(log.getEndTime().getTime()));
            stmt.setString(6, log.getDescription());
            stmt.setString(7, log.getActivityType());

            stmt.executeUpdate();

            MysqlDriverAdapter.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public Log getByID(UUID id) {
        return null;
    }
}
