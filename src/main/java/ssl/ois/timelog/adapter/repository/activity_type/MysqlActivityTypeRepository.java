package ssl.ois.timelog.adapter.repository.activity_type;

import org.springframework.beans.factory.annotation.Autowired;
import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.exception.activity.GetActivityTypeErrorException;
import ssl.ois.timelog.exception.activity.SaveActivityTypeErrorException;
import ssl.ois.timelog.model.activity_type.ActivityType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MysqlActivityTypeRepository implements ActivityTypeRepository{
    @Autowired
    private MysqlDriverAdapter mysqlDriverAdapter;

    public void addActivityType(ActivityType activityType) throws SaveActivityTypeErrorException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO `activity_type` " +
                            "(`id`,`activity_name`, `unit_id`, `is_enable`, `is_private`, `is_deleted`) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            )) {
                stmt.setString(1, activityType.getId().toString());
                stmt.setString(2, activityType.getActivityName());
                stmt.setString(3, activityType.getUnitId().toString());
                stmt.setInt(4, activityType.isEnable() ? 1 : 0);
                stmt.setInt(5, activityType.isPrivate() ? 1 : 0);
                stmt.setInt(6, activityType.isDeleted() ? 1 : 0);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SaveActivityTypeErrorException(activityType.getActivityName());
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
    }

    public void updateActivityType(ActivityType activityType) throws SaveActivityTypeErrorException{
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE `activity_type`" +
                        "SET `activity_name`= ?, `is_enable`= ?, `is_private`= ?, `is_deleted`= ?" +
                        "WHERE `id` = ?")) {

                stmt.setString(1, activityType.getActivityName());
                stmt.setInt(2, activityType.isEnable() ? 1 : 0);
                stmt.setInt(3, activityType.isPrivate() ? 1 : 0);
                stmt.setInt(4, activityType.isDeleted() ? 1 : 0);
                stmt.setString(5, activityType.getId().toString());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SaveActivityTypeErrorException(activityType.getActivityName());
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
    }

    public ActivityType findByNameAndUnitId(String activityName, String unitId) throws GetActivityTypeErrorException{
        Connection connection = null;
        ActivityType activityType = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT `activity_type`.*" +
                        "FROM `activity_type`" +
                        "WHERE `activity_type`.`activity_name` = ? " +
                        "AND `activity_type`.`unit_id` = ? ")) {

                stmt.setString(1, activityName);
                stmt.setString(2, unitId);

                try (ResultSet rs = stmt.executeQuery()) {
                    rs.next();

                    activityType = new ActivityType(
                            UUID.fromString(rs.getString("id")),
                            rs.getString("activity_name"),
                            UUID.fromString(rs.getString("unit_id")),
                            rs.getInt("is_enable") == 1,
                            rs.getInt("is_private") == 1,
                            rs.getInt("is_deleted") == 1
                    );
                }
            }
        } catch (SQLException e) {
            throw new GetActivityTypeErrorException(activityName);
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
        return activityType;
    }

    public ActivityType findById(String id) throws GetActivityTypeErrorException{
        Connection connection = null;
        ActivityType activityType = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT `activity_type`.*" +
                    "FROM `activity_type`" +
                    "WHERE `activity_type`.`id` = ?")) {

                stmt.setString(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    rs.next();

                    activityType = new ActivityType(
                            UUID.fromString(rs.getString("id")),
                            rs.getString("activity_name"),
                            UUID.fromString(rs.getString("unit_id")),
                            rs.getInt("is_enable") == 1,
                            rs.getInt("is_private") == 1,
                            rs.getInt("is_deleted") == 1
                    );
                }
            }
        } catch (SQLException e) {
            throw new GetActivityTypeErrorException(id);
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
        return activityType;
    }
}
