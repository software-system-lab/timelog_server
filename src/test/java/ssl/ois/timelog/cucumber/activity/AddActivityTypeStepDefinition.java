package ssl.ois.timelog.cucumber.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ssl.ois.timelog.adapter.repository.memory.MemoryActivityTypeListRepository;
import ssl.ois.timelog.cucumber.common.UserStepDefinition;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.service.activity.type.add.AddActivityTypeUseCase;
import ssl.ois.timelog.service.activity.type.add.AddActivityTypeUseCaseInput;
import ssl.ois.timelog.service.activity.type.add.AddActivityTypeUseCaseOutput;
import ssl.ois.timelog.service.repository.ActivityTypeListRepository;

public class AddActivityTypeStepDefinition {
    private AddActivityTypeUseCaseInput addActivityTypeUseCaseInput;
    private AddActivityTypeUseCaseOutput addActivityTypeUseCaseOutput;
    private ActivityTypeListRepository activityTypeListRepository;
    private UserStepDefinition userStepDefinition;

    public AddActivityTypeStepDefinition(UserStepDefinition userStepDefinition) {
        this.userStepDefinition = userStepDefinition;
    }

    @Given("I have {string} course in this semester")
    public void i_have_course_in_this_semester(String activityTypeName) {
        ActivityTypeList activityTypeList = new ActivityTypeList(this.userStepDefinition.getUserID());
        this.activityTypeListRepository = new MemoryActivityTypeListRepository();
        this.activityTypeListRepository.save(activityTypeList);

        this.addActivityTypeUseCaseInput = new AddActivityTypeUseCaseInput();
        this.addActivityTypeUseCaseInput.setActivityTypeName(activityTypeName);
    }

    @When("I want to add it to my activity type list")
    public void i_want_to_add_it_to_my_activity_type_list() {
        this.addActivityTypeUseCaseInput.setUserID(this.userStepDefinition.getUserID());
        this.addActivityTypeUseCaseOutput = new AddActivityTypeUseCaseOutput();
        AddActivityTypeUseCase addActivityTypeUseCase = new AddActivityTypeUseCase(activityTypeListRepository);
        addActivityTypeUseCase.execute(addActivityTypeUseCaseInput, addActivityTypeUseCaseOutput);
    }

    @Then("{string} is in my activity type list")
    public void is_in_my_activity_type_list(String activityTypeName) {
        assertEquals(activityTypeName, this.addActivityTypeUseCaseOutput.getActivityTypeName());
        Boolean found = false;
        for(ActivityType activityType : this.activityTypeListRepository.findByUserID(this.userStepDefinition.getUserID()).getTypeList()) {
            if(activityType.getName().equals(activityTypeName)) {
                found = true;
            }
        }
        assertTrue(found);
    }
}
