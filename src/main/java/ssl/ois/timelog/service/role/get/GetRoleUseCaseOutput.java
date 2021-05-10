package ssl.ois.timelog.service.role.get;

import ssl.ois.timelog.model.team.Role;

public class GetRoleUseCaseOutput {
    private Role role;

    public GetRoleUseCaseOutput(Role role){
        this.role = role;
    }
    public GetRoleUseCaseOutput(){}

    public Role getRole(){
        return this.role;
    }

    public void setRole(Role role){
        this.role = role;
    }
}