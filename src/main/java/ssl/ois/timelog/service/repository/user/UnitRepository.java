package ssl.ois.timelog.service.repository.user;

import ssl.ois.timelog.model.connect.Unit;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UnitRepository {
    void save(Unit user) throws DatabaseErrorException, DuplicateActivityTypeException, ActivityTypeNotExistException;
    Unit findByUnitID(String userID) throws DatabaseErrorException;
    void addActivityType(Unit user) throws DatabaseErrorException, DuplicateActivityTypeException;
    void editActivityType(Unit user) throws DatabaseErrorException, DuplicateActivityTypeException, ActivityTypeNotExistException;
    void removeActivityType(Unit user) throws DatabaseErrorException, ActivityTypeNotExistException;
    UUID findActivityUserMapperID(String userID, String activityTypeName) throws DatabaseErrorException;    
    default void addRoleRelation(String teamID, Map<UUID, Role> memberRoleMap) throws DatabaseErrorException {
    }
    default List<UUID> getActivityMapperIDListByUnitID(String unitID) throws DatabaseErrorException {
        return null;
    }
}

