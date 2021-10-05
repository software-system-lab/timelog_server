package ssl.ois.timelog.adapter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.service.exception.role.GetRoleErrorException;
import ssl.ois.timelog.service.role.get.GetRoleUseCase;
import ssl.ois.timelog.service.role.get.GetRoleUseCaseInput;
import ssl.ois.timelog.service.role.get.GetRoleUseCaseOutput;

@RestController
@RequestMapping("/api/role")
public class RoleRestAdapter {
    @Autowired
    GetRoleUseCase getRoleUseCase;

    @PostMapping(value = "/leader")
    public ResponseEntity<Boolean> isLeader(@RequestBody GetRoleUseCaseInput input){
        GetRoleUseCaseOutput output = new GetRoleUseCaseOutput();
        try{
            getRoleUseCase.execute(input, output);
        }catch(GetRoleErrorException e){
            if (input.getUserID().toString().equals("76d87586-375d-42ea-917c-ce3701c7c162")) {
                return ResponseEntity.status(HttpStatus.OK).body(false);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        return ResponseEntity.status(HttpStatus.OK).body(output.getRole().equals("LEADER"));
    }

    @PostMapping("/member")
    public ResponseEntity<Boolean> isMember(@RequestBody GetRoleUseCaseInput input){
        GetRoleUseCaseOutput output = new GetRoleUseCaseOutput();
        try{
            getRoleUseCase.execute(input, output);
        }catch(GetRoleErrorException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

        return ResponseEntity.status(HttpStatus.OK).body(output.getRole().equals("MEMBER"));
    }

    @PostMapping("/professor")
    public ResponseEntity<Boolean> isProfessor(@RequestBody GetRoleUseCaseInput input){
        GetRoleUseCaseOutput output = new GetRoleUseCaseOutput();
        try{
            getRoleUseCase.execute(input, output);
        }catch(GetRoleErrorException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

        return ResponseEntity.status(HttpStatus.OK).body(output.getRole().equals("PROFESSOR"));
    }

    @PostMapping("/stakeholder")
    public ResponseEntity<Boolean> isStakeHolder(@RequestBody GetRoleUseCaseInput input){
        GetRoleUseCaseOutput output = new GetRoleUseCaseOutput();
        try{
            getRoleUseCase.execute(input, output);
        }catch(GetRoleErrorException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

        return ResponseEntity.status(HttpStatus.OK).body(output.getRole().equals("STAKEHOLDER"));
    }
}