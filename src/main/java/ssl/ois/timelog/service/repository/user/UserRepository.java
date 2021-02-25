package ssl.ois.timelog.service.repository.user;

import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.model.unit.Unit;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;

import java.util.UUID;

public interface UserRepository {
    void save(UnitInterface user) throws DatabaseErrorException, DuplicateActivityTypeException, ActivityTypeNotExistException;
    UnitInterface findByUserID(String userID) throws DatabaseErrorException;
    void addActivityType(UnitInterface user) throws DatabaseErrorException, DuplicateActivityTypeException;
    void editActivityType(UnitInterface user) throws DatabaseErrorException, DuplicateActivityTypeException, ActivityTypeNotExistException;
    void removeActivityType(UnitInterface user) throws DatabaseErrorException, ActivityTypeNotExistException;
    UUID findActivityUserMapperID(String userID, String activityTypeName) throws DatabaseErrorException;
}
