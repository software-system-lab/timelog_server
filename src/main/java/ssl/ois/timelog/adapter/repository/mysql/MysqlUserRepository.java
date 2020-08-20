package ssl.ois.timelog.adapter.repository.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.repository.user.UserRepository;

public class MysqlUserRepository implements UserRepository {
    @Autowired
    private MysqlDriverAdapter mysqlDriverAdapter;

    @Override
    public void save(User user) throws DatabaseErrorException, DuplicateActivityTypeException,
            ActivityTypeNotExistException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT IGNORE INTO `user`(`id`) VALUES (?)"
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
    public void addActivityType(User user) throws DatabaseErrorException, DuplicateActivityTypeException{
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            if(this.isExistInMapper(connection, user.getID().toString(), user.getOperatedActivityType().getName())) {
                throw new DuplicateActivityTypeException();
            }
            this.createInActivityTypeTable(connection, user.getOperatedActivityType());
            this.createInActivityTypeUserMapper(connection, user.getID().toString(), user.getOperatedActivityType());

        } catch (SQLException e) {
            throw new DatabaseErrorException();
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
    }

    @Override
    public void updateActivityType(User user) throws DatabaseErrorException, DuplicateActivityTypeException, ActivityTypeNotExistException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            if(!this.isExistInMapper(connection, user.getID().toString(), user.getTargetActivityTypeName())) {
                throw new ActivityTypeNotExistException(user.getOperatedActivityType().getName());
            }
            if(this.isExistInMapper(connection, user.getID().toString(), user.getOperatedActivityType().getName()) &&
                    !user.getOperatedActivityType().getName().equals(user.getTargetActivityTypeName())) {
                throw new DuplicateActivityTypeException();
            }
            this.createInActivityTypeTable(connection, user.getOperatedActivityType());
            this.updateInActivityTypeUserMapper(connection, user.getID().toString(), user.getTargetActivityTypeName(), user.getOperatedActivityType());

        } catch (SQLException e) {
            throw new DatabaseErrorException();
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
    }

    @Override
    public void deleteActivityType(User user) throws DatabaseErrorException, ActivityTypeNotExistException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            if(!this.isExistInMapper(connection, user.getID().toString(), user.getTargetActivityTypeName())) {
                throw new ActivityTypeNotExistException(user.getTargetActivityTypeName());
            }
            this.removeFromActivityTypeUserMapper(connection, user.getID().toString(), user.getTargetActivityTypeName());

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

                try (ResultSet rs = stmt.executeQuery()) {
                    if(rs.next()) {
                        user = new User(UUID.fromString(rs.getString("id")), this.getActivityTypeList(connection, userID));
                    }
                }

            }

        } catch (SQLException e) {
            throw new DatabaseErrorException();
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
        return user;
    }

    private boolean isExistInMapper(Connection connection, String userID, String activityTypeName) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT COUNT(*) FROM `activity_user_mapper` " +
                "WHERE activity_user_mapper.user_id = ? " +
                "AND activity_user_mapper.activity_type_name = ?"
            )) {
                stmt.setString(1, userID);
                stmt.setString(2, activityTypeName);

                try (ResultSet rs = stmt.executeQuery()) {
                    rs.next();
    
                    return rs.getInt(1) == 1;
                }
            }
    }

    private List<ActivityType> getActivityTypeList(Connection connection, String userID) throws SQLException {
        List<ActivityType> activityTypeList = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM `activity_user_mapper` WHERE activity_user_mapper.user_id = ?"
            )) {
                stmt.setString(1, userID);

                try (ResultSet rs = stmt.executeQuery()) {
                    while(rs.next()) {
                        String activityTypeName = rs.getString("activity_type_name");
                        boolean isEnable = rs.getInt("is_enable") == 1;
                        boolean isPrivate = rs.getInt("is_private") == 1;
    
                        ActivityType activityType = new ActivityType(activityTypeName, isEnable, isPrivate);
                        activityTypeList.add(activityType);
                    }
                }

            }
        return activityTypeList;
    }

    private void createInActivityTypeTable(Connection connection, ActivityType activityType) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
            "INSERT IGNORE INTO `activity_type` (`name`) VALUES (?)"
        )) {
            stmt.setString(1, activityType.getName());

            stmt.executeUpdate();
        }
    }

    private void createInActivityTypeUserMapper(Connection connection, String userID, ActivityType activityType)
            throws SQLException {
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
    }

    private void updateInActivityTypeUserMapper(Connection connection, String userID, String targetActivityTypeName, ActivityType activityType)
            throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
            "UPDATE `activity_user_mapper` " + 
            "SET `activity_type_name`= ?, `is_enable`= ?,`is_private`= ? " + 
            "WHERE activity_user_mapper.activity_type_name = ?" +
            "AND activity_user_mapper.user_id = ?"
        )) {
            stmt.setString(1, activityType.getName());
            stmt.setInt(2, activityType.isEnable() ? 1 : 0);
            stmt.setInt(3, activityType.isPrivate() ? 1 : 0);
            stmt.setString(4, targetActivityTypeName);
            stmt.setString(5, userID);

            stmt.executeUpdate();
        }
    }

    private void removeFromActivityTypeUserMapper(Connection connection, String userID, String targetActivityTypeName)
            throws SQLException {
        try(PreparedStatement stmt = connection.prepareStatement(
            "DELETE FROM `activity_user_mapper` " +
            "WHERE activity_user_mapper.user_id = ? " +
            "AND activity_user_mapper.activity_type_name = ?"
        )) {
            stmt.setString(1, userID);
            stmt.setString(2, targetActivityTypeName);

            stmt.executeUpdate();
        }
    }

}