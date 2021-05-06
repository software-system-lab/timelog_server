package ssl.ois.timelog.service.manager;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import ssl.ois.timelog.service.exception.team.InitTeamDataErrorException;
import ssl.ois.timelog.service.exception.team.GetMemberOfErrorException;
import ssl.ois.timelog.model.team.Role;

public interface AMSManager {
    Map<UUID,String>  getMemberOf(String username)throws GetMemberOfErrorException, InitTeamDataErrorException;
    Map<UUID, Role> getTeamRoleRelation(String teamId)throws InitTeamDataErrorException; 
}
