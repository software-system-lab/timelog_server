package ssl.ois.timelog.adapter.repository.activity_type;

import org.springframework.beans.factory.annotation.Autowired;
import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.model.activity_type.ActivityType;
import ssl.ois.timelog.exception.log.SaveLogErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MysqlActivityTypeRepository implements ActivityTypeRepository{
    @Autowired
    private MysqlDriverAdapter mysqlDriverAdapter;

    public void addActivityType(ActivityType activityType) throws SaveLogErrorException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO `activity_type` " +
                            "(`id`,`activity_name`, `unit_id`, `is_enable`, `is_private`, `is_deleted`) " +
                            "VALUES (?, ?, ?, ?, ?)"
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
            throw new SaveLogErrorException(activityType.getActivityName());
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
    }
}
