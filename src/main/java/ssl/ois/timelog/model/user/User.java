package ssl.ois.timelog.model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.timebox.Timebox;
import ssl.ois.timelog.model.unit.AbstractUnit;

public class User extends AbstractUnit{

    private List<Timebox> timeboxList;

    public User(UUID id) {
        super(id);
        this.timeboxList = new ArrayList<>();
    }

    public User(UUID id, List<ActivityType> activityTypeList) {
        super(id, activityTypeList);
    }

    public User(UUID id, List<ActivityType> activityTypeList, List<Timebox> timeboxList) {
        super(id, activityTypeList);
        this.timeboxList = timeboxList;
    }

    @Override
    public List<Timebox> getTimeboxList() { return this.timeboxList; }

    @Override
    public void addTimebox(Timebox timebox) {
        this.timeboxList.add(timebox);
    }

}
