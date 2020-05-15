package ssl.ois.timelog.adapter.repository.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.common.SqlDateTimeConverter;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.log.LogRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Component
public class MysqlLogRepository implements LogRepository {
    @Autowired
    private MysqlDriverAdapter mysqlDriverAdapter;

    @Override
    public void save(Log log) {
        try {
            Connection connection = this.mysqlDriverAdapter.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO `log`" +
                            "(`id`, `user_id`, `title`, `start_time`, `end_time`, `description`, `activity_type`) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)");


            stmt.setString(1, log.getLogID().toString());
            stmt.setString(2, log.getUserID().toString());
            stmt.setString(3, log.getTitle());
            stmt.setString(4, SqlDateTimeConverter.convert(log.getStartTime()));
            stmt.setString(5, SqlDateTimeConverter.convert(log.getEndTime()));
            stmt.setString(6, log.getDescription());
            stmt.setString(7, log.getActivityType());

            stmt.executeUpdate();

            this.mysqlDriverAdapter.closeConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public Log getByID(UUID id) {
        return null;
    }
}
