package ssl.ois.timelog.adapter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ssl.ois.timelog.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.exception.activity.GetActivityTypeErrorException;
import ssl.ois.timelog.exception.activity.SaveActivityTypeErrorException;
import ssl.ois.timelog.service.activity_type.add.AddActivityTypeUseCase;
import ssl.ois.timelog.service.activity_type.add.AddActivityTypeUseCaseInput;
import ssl.ois.timelog.service.activity_type.edit.EditActivityTypeUseCase;
import ssl.ois.timelog.service.activity_type.edit.EditActivityTypeUseCaseInput;
import ssl.ois.timelog.service.activity_type.list.ListActivityTypeUseCase;
import ssl.ois.timelog.service.activity_type.list.ListActivityTypeUseCaseInput;
import ssl.ois.timelog.service.activity_type.list.ListActivityTypeUseCaseOutput;
import ssl.ois.timelog.service.activity_type.remove.RemoveActivityTypeUseCase;
import ssl.ois.timelog.service.activity_type.remove.RemoveActivityTypeUseCaseInput;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v2/activity_type")
public class ActivityTypeRestAdapter {

    @Autowired
    @Qualifier("addActivityTypeUseCase")
    private AddActivityTypeUseCase addActivityTypeUseCase;

    @Autowired
    @Qualifier("editActivityTypeUseCase")
    private EditActivityTypeUseCase editActivityTypeUseCase;

    @Autowired
    @Qualifier("removeActivityTypeUseCase")
    private RemoveActivityTypeUseCase removeActivityTypeUseCase;

    @Autowired
    @Qualifier("listActivityTypeUseCase")
    private ListActivityTypeUseCase listActivityTypeUseCase;

    @PostMapping("/add")
    public void addActivity(AddActivityTypeUseCaseInput input, HttpServletResponse response) {
        try {
            this.addActivityTypeUseCase.execute(input);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SaveActivityTypeErrorException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DuplicateActivityTypeException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @GetMapping("/list")
    public ListActivityTypeUseCaseOutput listActivity(
            @RequestParam String unitId,
            HttpServletResponse response) {
        ListActivityTypeUseCaseInput input = new ListActivityTypeUseCaseInput();
        ListActivityTypeUseCaseOutput output = new ListActivityTypeUseCaseOutput();

        input.setUnitId(unitId);

        try{
            this.listActivityTypeUseCase.execute(input, output);
            return output;
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @PutMapping("/{activityId}/edit")
    public void editActivity(
            @PathVariable String activityId,
            @RequestBody EditActivityTypeUseCaseInput requestBody,
            HttpServletResponse response) {
        requestBody.setId(activityId);
        try {
            this.editActivityTypeUseCase.execute(requestBody);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (SaveActivityTypeErrorException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (GetActivityTypeErrorException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{activityId}/remove")
    public void removeActivity(@PathVariable String activityId, HttpServletResponse response) {
        RemoveActivityTypeUseCaseInput input = new RemoveActivityTypeUseCaseInput();
        input.setId(activityId);
        try {
            this.removeActivityTypeUseCase.execute(input);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (GetActivityTypeErrorException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
