package ssl.ois.timelog.service.activity.type.list;

import java.util.List;
import java.util.UUID;

import ssl.ois.timelog.model.activity.type.ActivityType;

public class ListActivityTypeUseCaseOutput {
//    private List<ActivityType> activityTypeList;
//
//    public void setActivityTypeList(List<ActivityType> activityTypeList) {
//        this.activityTypeList = activityTypeList;
//    }
//
//    public List<ActivityType> getActivityTypeList() {
//        return this.activityTypeList;
//    }

    class UnitDTO{
        private UUID unitID;
        private String unitName;
        private List<ActivityType> activityTypeList;

        public UnitDTO(UUID unitID, String unitName, List<ActivityType> activityTypeList) {
            this.unitID = unitID;
            this.unitName = unitName;
            this.activityTypeList = activityTypeList;
        }

        public void setUnitID(UUID unitID) {
            this.unitID = unitID;
        }

        public UUID getUnitID() {
            return this.unitID;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getUnitName() {
            return this.unitName;
        }

        public void addActivityType(ActivityType activityType) {
            this.activityTypeList.add(activityType);
        }

        public List<ActivityType> getActivityTypeList() {
            return this.activityTypeList;
        }
    }

    private List<UnitDTO> unitDTOList;

    public void addUnitDTOtoList(UUID unitID, String unitName, List<ActivityType> activityTypeList) {
        UnitDTO unitDTO = new UnitDTO(unitID, unitName, activityTypeList);
    }

    public List<UnitDTO> getUnitDTOList() {
        return this.unitDTOList;
    }
}