package ssl.ois.timelog.service.user.belong;

import java.util.UUID;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import ssl.ois.timelog.service.exception.team.GetMemberOfErrorException;
import ssl.ois.timelog.service.repository.user.UnitRepository;
import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.model.team.Team;
import ssl.ois.timelog.service.exception.team.InitTeamDataErrorException;
import ssl.ois.timelog.service.manager.AMSManager;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;



@Service
public class GetMemberOfUseCase {
    private UnitRepository unitRepository;
    private AMSManager amsManager;

    public GetMemberOfUseCase(UnitRepository unitRepository, AMSManager amsManager) {
        this.unitRepository = unitRepository;
        this.amsManager = amsManager;
    }

    public void execute(GetMemberOfUseCaseInput input, GetMemberOfUseCaseOutput output)throws GetMemberOfErrorException, InitTeamDataErrorException, DuplicateActivityTypeException {
        List<UUID> teamIdList = this.amsManager.getMemberOf(input.getUsername());
        for(UUID teamID : teamIdList){
            UnitInterface team = this.unitRepository.findByUserID(teamID.toString());
            if(team == null){
                Map<UUID, Role> memberRoleMap = this.amsManager.getTeamRoleRelation(teamID.toString());
                team = new Team(teamID,memberRoleMap);
                this.unitRepository.save(team);
                this.unitRepository.addRoleRelation(teamID.toString(), memberRoleMap);

                ActivityType activityType = new ActivityType("Other", true, false);
                team.addActivityType(activityType);
                this.unitRepository.addActivityType(team);
            }
        }
    }
}
