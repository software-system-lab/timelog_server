package ssl.ois.timelog.adapter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ssl.ois.timelog.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.exception.activity.SaveActivityTypeErrorException;
import ssl.ois.timelog.service.activity_type.add.AddActivityTypeUseCase;
import ssl.ois.timelog.service.activity_type.add.AddActivityTypeUseCaseInput;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v2/activity")
public class ActivityRestAdapter {
    @Autowired
    @Qualifier("addActivityTypeUseCase")
    private AddActivityTypeUseCase addActivityTypeUseCase;
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
    public void listActivity(HttpServletResponse response) {

    }

    @PutMapping("/edit/{activityId}")
    public void editActivity(@PathVariable String activityId, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @DeleteMapping("/remove/{activityId}")
    public void removeActivity(@PathVariable String activityId, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
