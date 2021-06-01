package ssl.ois.timelog.adapter.repository.memory;

import ssl.ois.timelog.service.repository.user.UnitRepository;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.connect.Unit;
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
    private Map<String, Unit> users;
    private List<Unit> teams;

    public MemoryUnitRepository() {
        this.users = new HashMap<>();
        this.teams = new ArrayList<>();
    }

    @Override
    public void save(Unit user) {
        this.users.put(user.getID().toString(), user);
    }

    @Override
    public void addActivityType(Unit user) {
        this.save(user);
    }

    @Override
    public void editActivityType(Unit user) {
        this.save(user);
    }

    @Override
    public void removeActivityType(Unit user)  {
        this.save(user);
    }

    @Override
    public Unit findByUnitID(String userID) {
        return this.users.get(userID);
    }

    @Override
    public UUID findActivityUserMapperID(String userID, String activityTypeName) {
        UUID activityUserMapperID = null;
        for(final ActivityType activityType: this.findByUnitID(userID).getActivityTypeList()){
            if(activityType.getName().equals(activityTypeName)){
                activityUserMapperID = activityType.getId();
            }
        }
        return activityUserMapperID;
    }

    @Override
    public Role getRole(String userID, String teamID){
        for(Unit team : this.teams){
            if(team.getID().toString().equals(teamID)){
                return ((Team)team).getMemberRoleMap().get(UUID.fromString(userID));
            }
        }
        return null;
    }

    public void init(Map<String, Unit> users, List<Unit> teams){
        this.users = users;
        this.teams = teams;
    }
}
