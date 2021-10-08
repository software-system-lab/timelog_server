package ssl.ois.timelog.adapter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssl.ois.timelog.adapter.view.model.log.history.LogHistoryViewModel;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.log.team.list.ListTeamLogUseCase;
import ssl.ois.timelog.service.log.team.list.ListTeamLogUseCaseInput;
import ssl.ois.timelog.service.log.team.list.ListTeamLogUseCaseOutput;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v2/log")
public class LogRestAdapter {

    @Autowired
    private ListTeamLogUseCase listTeamLogUseCase;

    @GetMapping("/person/list")
    public ResponseEntity<Object> listPersonLog() {
        return null;
    }

    @GetMapping("/team/list")
    public ResponseEntity<ListTeamLogUseCaseOutput> listTeamLog(@RequestParam String teamId, @RequestParam String startDate, @RequestParam String endDate) {
        System.out.println("/team/list");
        ListTeamLogUseCaseInput input = new ListTeamLogUseCaseInput();
        ListTeamLogUseCaseOutput output = new ListTeamLogUseCaseOutput();

        input.setTeamId(teamId);
        input.setStartDate(startDate);
        input.setEndDate(endDate);

        try {
            this.listTeamLogUseCase.execute(input, output);
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (DatabaseErrorException | AccountErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(output);
    }

    @PostMapping("/person/add")
    public void addPersonLog(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @PostMapping("/team/add")
    public void addTeamLog(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @PutMapping("/edit/{logId}")
    public void editLog(@PathVariable String logId, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @DeleteMapping("/remove/{logId}")
    public void removeLog(@PathVariable String logId, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
