package ssl.ois.timelog.adapter.manager;
import ssl.ois.timelog.service.manager.AMSManager;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;
import ssl.ois.timelog.service.exception.team.InitTeamDataErrorException;
import ssl.ois.timelog.service.exception.team.GetMemberOfErrorException;

public class DirectoryAMSManager implements AMSManager {

    private String amsHost;
    private String amsPort;
    private RestTemplate restTemplate;

    DirectoryAMSManager(String amsHost, String amsPort){
        this.amsHost = amsHost;
        this.amsPort = amsPort;
        this.restTemplate = new RestTemplate();

    }

    public List<UUID> getMemberOf(String username)throws GetMemberOfErrorException, InitTeamDataErrorException{
        List<UUID> teamUUID = new ArrayList<>();
        try {
            final String url = this.amsHost + this.amsPort + "/team/get/memberOf";
            List<String> result = this.restTemplate.postForObject(url, username, List.class);
            for(String uid :result){
                uid = uid.replaceAll("^\"|\"$", "");
                teamUUID.add(UUID.fromString(uid));
            }
        } catch (RestClientException e) {
            throw new GetMemberOfErrorException();
        } 
        return teamUUID;
    }

    public Map<UUID, Role> getTeamRoleRelation(String teamId) throws InitTeamDataErrorException{
        Map<UUID, Role> memberRoleMap = new HashMap<>();
        try {
            final String url = this.amsHost + this.amsPort + "/team/get/members";
            List<String> result = this.restTemplate.postForObject(url, teamId, List.class);
            memberRoleMap.put(this.getLeader(teamId), Role.LEADER);

            for(String uid :result){
                uid = uid.replaceAll("^\"|\"$", "");
                userID = UUID.fromString(uid);
                if(memberRoleMap.get(userID)!=null){
                    memberRoleMap.put(userID, Role.MEMBER);
                }
            }
        } catch (RestClientException e) {
            throw new InitTeamDataErrorException();
        } 
        return memberRoleMap;
    }

    private UUID getLeader(String teamId) {
        UUID leaderID = null;
        try {
            final String url = this.amsHost + this.amsPort + "/team/get/leader";
            String result = this.restTemplate.postForObject(url, teamId, String.class);
            leaderID = this.getUidFromName(result);
        } catch (RestClientException e) {
            //throw exception
        } 
        return leaderID;
    }

    private UUID getUidFromName(String cn) {
        UUID id = null;
        try {
            final String url = this.amsHost + this.amsPort + "/team/get/uuid/user";
            String result = this.restTemplate.postForObject(url, teamId, String.class);
            uid = result.replaceAll("^\"|\"$", "");
            leaderID = UUID.fromString(uid);
        } catch (RestClientException e) {
            //throw exception
        } 
        return leaderID;
    }
}