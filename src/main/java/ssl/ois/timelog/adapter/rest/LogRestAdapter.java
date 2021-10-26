package ssl.ois.timelog.adapter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ssl.ois.timelog.exception.log.GetLogErrorException;
import ssl.ois.timelog.exception.log.RemoveLogException;
import ssl.ois.timelog.exception.log.SaveLogErrorException;
import ssl.ois.timelog.service.log.add.AddLogUseCase;
import ssl.ois.timelog.service.log.add.AddLogUseCaseInput;
import ssl.ois.timelog.service.log.edit.EditLogUseCase;
import ssl.ois.timelog.service.log.edit.EditLogUseCaseInput;
import ssl.ois.timelog.service.log.list.ListLogUseCase;
import ssl.ois.timelog.service.log.list.ListLogUseCaseInput;
import ssl.ois.timelog.service.log.list.ListLogUseCaseOutput;
import ssl.ois.timelog.service.log.remove.RemoveLogUseCase;
import ssl.ois.timelog.service.log.remove.RemoveLogUseCaseInput;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v2/log")
public class LogRestAdapter {

    @Autowired
    @Qualifier("listLogUseCase")
    private ListLogUseCase listLogUseCase;

    @Autowired
    @Qualifier("addLogUseCase")
    private AddLogUseCase addLogUseCase;

    @Autowired
    @Qualifier("editLogUseCase")
    private EditLogUseCase editLogUseCase;

    @Autowired
    @Qualifier("removeLogUseCase")
    private RemoveLogUseCase removeLogUseCase;

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
            this.listLogUseCase.execute(input, output);
            return output;
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    @PostMapping("/add")
    public void addPersonLog(
            @RequestBody AddLogUseCaseInput requestBody,
            HttpServletResponse response) {

        try {
            this.addLogUseCase.execute(requestBody);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SaveLogErrorException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit/{logId}")
    public void editLog(
            @PathVariable String logId,
            @RequestBody EditLogUseCaseInput requestBody,
            HttpServletResponse response){

        requestBody.setId(logId);

        try {
            this.editLogUseCase.execute(requestBody);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (GetLogErrorException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (SaveLogErrorException | ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove/{logId}")
    public void removeLog(@PathVariable String logId, HttpServletResponse response){
        RemoveLogUseCaseInput input = new RemoveLogUseCaseInput();

        input.setId(logId);

        try {
            this.removeLogUseCase.execute(input);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (RemoveLogException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
