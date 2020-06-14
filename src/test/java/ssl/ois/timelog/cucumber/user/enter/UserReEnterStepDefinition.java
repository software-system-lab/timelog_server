package ssl.ois.timelog.cucumber.user.enter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import ssl.ois.timelog.adapter.repository.memory.MemoryActivityTypeListRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryLogRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryUserRepository;
import ssl.ois.timelog.cucumber.common.UserStepDefinition;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.log.add.AddLogUseCase;
import ssl.ois.timelog.service.log.add.AddLogUseCaseInput;
import ssl.ois.timelog.service.log.add.AddLogUseCaseOutput;
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
    private List<Log> logList;

    public UserReEnterStepDefinition(UserStepDefinition userStepDefinition) {
        this.userStepDefinition = userStepDefinition;
    }

    @Before
    public void setup() {
        this.userRepository = new MemoryUserRepository();
        this.activityTypeListRepository = new MemoryActivityTypeListRepository();
        this.logRepository = new MemoryLogRepository();
    }

    @Given("I have added new activity type {string} before")
    public void i_have_added_new_activity_type_before(String typeName) {
        EnterUseCase enterUseCase = new EnterUseCase(this.userRepository, this.activityTypeListRepository, this.logRepository);
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
        AddLogUseCase addLogUseCase = new AddLogUseCase(logRepository);
        AddLogUseCaseInput addLogUseCaseInput = new AddLogUseCaseInput();
        AddLogUseCaseOutput addLogUseCaseOutput = new AddLogUseCaseOutput();

        addLogUseCaseInput.setUserID(this.userStepDefinition.getUserID());
        addLogUseCaseInput.setTitle(title);
        addLogUseCaseInput.setStartTime(startTime);
        addLogUseCaseInput.setEndTime(endTime);
        addLogUseCaseInput.setDescription(description);
        addLogUseCaseInput.setActivityTypeName(activityName);

        addLogUseCase.execute(addLogUseCaseInput, addLogUseCaseOutput);
    }

    @When("I enter Timelog")
    public void i_enter_Timelog() {
        EnterUseCase enterUseCase = new EnterUseCase(this.userRepository, this.activityTypeListRepository, this.logRepository);
        EnterUseCaseInput enterUseCaseInput = new EnterUseCaseInput();
        this.enterUseCaseOutput = new EnterUseCaseOutput();

        enterUseCaseInput.setUserID(this.userStepDefinition.getUserID());
        enterUseCaseInput.setUserName(this.userStepDefinition.getUserName());

        enterUseCase.execute(enterUseCaseInput, this.enterUseCaseOutput);
    }

    @Then("I will get my activity type list and log list")
    public void i_will_get_my_activity_type_list_and_log_list() {
        this.activityTypeList = this.enterUseCaseOutput.getActivityTypeList();
        this.logList = this.enterUseCaseOutput.getLogList();
    }

    @Then("The activity type list contains {string} and {string}")
    public void the_activity_type_list_contains_and(String activityTypeName1, String activityTypeName2) {
        assertEquals(2, this.activityTypeList.size());
        Boolean containsType1 = false;
        Boolean containsType2 = false;

        for(ActivityType type: this.activityTypeList) {
            if(type.getName().equals(activityTypeName1)) {
                containsType1 = true;
            } else if(type.getName().equals(activityTypeName2)) {
                containsType2 = true;
            }
        }

        assertTrue(containsType1);
        assertTrue(containsType2);
    }

    @Then("The log list contains a log with title {string} and start time {string} and end time {string} and description {string} and activity type {string}")
    public void the_log_list_contains_a_log_with_title_and_start_time_and_end_time_and_description_and_activity_type(String title, String startTime, String endTime, String description, String activityTypeName) {
        Boolean found = false;
        for(Log log: this.logList) {
            if(log.getTitle().equals(title) && 
               log.getDateFormat().format(log.getStartTime()).equals(startTime) &&
               log.getDateFormat().format(log.getEndTime()).equals(endTime) &&
               log.getDescription().equals(description) &&
               log.getActivityTypeName().equals(activityTypeName)) {
                found = true;
                break;
            }
        }

        assertTrue(found);
    }
}