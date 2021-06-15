package ssl.ois.timelog.adapter.manager.memory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.CloseableThreadContext.Instance;

import ssl.ois.timelog.model.connect.Unit;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.model.team.Team;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.service.manager.AccountManager;

public class MemoryAMSManager implements AccountManager {
    Map<String, Unit> directory;
    public MemoryAMSManager(Map<String, Unit> directory){
        this.directory = directory;
    }

    public Map<UUID,String> getBelongingTeams(String username)throws AccountErrorException{
        UUID userID = directory.get(username).getID();
        Map<UUID, String> belongingTeams = new HashMap<>();
        for(Map.Entry<String, Unit> pair: directory.entrySet()){
            if(((Team)pair.getValue()).getMemberRoleMap().get(userID) != null){
                belongingTeams.put(pair.getValue().getID(), pair.getKey());
            }
        }
        return belongingTeams;
    }

    public Map<UUID, Role> getTeamRoleRelation(String teamName) throws AccountErrorException {
        if(directory.get(teamName) instanceof Team)
            return directory.get(teamName).getMemberRoleMap();
        else
            throw new AccountErrorException(teamName + "is not a team");
    }

    public UUID getTeamIdByTeamName(String teamName) throws AccountErrorException{
        if(directory.get(teamName) instanceof Team)
            return directory.get(teamName).getID();
        else
            throw new AccountErrorException(teamName + "is not a team");
    }

    public String getNameById(String id) throws AccountErrorException{
        for(Map.Entry<String, Unit> pair: directory.entrySet()){
            if(pair.getValue().getID().equals(UUID.fromString(id))){
                return pair.getKey();
            }
        }
        throw new AccountErrorException("Cant found " + id);
    }

    public Map<UUID,Role> getMemberRoleOfTeam(String unitID) throws AccountErrorException{
        Unit targetUnit = null;
        for(Map.Entry<String, Unit> pair: directory.entrySet()){
            if(pair.getValue().getID().toString().equals(unitID)){
                targetUnit = pair.getValue();
                break;
            }
        }
        if(targetUnit == null){
            throw new AccountErrorException("Cant found " + unitID);
        }
        else if(targetUnit instanceof Team){
            return targetUnit.getMemberRoleMap();
        }
        else{
            return null;
        }
    }
}
