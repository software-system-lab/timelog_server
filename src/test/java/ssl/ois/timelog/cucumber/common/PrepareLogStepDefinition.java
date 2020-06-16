package ssl.ois.timelog.cucumber.common;

import io.cucumber.java.en.Given;
import ssl.ois.timelog.service.log.LogRepository;
import ssl.ois.timelog.service.log.add.*;

public class PrepareLogStepDefinition {

    private LogRepository logRepository;
    private UserStepDefinition userStepDefinition;
    private String logID;
    private String userID;

    @Given("I have added a log with title {string} and start time {string} and end time {string} and description {string} and activity type {string} before")
    public void i_have_added_a_log_with_title_and_start_time_and_end_time_and_description_and_activity_type_before(String title, String startTime, String endTime, String description, String activityTypeName) {
        AddLogUseCase addLogUseCase = new AddLogUseCase(this.logRepository);
        AddLogUseCaseInput addLogUseCaseInput = new AddLogUseCaseInput();
        AddLogUseCaseOutput addLogUseCaseOutput = new AddLogUseCaseOutput();
        addLogUseCaseInput.setUserID(this.userID != null ? this.userID : this.userStepDefinition.getUserID());
        addLogUseCaseInput.setTitle(title);
        addLogUseCaseInput.setStartTime(startTime);
        addLogUseCaseInput.setEndTime(endTime);
        addLogUseCaseInput.setDescription(description);
        addLogUseCaseInput.setActivityTypeName(activityTypeName);

        addLogUseCase.execute(addLogUseCaseInput, addLogUseCaseOutput);

        this.logID = addLogUseCaseOutput.getLogID();
    }

    public void setLogRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void setUserStepDefinition(UserStepDefinition userStepDefinition) {
        this.userStepDefinition = userStepDefinition;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLogID() {
        return this.logID;
    }
}