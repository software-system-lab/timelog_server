package ssl.ois.timelog.adapter.repository.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.common.SqlDateTimeConverter;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.log.LogRepository;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class MysqlLogRepository implements LogRepository {
    @Autowired
    private MysqlDriverAdapter mysqlDriverAdapter;

    @Override
    public void save(Log log) throws ConnectException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO `log`" +
                            "(`id`, `user_id`, `title`, `start_time`, `end_time`, `description`, `activity_type`) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                stmt.setString(1, log.getLogID().toString());
                stmt.setString(2, log.getUserID().toString());
                stmt.setString(3, log.getTitle());
                stmt.setString(4, SqlDateTimeConverter.convert(log.getStartTime()));
                stmt.setString(5, SqlDateTimeConverter.convert(log.getEndTime()));
                stmt.setString(6, log.getDescription());
                stmt.setString(7, log.getActivityTypeName());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ConnectException(e.getMessage());
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }


    }

    @Override
    public Log getByID(UUID id) {
        return null;
    }
}
