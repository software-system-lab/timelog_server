package ssl.ois.timelog.adapter.manager;
import ssl.ois.timelog.service.manager.AMSManager;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;
import ssl.ois.timelog.service.exception.team.InitTeamDataErrorException;
import ssl.ois.timelog.service.exception.team.GetMemberOfErrorException;
import ssl.ois.timelog.service.exception.AMSErrorException;
import ssl.ois.timelog.model.team.Role;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import org.springframework.web.client.RestClientException;

public class DirectoryAMSManager implements AMSManager {

    private String url;
    private RestTemplate restTemplate;

    public DirectoryAMSManager(String amsHost, String amsPort){
        this.url = "http://" + amsHost + ":" + amsPort;
        this.restTemplate = new RestTemplate();

    }

    public Map<UUID,String> getMemberOf(String username)throws AMSErrorException{
        Map<UUID,String> teamList = new HashMap<>();
        try {
            final String url = this.url + "/team/get/memberOf";
            List<LinkedHashMap<String,String>> result = this.restTemplate.postForObject(url, username, List.class);
            for(LinkedHashMap<String,String> each : result) {
                String uid = each.get("id").replaceAll("^\"|\"$", "");

                teamList.put(UUID.fromString(uid),each.get("name"));
            }
        } catch (RestClientException e) {
            throw new AMSErrorException();
        } 
        return teamList;
    }

    public Map<UUID, Role> getTeamRoleRelation(String teamId) throws AMSErrorException{
        Map<UUID, Role> memberRoleMap = new HashMap<>();
        try {
            final String url = this.url + "/team/get/members";
            List<String> result = this.restTemplate.postForObject(url, teamId, List.class);
            memberRoleMap.put(this.getLeader(teamId), Role.LEADER);

            for(String uid :result){
                uid = uid.replaceAll("^\"|\"$", "");
                UUID userID = UUID.fromString(uid);
                if(memberRoleMap.get(userID)!=null){
                    memberRoleMap.put(userID, Role.MEMBER);
                }
            }
        } catch (RestClientException e) {
            throw new AMSErrorException();
        } 
        return memberRoleMap;
    }

    private UUID getLeader(String teamId) {
        UUID leaderID = null;
        try {
            final String url = this.url + "/team/get/leader";
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
            final String url = this.url + "/team/get/uuid/user";
            String result = this.restTemplate.postForObject(url, cn, String.class);
            String uid = result.replaceAll("^\"|\"$", "");
            id = UUID.fromString(uid);
        } catch (RestClientException e) {
            //throw exception
        } 
        return id;
    }
}


