package ssl.ois.timelog.service.user.enter;

import ssl.ois.timelog.model.activity.type.ActivityTypeList;

public class EnterUseCaseOutput {
    private ActivityTypeList activityTypeList;

    public void setActivityTypeList(ActivityTypeList activityTypeList) {
        this.activityTypeList = activityTypeList;
    }

    public ActivityTypeList getActivityTypeList() {
        return this.activityTypeList;
    }
}
