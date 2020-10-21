package ssl.ois.timelog.adapter.repository.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.common.SqlDateTimeConverter;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.log.GetLogErrorException;
import ssl.ois.timelog.service.exception.log.SaveLogErrorException;
import ssl.ois.timelog.service.repository.log.LogRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.io.*;

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
    public void update(Log log, String targetID) throws GetLogErrorException, SaveLogErrorException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement("UPDATE `log`"
                    + "SET `id`= ?, `title`= ?, `start_time`= ?, `end_time`= ?, `activity_type`= ? "
                    + "WHERE log.id = ? AND log.user_id = ?")) {

                stmt.setString(1, log.getID().toString());
                stmt.setString(2, log.getTitle());
                stmt.setString(3, SqlDateTimeConverter.toString(log.getStartTime()));
                stmt.setString(4, SqlDateTimeConverter.toString(log.getEndTime()));
                stmt.setString(5, log.getActivityTypeName());
                stmt.setString(6, targetID);
                stmt.setString(7, log.getUserID().toString());

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

                try (ResultSet rs = stmt.executeQuery()) {
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

            }
        } catch (SQLException e) {
            throw new GetLogErrorException(id);
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
        return log;
    }

    @Override
    public Boolean removeByID(String logID) throws GetLogErrorException, SaveLogErrorException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();
            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM `log` WHERE `id` = ?")) {
                stmt.setString(1, logID);
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            return false;
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
    }

    @Override
    public List<Log> findByPeriod(String userID, String startDate, String endDate) throws DatabaseErrorException {
        Connection connection = null;
        List<Log> logList = new ArrayList<>();

        try {
            connection = this.mysqlDriverAdapter.getConnection();
            try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `log` " +
                    "WHERE `user_id` = ? AND `start_time` >= ? AND `end_time` < ? ")) {
                        stmt.setString(1, userID);
                        stmt.setString(2, startDate);
                        stmt.setString(3, endDate);
                        
                        try (ResultSet result = stmt.executeQuery()) {
                            while (result.next()) {
                                UUID logID = UUID.fromString(result.getString("id"));
                                UUID uid = UUID.fromString(result.getString("user_id"));
                                String title = result.getString("title");
                                String startTime = result.getString("start_time").replace("-", "/");
                                String endTime = result.getString("end_time").replace("-", "/");
                                String description = result.getString("description");
                                String activityType = result.getString("activity_type");
                
                
                                Log log = new Log(logID, uid, title, startTime.substring(0, startTime.lastIndexOf(':')),
                                        endTime.substring(0, endTime.lastIndexOf(':')), description, activityType);
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

    @Override
    public List<Log> findByPeriodandNotPrivate(String userID, String startDate, String endDate) throws DatabaseErrorException {
        Connection connection = null;
        List<Log> logList = new ArrayList<>();

        try {
            connection = this.mysqlDriverAdapter.getConnection();
            try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `log` as a," +
                "`activity_user_mapper` as b " +
                "WHERE a.user_id = ? AND a.start_time >= ? AND a.end_time < ?" +
                "AND a.user_id = b.user_id AND a.activity_type = b.activity_type_name AND b.is_private = 0 ")) {
                stmt.setString(1, userID);
                stmt.setString(2, startDate);
                stmt.setString(3, endDate);

                try (ResultSet result = stmt.executeQuery()) {
                    while (result.next()) {
                        UUID logID = UUID.fromString(result.getString("id"));
                        UUID uid = UUID.fromString(result.getString("user_id"));
                        String title = result.getString("title");
                        String startTime = result.getString("start_time").replace("-", "/");
                        String endTime = result.getString("end_time").replace("-", "/");
                        String description = result.getString("description");
                        String activityType = result.getString("activity_type");

                        Log log = new Log(logID, uid, title, startTime.substring(0, startTime.lastIndexOf(':')),
                                endTime.substring(0, endTime.lastIndexOf(':')), description, activityType);
                        logList.add(log);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException();
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
        return logList;
    }
}
