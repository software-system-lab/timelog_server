package ssl.ois.timelog.adapter.repository.memory;

import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.repository.user.UnitRepository;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.connect.Unit;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryUnitRepository implements UnitRepository {
    /*
    users Map<String, Unit> UUID, unit
    */
    private Map<String, Unit> units;

    public MemoryUnitRepository() {
        this.units = new HashMap<>();
    }

    @Override
    public void save(Unit unit) {
        this.units.put(unit.getID().toString(), unit);
    }

    @Override
    public void addActivityType(Unit unit) {
        this.save(unit);
    }

    @Override
    public void editActivityType(Unit unit) {
        this.save(unit);
    }

    @Override
    public void removeActivityType(Unit unit)  {
        this.save(unit);
    }

    @Override
    public Unit findByUnitID(String unitID) {
        return this.units.get(unitID);
    }

    @Override
    public UUID findActivityUserMapperID(String unitID, String activityTypeName) {
        UUID activityUnitMapperID = null;
        for(final ActivityType activityType: this.findByUnitID(unitID).getActivityTypeList()){
            if(activityType.getName().equals(activityTypeName)){
                activityUnitMapperID = activityType.getId();
            }
        }
        return activityUnitMapperID;
    }

    @Override
    public List<UUID> getActivityMapperIDListByUnitID(String unitID) throws DatabaseErrorException {
        return this.findByUnitID(unitID).getActivityTypeList()
                .stream()
                .map(ActivityType::getId)
                .collect(Collectors.toList());
    }
}
