package ssl.ois.timelog.adapter.repository.memory;

import ssl.ois.timelog.service.repository.user.UnitRepository;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.model.team.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MemoryUnitRepository implements UnitRepository {
    /*
    users Map<String, UnitInterface> UUID, user
    */
    private Map<String, UnitInterface> users;
    private List<UnitInterface> teams;

    public MemoryUnitRepository() {
        this.users = new HashMap<>();
        this.teams = new ArrayList<>();
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

    @Override
    public Role getRole(String userID, String teamID){
        for(UnitInterface team : this.teams){
            if(team.getID().toString().equals(teamID)){
                return ((Team)team).getMemberRoleMap().get(UUID.fromString(userID));
            }
        }
        return null;
    }

    public void init(Map<String, UnitInterface> users, List<UnitInterface> teams){
        this.users = users;
        this.teams = teams;
    }
}
