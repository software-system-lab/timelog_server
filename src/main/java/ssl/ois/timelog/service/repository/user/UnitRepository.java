package ssl.ois.timelog.service.repository.user;

import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;

import java.util.Map;
import java.util.UUID;

public interface UnitRepository {
    void save(UnitInterface user) throws DatabaseErrorException, DuplicateActivityTypeException, ActivityTypeNotExistException;
    UnitInterface findByUserID(String userID) throws DatabaseErrorException;
    void addActivityType(UnitInterface user) throws DatabaseErrorException, DuplicateActivityTypeException;
    void editActivityType(UnitInterface user) throws DatabaseErrorException, DuplicateActivityTypeException, ActivityTypeNotExistException;
    void removeActivityType(UnitInterface user) throws DatabaseErrorException, ActivityTypeNotExistException;
    UUID findActivityUserMapperID(String userID, String activityTypeName) throws DatabaseErrorException;
    void insertTeamToUnit(UnitInterface team) throws DatabaseErrorException;
    
    default void addRoleRelation(String teamID, Map<UUID, Role> memberRoleMap) throws DatabaseErrorException {

    }
    Role getRole(UUID userID, UUID teamID) throws DatabaseErrorException;
}

