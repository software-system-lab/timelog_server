package ssl.ois.timelog.adapter.manager.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ssl.ois.timelog.common.MemberDTO;
import ssl.ois.timelog.model.connect.Unit;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.model.team.Team;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.service.manager.AccountManager;

public class MemoryAMSManager implements AccountManager {
    Map<String, Unit> directory; // Map<unitname, unit>

    public MemoryAMSManager(Map<String, Unit> directory){
        this.directory = directory;
    }

    public Map<UUID,String> getBelongingTeams(String username) throws AccountErrorException {
        UUID userID = directory.get(username).getID();
        Map<UUID, String> belongingTeams = new HashMap<>();
        for (Map.Entry<String, Unit> pair: directory.entrySet()){
            if (pair.getValue() instanceof Team){
                if (pair.getValue().getMemberRoleMap().get(userID) != null){
                    belongingTeams.put(pair.getValue().getID(), pair.getKey());
                }
            }
        }
        return belongingTeams;
    }

    public Map<UUID, MemberDTO> getTeamRoleRelation(String teamId) throws AccountErrorException {
        Unit targetTeam = null;

        for (Map.Entry<String, Unit> entry: this.directory.entrySet()) {
            if (entry.getValue().getID().toString().equals(teamId)) {
                targetTeam = entry.getValue();
                break;
            }
        }
        if (targetTeam == null) {
            throw new AccountErrorException("teamId " + teamId + " not found");
        }

        Map<UUID, MemberDTO> memberRoleMap = new HashMap<>();

        try {
            for (Map.Entry<UUID, Role> roleMap: targetTeam.getMemberRoleMap().entrySet()) {
                for (Map.Entry<String, Unit> unitPair: this.directory.entrySet()) {
                    if (roleMap.getKey().toString().equals(unitPair.getValue().getID().toString())) {
                        memberRoleMap.put(roleMap.getKey(), new MemberDTO(
                                unitPair.getValue().getID().toString(),
                                unitPair.getKey(),
                                "",
                                "",
                                roleMap.getValue()
                        ));
                    }
                }
            }
        } catch (UnsupportedOperationException e) {
            throw new AccountErrorException("fatal error");
        }

        return memberRoleMap;
    }

    public String getNameById(String id) throws AccountErrorException{
        for (Map.Entry<String, Unit> pair: directory.entrySet()) {
            if (pair.getValue().getID().equals(UUID.fromString(id))) {
                return pair.getKey();
            }
        }
        throw new AccountErrorException("Cant found " + id);
    }

    public Map<UUID,Role> getMemberRoleOfTeam(String unitID) throws AccountErrorException {
        Unit targetUnit = null;
        for (Map.Entry<String, Unit> pair: directory.entrySet()) {
            if (pair.getValue().getID().toString().equals(unitID)) {
                targetUnit = pair.getValue();
                break;
            }
        }
        if (targetUnit == null) {
            throw new AccountErrorException("Cant found " + unitID);
        }
        else if (targetUnit instanceof Team) {
            return targetUnit.getMemberRoleMap();
        }
        else {
            return null;
        }
    }
}
