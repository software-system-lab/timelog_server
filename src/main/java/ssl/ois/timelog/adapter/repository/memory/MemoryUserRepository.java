package ssl.ois.timelog.adapter.repository.memory;

import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.repository.user.UserRepository;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.model.unit.Unit;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.UUID;

public class MemoryUserRepository implements UserRepository {
    private Map<String, UnitInterface> users;

    public MemoryUserRepository() {
        this.users = new HashMap<>();
    }

    @Override
    public void save(UnitInterface user) {
        this.users.put(user.getID().toString(), user);
    }

    @Override
    public void addActivityType(UnitInterface user) {
        this.save(user);
    }

    @Override
    public void editActivityType(UnitInterface user) {
        this.save(user);
    }

    @Override
    public void removeActivityType(UnitInterface user)  {
        this.save(user);
    }

    @Override
    public UnitInterface findByUserID(String userID) {
        return this.users.get(userID);
    }

    @Override
    public UUID findActivityUserMapperID(String userID, String activityTypeName) {
        UUID activityUserMapperID = null;
        for(final ActivityType activityType: this.findByUserID(userID).getActivityTypeList()){
            if(activityType.getName().equals(activityTypeName)){
                activityUserMapperID = activityType.getId();
            }
        }
        return activityUserMapperID;
    }
}
