package ssl.ois.timelog.adapter.repository.memory;

import ssl.ois.timelog.service.repository.user.UnitRepository;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.model.team.Role;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryUnitRepository implements UnitRepository {
    private Map<String, UnitInterface> users;

    public MemoryUnitRepository() {
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
    public void addRoleRelation(String teamID, Map<UUID,Role> memberRoleMap) {

    }

    @Override
    public UnitInterface findByUserID(String userID) {
        return this.users.get(userID);
    }

    public void insertTeamToUnit(UnitInterface team) {
        this.users.put(team.getID().toString(), team);
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
