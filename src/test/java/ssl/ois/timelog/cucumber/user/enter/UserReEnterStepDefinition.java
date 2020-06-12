package ssl.ois.timelog.cucumber.user.enter;

import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import ssl.ois.timelog.adapter.repository.memory.MemoryActivityTypeListRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryLogRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryUserRepository;
import ssl.ois.timelog.cucumber.common.UserStepDefinition;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.service.log.AddLogUseCase;
import ssl.ois.timelog.service.log.AddLogUseCaseInput;
import ssl.ois.timelog.service.log.AddLogUseCaseOutput;
import ssl.ois.timelog.service.log.LogRepository;
import ssl.ois.timelog.service.repository.ActivityTypeListRepository;
import ssl.ois.timelog.service.repository.UserRepository;
import ssl.ois.timelog.service.user.enter.EnterUseCase;
import ssl.ois.timelog.service.user.enter.EnterUseCaseInput;
import ssl.ois.timelog.service.user.enter.EnterUseCaseOutput;


public class UserReEnterStepDefinition {
    private UserStepDefinition userStepDefinition;
    private UserRepository userRepository;
    private ActivityTypeListRepository activityTypeListRepository;
    private LogRepository logRepository;
    private EnterUseCaseOutput enterUseCaseOutput;
    private List<ActivityType> activityTypeList;

    public UserReEnterStepDefinition(UserStepDefinition userStepDefinition) {
        this.userStepDefinition = userStepDefinition;
    }

    @Given("I have added new activity type {string} before")
    public void i_have_added_new_activity_type_before(String typeName) {
        this.userRepository = new MemoryUserRepository();
        this.activityTypeListRepository = new MemoryActivityTypeListRepository();
        EnterUseCase enterUseCase = new EnterUseCase(this.userRepository, this.activityTypeListRepository);
        EnterUseCaseInput enterUseCaseInput = new EnterUseCaseInput();
        EnterUseCaseOutput enterUseCaseOutput = new EnterUseCaseOutput();
        enterUseCaseInput.setUserID(this.userStepDefinition.getUserID());
        enterUseCaseInput.setUserName(this.userStepDefinition.getUserName());
        enterUseCase.execute(enterUseCaseInput, enterUseCaseOutput);

        ActivityTypeList activityTypeList = this.activityTypeListRepository.findByUserID(this.userStepDefinition.getUserID());
        activityTypeList.newType(typeName);
        this.activityTypeListRepository.update(activityTypeList);
    }

    @Given("I have added a log with title {string} and start time {string} and end time {string} and description {string} and activity type {string}")
    public void i_have_added_a_log_with_title_and_start_time_and_end_time_and_description_and_activity_type(String title, String startTime, String endTime, String description, String activityName) {
        logRepository = new MemoryLogRepository();
        AddLogUseCase addLogUseCase = new AddLogUseCase(logRepository);
        AddLogUseCaseInput addLogUseCaseInput = new AddLogUseCaseInput();
        AddLogUseCaseOutput addLogUseCaseOutput = new AddLogUseCaseOutput();
        addLogUseCaseInput.setUserID(this.userStepDefinition.getUserID());
        addLogUseCaseInput.setTitle(title);
        addLogUseCaseInput.setStartTime(startTime);
        addLogUseCaseInput.setEndTime(endTime);
        addLogUseCaseInput.setDescription(description);
        addLogUseCaseInput.setActivityName(activityName);
        addLogUseCase.execute(addLogUseCaseInput, addLogUseCaseOutput);
    }

    @When("I enter Timelog")
    public void i_enter_Timelog() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I will get my activity type list and log list")
    public void i_will_get_my_activity_type_list_and_log_list() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("The activity type list contains {string} and {string}")
    public void the_activity_type_list_contains_and(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("The log list contains a log with title {string} and start time {string} and end time {string} and description {string} and activity type {string}")
    public void the_log_list_contains_a_log_with_title_and_start_time_and_end_time_and_description_and_activity_type(String string, String string2, String string3, String string4, String string5) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}