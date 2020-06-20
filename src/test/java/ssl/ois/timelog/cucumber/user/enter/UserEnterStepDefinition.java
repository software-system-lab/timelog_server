package ssl.ois.timelog.cucumber.user.enter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ssl.ois.timelog.adapter.repository.memory.MemoryLogRepository;
import ssl.ois.timelog.adapter.repository.memory.MemoryUserRepository;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.repository.log.LogRepository;
import ssl.ois.timelog.service.exception.log.SaveLogErrorException;
import ssl.ois.timelog.service.repository.user.UserRepository;
import ssl.ois.timelog.service.user.enter.EnterUseCase;
import ssl.ois.timelog.service.user.enter.EnterUseCaseInput;
import ssl.ois.timelog.service.user.enter.EnterUseCaseOutput;

public class UserEnterStepDefinition {
    private String userID;
    private UserRepository userRepository;
    private LogRepository logRepository;
    private EnterUseCaseOutput enterUseCaseOutput;
    private List<ActivityType> activityTypeList;
    private List<Log> logList;

    @Before
    public void setup() {
        this.userRepository = new MemoryUserRepository();
        this.logRepository = new MemoryLogRepository();
    }

    @Given("My user ID is {string}")
    public void my_user_ID_is(String userID) {
        this.userID = userID;
    }

    @When("I first time enter Timelog")
    public void i_first_time_enter_Timelog() {
        EnterUseCase enterUseCase = new EnterUseCase(this.userRepository, this.logRepository);
        EnterUseCaseInput enterUseCaseInput = new EnterUseCaseInput();
        enterUseCaseInput.setUserID(this.userID);
        this.enterUseCaseOutput = new EnterUseCaseOutput();
        try {
            enterUseCase.execute(enterUseCaseInput, enterUseCaseOutput);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Then("I will get my activity type list that only contains {string}")
    public void i_will_get_my_activity_type_list_that_only_contains(String activityTypeName) {
        // assertion for output of use case
        List<ActivityType> activityTypeListFromOutput = this.enterUseCaseOutput.getActivityTypeList();
        assertEquals(1, activityTypeListFromOutput.size());
        assertEquals(activityTypeName, activityTypeListFromOutput.get(0).getName());

        // verify that the activity type is actually stored
        try {
            List<ActivityType> activityTypeListFromRepo = this.userRepository.findByUserID(this.userID).getActivityTypeList();
            assertEquals(1, activityTypeListFromRepo.size());
            assertEquals(activityTypeName, activityTypeListFromRepo.get(0).getName());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Then("I will get my log list that contains nothing")
    public void i_will_get_my_log_list_that_contains_nothing() {
        List<Log> logs = this.enterUseCaseOutput.getLogList();
        assertTrue(logs.isEmpty(), "The log list is not empty");
    }

    @Given("I have entered the Timelog with user ID {string} before")
    public void i_have_entered_the_Timelog_with_user_ID_before(String userID) {
        EnterUseCase enterUseCase = new EnterUseCase(this.userRepository, this.logRepository);
        EnterUseCaseInput enterUseCaseInput = new EnterUseCaseInput();
        EnterUseCaseOutput enterUseCaseOutput = new EnterUseCaseOutput();

        this.userID = userID;

        enterUseCaseInput.setUserID(userID);

        try {
            enterUseCase.execute(enterUseCaseInput, enterUseCaseOutput);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Given("There is an activity type {string} in my activity type list")
    public void there_is_an_activity_type_in_my_activity_type_list(String activityTypeName) {
        ActivityType activityType = new ActivityType(activityTypeName, true, false);
        try {
            User user = this.userRepository.findByUserID(this.userID);
            user.addActivityType(activityType);
            this.userRepository.save(user);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Given("There is a log with title {string} and start time {string} and end time {string} and description {string} and activity type {string} in my log history")
    public void there_is_a_log_with_title_and_start_time_and_end_time_and_description_and_activity_type_in_my_log_history(String title, String startTime, String endTime, String description, String activityTypeName) {
        try {
            this.logRepository.save(
                new Log(UUID.fromString(this.userID), title, startTime, endTime, description, activityTypeName)
            );
        } catch (SaveLogErrorException e) {
            fail(e.getMessage());
        }
    }

    @When("I enter the Timelog with same user ID again")
    public void i_enter_the_Timelog_with_same_user_ID_again() {
        EnterUseCase enterUseCase = new EnterUseCase(this.userRepository, this.logRepository);
        EnterUseCaseInput enterUseCaseInput = new EnterUseCaseInput();
        this.enterUseCaseOutput = new EnterUseCaseOutput();

        enterUseCaseInput.setUserID(this.userID);

        try {
            enterUseCase.execute(enterUseCaseInput, enterUseCaseOutput);
        } catch (Exception e) {
            fail(e.getMessage());
        }
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
            SimpleDateFormat dateFormat = new SimpleDateFormat(Log.dateFormatString);
            if(log.getTitle().equals(title) &&
               dateFormat.format(log.getStartTime()).equals(startTime) &&
               dateFormat.format(log.getEndTime()).equals(endTime) &&
               log.getDescription().equals(description) &&
               log.getActivityTypeName().equals(activityTypeName)) {
                found = true;
                break;
            }
        }

        assertTrue(found);
    }
}
