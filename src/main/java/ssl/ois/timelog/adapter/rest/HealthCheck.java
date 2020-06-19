package ssl.ois.timelog.adapter.rest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.exception.user.InitUserDataErrorException;
import ssl.ois.timelog.service.user.enter.EnterUseCase;
import ssl.ois.timelog.service.user.enter.EnterUseCaseInput;
import ssl.ois.timelog.service.user.enter.EnterUseCaseOutput;

@RestController
@RequestMapping("/")
public class HealthCheck {
    @Autowired
    EnterUseCase enterUseCase;

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

    @GetMapping(value = "/")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Timelog server is healthy");
    }

    @GetMapping(value = "/test")
    public String foo() {
        return "get successfully";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "/";
    }
}
