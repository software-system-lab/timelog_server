package ssl.ois.timelog.adapter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.exception.user.InitUserDataErrorException;
import ssl.ois.timelog.service.team.get.GetTeamUseCase;
import ssl.ois.timelog.service.user.enter.EnterUseCase;
import ssl.ois.timelog.service.user.enter.EnterUseCaseInput;
import ssl.ois.timelog.service.user.enter.EnterUseCaseOutput;
import ssl.ois.timelog.service.user.belong.GetMemberOfUseCase;
import ssl.ois.timelog.service.user.belong.GetMemberOfUseCaseInput;
import ssl.ois.timelog.service.user.belong.GetMemberOfUseCaseOutput;
import ssl.ois.timelog.service.exception.team.GetMemberOfErrorException;
import ssl.ois.timelog.service.team.get.GetTeamUseCase;
import ssl.ois.timelog.service.team.get.GetTeamUseCaseInput;
import ssl.ois.timelog.service.team.get.GetTeamUseCaseOutput;
import ssl.ois.timelog.service.exception.team.GetTeamErrorException;
import ssl.ois.timelog.service.exception.team.InitTeamDataErrorException;


@RestController
@RequestMapping("/api")
public class LoginAdapter {
    @Autowired
    EnterUseCase enterUseCase;

    @Autowired
    GetMemberOfUseCase getMemberOfUseCase;

    @Autowired
    GetTeamUseCase getTeamUseCase;

    @PostMapping(value = "/login")
    public ResponseEntity<EnterUseCaseOutput> enterTimelog(@RequestBody EnterUseCaseInput input) {
        EnterUseCaseOutput output = new EnterUseCaseOutput();
        try {
            this.enterUseCase.execute(input, output);
        } catch (DuplicateActivityTypeException | InitUserDataErrorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(output);
        }

        return ResponseEntity.status(HttpStatus.OK).body(output);
    }

    @PostMapping(value = "/belong")
    public ResponseEntity<GetMemberOfUseCaseOutput> getMemberOfList(@RequestBody GetMemberOfUseCaseInput input) {
        GetMemberOfUseCaseOutput output = new GetMemberOfUseCaseOutput();

        try {
            this.getMemberOfUseCase.execute(input, output);
        } catch (GetMemberOfErrorException | InitTeamDataErrorException | DuplicateActivityTypeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(output);
        }

        return ResponseEntity.status(HttpStatus.OK).body(output);
    }

    @PostMapping(value = "/group")
    public ResponseEntity<GetTeamUseCaseOutput> getTeam (@RequestBody GetTeamUseCaseInput input) {
        GetTeamUseCaseOutput output = new GetTeamUseCaseOutput();
        try {
            this.getTeamUseCase.execute(input, output);
        } catch (GetTeamErrorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(output);
        }
        return ResponseEntity.status(HttpStatus.OK).body(output);
    }
}
