package ssl.ois.timelog.adapter.repository.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.service.exception.activity.GetActivityTypeErrorException;
import ssl.ois.timelog.service.exception.activity.SaveActivityTypeErrorException;
import ssl.ois.timelog.service.repository.activity.ActivityTypeListRepository;

public class MysqlActivityTypeListRepository implements ActivityTypeListRepository{
    @Autowired
    private MysqlDriverAdapter mysqlDriverAdapter;

	@Override
	public void save(ActivityTypeList activityTypeList) throws SaveActivityTypeErrorException {
        Connection connection = null;
        try {
            connection = this.mysqlDriverAdapter.getConnection();

            StringBuffer sql = new StringBuffer("INSERT INTO `activity_type` (`name`, `user_id`, `is_private`, `is_enable` VALUES (?, ?, ?, ?)");
            for(int i=1; i<activityTypeList.getTypeList().size(); i++) {
                sql.append(", (?, ?, ?, ?)");
            }

            try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
                List<ActivityType> activityTypes = activityTypeList.getTypeList();
                
                for(int i=0; i<activityTypes.size(); i++) {
                    stmt.setString(i*4+1, activityTypes.get(i).getName());
                    stmt.setString(i*4+1, activityTypeList.getUserID());
                    stmt.setInt(i*4+2, activityTypes.get(i).isPrivate() ? 1 : 0);
                    stmt.setInt(i*4+3, activityTypes.get(i).isEnable() ? 1 : 0);
                }

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SaveActivityTypeErrorException();
        } finally {
            this.mysqlDriverAdapter.closeConnection(connection);
        }
	}

	@Override
	public ActivityTypeList findByUserID(String userID) throws GetActivityTypeErrorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(ActivityTypeList activityTypeList)
			throws GetActivityTypeErrorException, SaveActivityTypeErrorException {
		// TODO Auto-generated method stub
	}
}