package ssl.ois.timelog.service.role.get;

import ssl.ois.timelog.model.team.Role;

public class GetRoleUseCaseOutput {
    private String role;

    public GetRoleUseCaseOutput(Role role){
        this.role = role.name();
    }
    public GetRoleUseCaseOutput(){}

    public String getRole(){
        return this.role;
    }

    public void setRole(Role role){
        this.role = role.name();
    }
}