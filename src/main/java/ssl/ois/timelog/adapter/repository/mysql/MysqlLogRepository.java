package ssl.ois.timelog.adapter.repository.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.common.SqlDateTimeConverter;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.exception.log.GetLogErrorException;
import ssl.ois.timelog.service.exception.log.SaveLogErrorException;
import ssl.ois.timelog.service.repository.log.LogRepository;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MysqlLogRepository implements LogRepository {
    @Autowired
    private MysqlDriverAdapter mysqlDriverAdapter;

    @Override
    public void save(Log log) throws SaveLogErrorException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO `log`"
                    + "(`id`, `user_id`, `title`, `start_time`, `end_time`, `description`, `activity_type`) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                stmt.setString(1, log.getID().toString());
                stmt.setString(2, log.getUserID().toString());
                stmt.setString(3, log.getTitle());
                stmt.setString(4, SqlDateTimeConverter.toString(log.getStartTime()));
                stmt.setString(5, SqlDateTimeConverter.toString(log.getEndTime()));
                stmt.setString(6, log.getDescription());
                stmt.setString(7, log.getActivityTypeName());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SaveLogErrorException(log.getTitle());
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
    }

    @Override
    public Log findByID(String id) throws GetLogErrorException {
        Connection connection = null;
        Log log = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `log` WHERE `id` = ?")) {

                stmt.setString(1, id);

                ResultSet rs = stmt.executeQuery();

                rs.next();

                UUID logID = UUID.fromString(rs.getString("id"));
                UUID userID = UUID.fromString(rs.getString("user_id"));
                String title = rs.getString("title");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String description = rs.getString("description");
                String activityTypeName = rs.getString("activity_type");
                log = new Log(logID, userID, title, startTime, endTime, description, activityTypeName);
            }
        } catch (SQLException e) {
            throw new GetLogErrorException(id);
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
        return log;
    }

    @Override
    public List<Log> getByUserID(String userID) throws GetLogErrorException {
        return new ArrayList<>();
    }

    @Override
    public Boolean removeByID(String logID) throws GetLogErrorException, SaveLogErrorException {
        return false;
    }
}
