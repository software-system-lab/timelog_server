package ssl.ois.timelog.cucumber.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import ssl.ois.timelog.adapter.repository.memory.MemoryLogRepository;
import ssl.ois.timelog.cucumber.common.UserStepDefinition;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.log.LogRepository;
import ssl.ois.timelog.service.log.add.*;
import io.cucumber.java.en.Then;

public class AddLogStepDefinition {
    private UserStepDefinition userStepDefinition;
    private String title;
    private String startTime;
    private String endTime;
    private String description;
    private String activityTypeName;
    private LogRepository logRepository;
    private AddLogUseCaseOutput addLogUseCaseOutput;
    private Log log;

    public AddLogStepDefinition(UserStepDefinition userStepDefinition) {
        this.userStepDefinition = userStepDefinition;
    }

    @Given("I {string} from {string} to {string}, the description is {string}")
    public void i_from_to_the_description_is(String title, String startTime, String endTime, String description) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    @Given("Activity type {string} is selected")
    public void activity_type_is_selected(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    @When("I record the activity to Timelog")
    public void i_record_the_activity_to_Timelog() {
        this.logRepository = new MemoryLogRepository();
        AddLogUseCase usecase = new AddLogUseCase(this.logRepository);
        AddLogUseCaseInput addLogUseCaseInput = new AddLogUseCaseInput();
        this.addLogUseCaseOutput= new AddLogUseCaseOutput();
        
        addLogUseCaseInput.setUserID(this.userStepDefinition.getUserID());
        addLogUseCaseInput.setTitle(this.title);
        addLogUseCaseInput.setStartTime(this.startTime);
        addLogUseCaseInput.setEndTime(this.endTime);
        addLogUseCaseInput.setDescription(this.description);
        addLogUseCaseInput.setActivityTypeName(this.activityTypeName);

        usecase.execute(addLogUseCaseInput, this.addLogUseCaseOutput);
    }

    @Then("A new log is added")
    public void a_new_log_is_added() {
        String logID = this.addLogUseCaseOutput.getLogID();

        assertNotNull(logID);

        this.log = this.logRepository.getByID(UUID.fromString(logID));

        assertNotNull(this.log);
    }

    @Then("This log has title {string}")
    public void this_log_has_title(String title) {
        assertEquals(title, this.log.getTitle());
    }

    @Then("This log has start time with {string}")
    public void this_log_has_start_time_with(String startTime) {
        assertEquals(startTime, this.log.getDateFormat().format(this.log.getStartTime()));
    }

    @Then("This log has end time with {string}")
    public void this_log_has_end_time_with(String endTime) {
        assertEquals(endTime, this.log.getDateFormat().format(this.log.getEndTime()));
    }

    @Then("This log has description {string}")
    public void this_log_has_description(String description) {
        assertEquals(description, this.log.getDescription());
    }

    @Then("This log has activity type {string}")
    public void this_log_has_activity_type(String activityTypeName) {
        assertEquals(activityTypeName, this.log.getActivityTypeName());
    }
}