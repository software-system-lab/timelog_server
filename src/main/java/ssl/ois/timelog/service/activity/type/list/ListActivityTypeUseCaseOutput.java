package ssl.ois.timelog.service.activity.type.list;

import java.util.ArrayList;
import java.util.List;

import ssl.ois.timelog.model.activity.type.ActivityType;

public class ListActivityTypeUseCaseOutput {
    class UnitDTO{
        private String unitID;
        private String unitName;
        private List<ActivityType> activityTypeList;

        public UnitDTO(String unitID, String unitName, List<ActivityType> activityTypeList) {
            this.unitID = unitID;
            this.unitName = unitName;
            this.activityTypeList = activityTypeList;
        }

        public void setUnitID(String unitID) {
            this.unitID = unitID;
        }

        public String getUnitID() {
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

    public ListActivityTypeUseCaseOutput(){
        this.unitDTOList = new ArrayList<>();
    }
    public void addUnitDTOtoList(String unitID, String unitName, List<ActivityType> activityTypeList) {
        UnitDTO unitDTO = new UnitDTO(unitID, unitName, activityTypeList);
        this.unitDTOList.add(unitDTO);
    }

    public List<UnitDTO> getUnitDTOList() {
        return this.unitDTOList;
    }
}