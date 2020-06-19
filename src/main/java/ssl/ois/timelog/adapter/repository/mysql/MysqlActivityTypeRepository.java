package ssl.ois.timelog.adapter.repository.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.repository.activity.ActivityTypeRepository;

public class MysqlActivityTypeRepository implements ActivityTypeRepository {

    @Autowired
    private MysqlDriverAdapter mysqlDriverAdapter;

    @Override
    public void addActivityType(String userID, ActivityType activityType)
            throws DatabaseErrorException, DuplicateActivityTypeException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT COUNT(*) FROM activity_user_mapper WHERE user_id = ? AND activity_type_name = ?"
            )) {
                stmt.setString(1, userID);
                stmt.setString(2, activityType.getName());

                ResultSet rs = stmt.executeQuery();

                rs.next();
                int count = rs.getInt(1);
                if(count > 0) {
                    throw new DuplicateActivityTypeException();
                }
            }

            try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT IGNORE INTO `activity_type` (`name`) VALUES (?)"
            )) {
                stmt.setString(1, activityType.getName());

                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO `activity_user_mapper` " + 
                "(`activity_type_name`, `user_id`, `is_enable`, `is_private`) " +
                "VALUES (?, ?, ?, ?)"
            )) {
                stmt.setString(1, activityType.getName());
                stmt.setString(2, userID);
                stmt.setInt(3, activityType.isEnable() ? 1 : 0);
                stmt.setInt(4, activityType.isPrivate() ? 1 : 0);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
    }

    @Override
    public List<ActivityType> getActivityTypeList(String userID) throws DatabaseErrorException {
        Connection connection = null;
        List<ActivityType> activityTypeList = new ArrayList<>();
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM `activity_user_mapper` WHERE activity_user_mapper.user_id = ?"
            )) {
                stmt.setString(1, userID);

                ResultSet rs = stmt.executeQuery();

                while(rs.next()) {
                    String activityTypeName = rs.getString("activity_type_name");
                    boolean isEnable = rs.getInt("is_enable") == 1;
                    boolean isPrivate = rs.getInt("is_private") == 1;

                    ActivityType activityType = new ActivityType(activityTypeName, isEnable, isPrivate);
                    activityTypeList.add(activityType);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
        
        return activityTypeList;
    }

    @Override
    public void updateActivityType(String userID, String targetActivityTypeName, ActivityType newActivityType)
        throws ActivityTypeNotExistException, DatabaseErrorException {
            
        Connection connection = null;
        
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT COUNT(*) FROM `activity_user_mapper` " +
                "WHERE activity_user_mapper.user_id = ? " +
                "AND activity_user_mapper.activity_type_name = ?"
            )) {
                stmt.setString(1, userID);
                stmt.setString(2, targetActivityTypeName);

                ResultSet rs = stmt.executeQuery();

                rs.next();
                int count = rs.getInt(1);
                if(count == 0) {
                    throw new ActivityTypeNotExistException(targetActivityTypeName);
                }
            }

            try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT IGNORE INTO `activity_type` (`name`) VALUES (?)"
            )) {
                stmt.setString(1, newActivityType.getName());

                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE `activity_user_mapper` " + 
                "SET `activity_type_name`= ?, `is_enable`= ?,`is_private`= ? " + 
                "WHERE activity_user_mapper.activity_type_name = ?" +
                "AND activity_user_mapper.user_id = ?"
            )) {
                stmt.setString(1, newActivityType.getName());
                stmt.setInt(2, newActivityType.isEnable() ? 1 : 0);
                stmt.setInt(3, newActivityType.isPrivate() ? 1 : 0);
                stmt.setString(4, targetActivityTypeName);
                stmt.setString(5, userID);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
    }

    @Override
    public void removeActivityType(String userID, String targetActivityTypeName) throws ActivityTypeNotExistException,
            DatabaseErrorException {
        Connection connection = null;

        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try(PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM `activity_user_mapper` " +
                "WHERE activity_user_mapper.user_id = ? " +
                "AND activity_user_mapper.activity_type_name = ?"
            )) {
                stmt.setString(1, userID);
                stmt.setString(2, targetActivityTypeName);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
    }
    
}