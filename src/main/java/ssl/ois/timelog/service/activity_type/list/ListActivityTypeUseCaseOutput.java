package ssl.ois.timelog.service.activity_type.list;

import ssl.ois.timelog.model.activity_type.ActivityType;

import java.util.ArrayList;
import java.util.List;

public class ListActivityTypeUseCaseOutput {
    private List<ActivityType> activityTypes;

    public ListActivityTypeUseCaseOutput() {
        this.activityTypes = new ArrayList<>();
    }

    public List<ActivityType> getActivityTypes() {
        return activityTypes;
    }

    public void setActivityTypes(List<ActivityType> activityTypes) {
        this.activityTypes = activityTypes;
    }
}
