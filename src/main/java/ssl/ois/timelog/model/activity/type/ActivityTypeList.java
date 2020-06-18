package ssl.ois.timelog.model.activity.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityTypeList {
    private String userID;
    private List<ActivityType> typeList;

    public ActivityTypeList(String userID) {
        this.typeList = new ArrayList<>();
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public List<ActivityType> getTypeList() {
        return Collections.unmodifiableList(typeList);
    }

    public void updateType(String activityTypeName, ActivityType activityType) {
        if (this.typeList.removeIf(type -> type.getName().equals(activityTypeName))) {
            this.typeList.add(activityType);
        }
    }

    public void newType(String activityTypeName) {
        ActivityType activityType = new ActivityType(activityTypeName);
        this.typeList.add(activityType);
    }

    public boolean removeType(String activityTypeName) {
        return this.typeList.removeIf(activityType -> activityType.getName().equals(activityTypeName));
    }
}
