package ssl.ois.timelog.model.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityTypeList {
    private String userID;
    private List<ActivityType> activityTypeList;

    public ActivityTypeList() {
        this.activityTypeList = new ArrayList<>();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<ActivityType> getActivityTypeList() {
        return Collections.unmodifiableList(activityTypeList);
    }

    public void newType(String activityTypeName) {
        ActivityType activityType = new ActivityType(activityTypeName);
        this.activityTypeList.add(activityType);
    }

    public boolean removeType(String activityTypeName) {
        return this.activityTypeList.removeIf(activityType -> activityType.getName().equals(activityTypeName));
    }
}
