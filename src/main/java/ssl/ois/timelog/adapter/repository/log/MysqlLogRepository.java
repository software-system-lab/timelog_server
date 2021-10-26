package ssl.ois.timelog.adapter.repository.log;

import org.springframework.beans.factory.annotation.Autowired;
import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.common.SqlDateTimeConverter;
import ssl.ois.timelog.exception.DatabaseErrorException;
import ssl.ois.timelog.exception.log.GetLogErrorException;
import ssl.ois.timelog.exception.log.RemoveLogException;
import ssl.ois.timelog.exception.log.SaveLogErrorException;
import ssl.ois.timelog.model.log.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MysqlLogRepository implements LogRepository{

    @Autowired
    private MysqlDriverAdapter mysqlDriverAdapter;

    public void addLog(Log log) throws SaveLogErrorException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO `log`" +
                        "(`id`, `unit_id`, `title`, `start_time`, `end_time`, `description`, `activity_type_id`, `create_by`) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

                stmt.setString(1, log.getId());
                stmt.setString(2, log.getUnitId());
                stmt.setString(3, log.getTitle());
                stmt.setString(4, SqlDateTimeConverter.toString(log.getStartTime()));
                stmt.setString(5, SqlDateTimeConverter.toString(log.getEndTime()));
                stmt.setString(6, log.getDescription());
                stmt.setString(7, log.getActivityTypeId());
                stmt.setString(8, log.getCreateBy());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SaveLogErrorException(log.getTitle());
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
    }

    public void updateLog(Log log) throws SaveLogErrorException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE `log`" +
                        "SET `title`= ?, `start_time`= ?, `end_time`= ?, `activity_type_id`= ?" +
                        "WHERE `id` = ?")) {

                stmt.setString(1, log.getTitle());
                stmt.setString(2, SqlDateTimeConverter.toString(log.getStartTime()));
                stmt.setString(3, SqlDateTimeConverter.toString(log.getEndTime()));
                stmt.setString(4, log.getActivityTypeId());
                stmt.setString(5, log.getId());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SaveLogErrorException(log.getTitle());
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
    }

    public void removeLog(String id) throws RemoveLogException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM `log` WHERE `id` = ?")) {
                stmt.setString(1, id);

                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RemoveLogException(id);
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
    }

    public Log findById(String id) throws GetLogErrorException, ParseException {
        Connection connection = null;
        Log log = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT `log`.* ,`activity_type`.`activity_name`" +
                        "FROM `log`, `activity_type`" +
                        "WHERE `log`.`activity_type_id` = `activity_type`.`id` " +
                        "AND `log`.`id` = ? ")) {

                stmt.setString(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    rs.next();

                    String logID = rs.getString("id");
                    String unitId = rs.getString("unit_id");
                    String title = rs.getString("title");
                    Date startTime = SqlDateTimeConverter.toDate(rs.getString("start_time"));
                    Date endTime = SqlDateTimeConverter.toDate(rs.getString("end_time"));
                    String description = rs.getString("description");
                    String activityTypeId = rs.getString("activity_type_id");
                    String activityTypeName = rs.getString("activity_name");
                    String createBy = rs.getString("create_by");

                    log = new Log(logID, unitId, title, startTime, endTime,
                            description, activityTypeId, activityTypeName, createBy);
                }
            }
        } catch (SQLException e) {
            throw new GetLogErrorException(id);
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
        return log;
    }

    public List<Log> findByPeriod(String unitId, String startDate, String endDate) throws ParseException, DatabaseErrorException{
        Connection connection = null;
        List<Log> logList = new ArrayList<>();
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT `log`.* ,`activity_type`.`activity_name`" +
                        "FROM `log`, `activity_type`" +
                        "WHERE `log`.`activity_type_id` = `activity_type`.`id` " +
                        "AND `log`.`unit_id` = ?  " +
                        "AND `log`.`start_time` >= ? " +
                        "AND `log`.`end_time` < ? ")) {

                stmt.setString(1, unitId);
                stmt.setString(2, startDate);
                stmt.setString(3, endDate);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String logID = rs.getString("id");
                        String uid = rs.getString("unit_id");
                        String title = rs.getString("title");
                        Date startTime = SqlDateTimeConverter.toDate(rs.getString("start_time"));
                        Date endTime = SqlDateTimeConverter.toDate(rs.getString("end_time"));
                        String description = rs.getString("description");
                        String activityTypeId = rs.getString("activity_type_id");
                        String activityTypeName = rs.getString("activity_name");
                        String createBy = rs.getString("create_by");
                        Log log = new Log(logID, uid, title, startTime,
                                endTime, description, activityTypeId, activityTypeName, createBy);
                        logList.add(log);
                    }
                }
            }

        } catch (SQLException e) {
            throw new DatabaseErrorException();
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
        return logList;
    }
}
