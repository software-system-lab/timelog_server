package ssl.ois.timelog.adapter.repository.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.connect.Unit;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.model.team.Team;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.manager.AccountManager;
import ssl.ois.timelog.service.repository.user.UnitRepository;


public class MysqlUnitRepository implements UnitRepository {
    @Autowired
    private MysqlDriverAdapter mysqlDriverAdapter;

    @Autowired
    private AccountManager accountManager;

    @Override
    public void save(Unit user) throws DatabaseErrorException, DuplicateActivityTypeException,
            ActivityTypeNotExistException {
        try (Connection connection = this.mysqlDriverAdapter.getConnection()){
            try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT IGNORE INTO `unit`(`id`) VALUES (?)"
            )) {
                stmt.setString(1, user.getID().toString());

                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public void addActivityType(Unit user) throws DatabaseErrorException, DuplicateActivityTypeException{
        try (Connection connection = this.mysqlDriverAdapter.getConnection()){

            if(this.isExistInMapper(connection, user.getID().toString(), user.getOperatedActivityType().getName())) {
                if(this.isDeletedInMapper(connection, user.getID().toString(), user.getOperatedActivityType().getName())){
                    this.updateActivityTypeUserMapper(connection, user.getOperatedActivityType());
                }
                else{
                    throw new DuplicateActivityTypeException();
                }
            }
            else {
                this.addActivityType(connection, user.getOperatedActivityType());
                this.addActivityTypeUserMapper(connection, user.getID().toString(), user.getOperatedActivityType());
            }

        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public void editActivityType(Unit user) throws DatabaseErrorException, DuplicateActivityTypeException, ActivityTypeNotExistException {
        try (Connection connection = this.mysqlDriverAdapter.getConnection()){
            if(!this.isExistInMapper(connection, user.getID().toString(), user.getTargetActivityTypeName())) {
                throw new ActivityTypeNotExistException(user.getOperatedActivityType().getName());
            }
            if(this.isExistInMapper(connection, user.getID().toString(), user.getOperatedActivityType().getName()) &&
                    !user.getOperatedActivityType().getName().equals(user.getTargetActivityTypeName())) {
                    if(this.isDeletedInMapper(connection, user.getID().toString(), user.getOperatedActivityType().getName())){
                        this.updateActivityTypeUserMapper(connection, user.getOperatedActivityType());
                    }
                    else{
                        throw new DuplicateActivityTypeException();
                    }
            }
            this.addActivityType(connection, user.getOperatedActivityType());
            this.updateActivityTypeUserMapper(connection, user.getOperatedActivityType());

        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public void removeActivityType(Unit user) throws DatabaseErrorException, ActivityTypeNotExistException {
        try (Connection connection = this.mysqlDriverAdapter.getConnection()){
            if(!this.isExistInMapper(connection, user.getID().toString(), user.getTargetActivityTypeName())) {
                throw new ActivityTypeNotExistException(user.getTargetActivityTypeName());
            }
            this.removeActivityTypeUserMapper(connection, user.getID().toString(), user.getTargetActivityTypeName());
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public void addRoleRelation(String teamID, Map<UUID,Role> memberRoleMap) throws DatabaseErrorException {
        try (Connection connection = this.mysqlDriverAdapter.getConnection()){
            for(Map.Entry<UUID, Role> user : memberRoleMap.entrySet()) {
                this.insertRoleRelation(connection, teamID, user.getKey().toString(), user.getValue().name());
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public Unit findByUnitID(String unitID) throws DatabaseErrorException{
        Logger logger = Logger.getLogger(this.getClass().toString() + "::findByUnitID");
        Unit unit = null;
        try (Connection connection = this.mysqlDriverAdapter.getConnection()){
            try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM `unit` WHERE `id` = ?"
            )) {
                stmt.setString(1, unitID);

                try (ResultSet rs = stmt.executeQuery()){
                    if(rs.next()) {
                        Map<UUID,Role> memberOfTeam = getMemberRoleOfTeam(unitID);
                        if(!memberOfTeam.isEmpty()){
                            unit = new Team(UUID.fromString(rs.getString("id")), this.getActivityTypeList(connection, unitID), memberOfTeam);
                        }
                        else{
                            unit = new User(UUID.fromString(rs.getString("id")), this.getActivityTypeList(connection, unitID));
                        }
                    }
                }
            }
        } catch (SQLException | AccountErrorException e) {
            logger.warning(e.getMessage());
            throw new DatabaseErrorException();
        }
        return unit;
    }

    @Override
    public List<UUID> getActivityMapperIDListByUnitID(String unitID) throws DatabaseErrorException{
        List<UUID> activityMapperIDList = new ArrayList<>();
        try (Connection connection = this.mysqlDriverAdapter.getConnection()){

            try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT `id` FROM `activity_user_mapper` WHERE activity_user_mapper.unit_id = ?" +
                " AND activity_user_mapper.is_deleted = 0 "
            )) {
                stmt.setString(1, unitID);
                try (ResultSet rs = stmt.executeQuery()) {
                    while(rs.next()){
                        UUID activityUserMapperID = UUID.fromString(rs.getString("id"));
                        activityMapperIDList.add(activityUserMapperID);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
        return activityMapperIDList;
    }

    private boolean isExistInMapper(Connection connection, String userID, String activityTypeName) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
            "SELECT COUNT(*) FROM `activity_user_mapper` " +
            "WHERE activity_user_mapper.unit_id = ? " +
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

    private boolean isDeletedInMapper(Connection connection, String userID, String activityTypeName) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
            "SELECT COUNT(*) FROM `activity_user_mapper` " +
            "WHERE activity_user_mapper.unit_id = ? " +
            "AND activity_user_mapper.activity_type_name = ?" +
            "AND activity_user_mapper.is_deleted = ?"
        )) {
            stmt.setString(1, userID);
            stmt.setString(2, activityTypeName);
            stmt.setInt(3, 1);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) == 1;
            }
        }
    }

    private List<ActivityType> getActivityTypeList(Connection connection, String userID) throws SQLException {
        List<ActivityType> activityTypeList = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM `activity_user_mapper` WHERE activity_user_mapper.unit_id = ?" +
                " AND activity_user_mapper.is_deleted = 0 "
            )) {
                stmt.setString(1, userID);

                try (ResultSet rs = stmt.executeQuery()) {
                    while(rs.next()) {
                        UUID id = UUID.fromString(rs.getString("id"));
                        String activityTypeName = rs.getString("activity_type_name");
                        boolean isEnable = rs.getInt("is_enable") == 1;
                        boolean isPrivate = rs.getInt("is_private") == 1;
    
                        ActivityType activityType = new ActivityType(id, activityTypeName, isEnable, isPrivate);

                        activityTypeList.add(activityType);
                    }
                }
            }
        return activityTypeList;
    }

    private Map<UUID, Role> getMemberRoleOfTeam(String unitID) throws AccountErrorException{
        Map<UUID, Role> memberRoleMap = new HashMap<>();
        memberRoleMap = accountManager.getMemberRoleOfTeam(unitID);
        return memberRoleMap;
    }

    private void addActivityType(Connection connection, ActivityType activityType) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
            "INSERT IGNORE INTO `activity_type` (`name`) VALUES (?)"
        )) {
            stmt.setString(1, activityType.getName());

            stmt.executeUpdate();
        }
    }

