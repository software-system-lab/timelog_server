package ssl.ois.timelog.adapter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ssl.ois.timelog.exception.log.GetLogErrorException;
import ssl.ois.timelog.service.log.add.AddLogUseCaseInput;
import ssl.ois.timelog.exception.AccountErrorException;
import ssl.ois.timelog.exception.DatabaseErrorException;
import ssl.ois.timelog.exception.log.SaveLogErrorException;
import ssl.ois.timelog.service.log.add.AddLogUseCase;
import ssl.ois.timelog.service.log.edit.EditLogUseCase;
import ssl.ois.timelog.service.log.edit.EditLogUseCaseInput;
import ssl.ois.timelog.service.log.list.ListLogUseCase;
import ssl.ois.timelog.service.log.list.ListLogUseCaseInput;
import ssl.ois.timelog.service.log.list.ListLogUseCaseOutput;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v2/log")
public class LogRestAdapter {

    @Autowired
    @Qualifier("listTeamLogUseCase")
    private ListLogUseCase listTeamLogUseCase;

    @Autowired
    @Qualifier("addLogUseCase")
    private AddLogUseCase addLogUseCase;

    @Autowired
    @Qualifier("editLogUseCase")
    private EditLogUseCase editLogUseCase;

    @GetMapping("/list")
    public ListLogUseCaseOutput listTeamLog(
            @RequestParam String unitId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpServletResponse response) {
        ListLogUseCaseInput input = new ListLogUseCaseInput();
        ListLogUseCaseOutput output = new ListLogUseCaseOutput();

        input.setUnitId(unitId);
        input.setStartDate(startDate);
        input.setEndDate(endDate);

        try {
            this.listTeamLogUseCase.execute(input, output);
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return output;
    }

    @PostMapping("/add")
    public void addPersonLog(
            @RequestBody AddLogUseCaseInput requestBody,
            HttpServletResponse response) {

        try {
            this.addLogUseCase.execute(requestBody);
        } catch (SaveLogErrorException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @PutMapping("/edit/{logId}")
    public void editLog(
            @PathVariable String logId,
            @RequestBody EditLogUseCaseInput input,
            HttpServletResponse response){

        try {
            this.editLogUseCase.execute(input);
        } catch (GetLogErrorException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (SaveLogErrorException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @DeleteMapping("/remove/{logId}")
    public void removeLog(@PathVariable String logId, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
