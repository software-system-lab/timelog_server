package ssl.ois.timelog.service.user.belong;

import java.util.UUID;
import org.springframework.stereotype.Service;

import java.util.Map;

import ssl.ois.timelog.service.exception.team.GetMemberOfErrorException;
import ssl.ois.timelog.service.repository.user.UnitRepository;
import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.model.team.Team;
import ssl.ois.timelog.service.exception.team.InitTeamDataErrorException;
import ssl.ois.timelog.service.manager.AccountManager;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.service.exception.DatabaseErrorException;

@Service
public class GetMemberOfUseCase {
    private UnitRepository unitRepository;
    private AccountManager accountManager;

    public GetMemberOfUseCase(UnitRepository unitRepository, AccountManager accountManager) {
        this.unitRepository = unitRepository;
        this.accountManager = accountManager;
    }

    public void execute(GetMemberOfUseCaseInput input, GetMemberOfUseCaseOutput output)throws GetMemberOfErrorException,InitTeamDataErrorException {
        try{
            Map<UUID,String> teamIdList = this.accountManager.getMemberOf(input.getUsername());
            for(Map.Entry<UUID, String> teamID : teamIdList.entrySet()) {
                UnitInterface team = this.unitRepository.findByUserID(teamID.getKey().toString());
                if(team == null){
                    System.out.println("TeamID - unitRepository.findByUserID");
                    System.out.println(teamID.getKey().toString());
                    Map<UUID,Role> memberRoleMap = this.accountManager.getTeamRoleRelation(teamID.getKey().toString());
                    team = new Team(teamID.getKey(),memberRoleMap);
                    this.unitRepository.save(team);
                    this.unitRepository.addRoleRelation(teamID.getKey().toString(), memberRoleMap);
    
                    ActivityType activityType = new ActivityType("Other", true, false);
                    team.addActivityType(activityType);
                    this.unitRepository.addActivityType(team);
                }
                output.addTeamToList(teamID.getValue(),teamID.getKey());
            }
        } catch (AccountErrorException e) {
            throw new GetMemberOfErrorException();
        } catch (DatabaseErrorException | ActivityTypeNotExistException | DuplicateActivityTypeException e) {
            throw new InitTeamDataErrorException();
        }
    }
}