    private void addActivityTypeUserMapper(Connection connection, String userID, ActivityType activityType)
            throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
            "INSERT INTO `activity_user_mapper` " + 
            "(`id`,`activity_type_name`, `unit_id`, `is_enable`, `is_private`) " +
            "VALUES (?, ?, ?, ?, ?)"
        )) {
            stmt.setString(1, activityType.getId().toString());
            stmt.setString(2, activityType.getName());
            stmt.setString(3, userID);
            stmt.setInt(4, activityType.isEnable() ? 1 : 0);
            stmt.setInt(5, activityType.isPrivate() ? 1 : 0);

            stmt.executeUpdate();
        }
    }

    private void updateActivityTypeUserMapper(Connection connection, ActivityType activityType)
            throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(
            "UPDATE `activity_user_mapper` " + 
            "SET `activity_type_name`= ?, `is_enable`= ?,`is_private`= ?,`is_deleted`= ? " +
            "WHERE activity_user_mapper.id = ?" 
        )) {
            stmt.setString(1, activityType.getName());
            stmt.setInt(2, activityType.isEnable() ? 1 : 0);
            stmt.setInt(3, activityType.isPrivate() ? 1 : 0);
            stmt.setInt(4, 0);
            stmt.setString(5, activityType.getId().toString());

            stmt.executeUpdate();

        }
    }

    private void removeActivityTypeUserMapper(Connection connection, String userID, String targetActivityTypeName)
            throws SQLException {
        try(PreparedStatement stmt = connection.prepareStatement(
            "UPDATE `activity_user_mapper` " +
            "SET `is_deleted`= ? " +
            "WHERE activity_user_mapper.unit_id = ? " +
            "AND activity_user_mapper.activity_type_name = ?"
        )) {
            stmt.setInt(1, 1);
            stmt.setString(2, userID);
            stmt.setString(3, targetActivityTypeName);

            stmt.executeUpdate();
        }
    }

    private void insertRoleRelation(Connection connection, String teamID, String userID, String role) throws SQLException {
        try(PreparedStatement stmt = connection.prepareStatement(
            "INSERT IGNORE INTO `role_relation` VALUES (?,?,?)"
        )) {
            stmt.setString(1, teamID);
            stmt.setString(2, userID);
            stmt.setString(3, role);

            stmt.executeUpdate();
        }
    }

    @Override
    public UUID findActivityUserMapperID(String userID, String activityTypeName) throws DatabaseErrorException {
        UUID activityUserMapperID = null;
        try (Connection connection = this.mysqlDriverAdapter.getConnection()){
            if(this.isExistInMapper(connection, userID, activityTypeName)) {
                try (PreparedStatement stmt = connection.prepareStatement(
                        "SELECT `id` FROM `activity_user_mapper` WHERE `unit_id` = ? AND `activity_type_name` = ?")) {

                    stmt.setString(1, userID);
                    stmt.setString(2, activityTypeName);
                    try (ResultSet rs = stmt.executeQuery()) {
                        rs.next();
                        activityUserMapperID = UUID.fromString(rs.getString("id"));

                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
        return activityUserMapperID;
    }
}