package ssl.ois.timelog.cucumber.activity;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ssl.ois.timelog.adapter.repository.memory.MemoryActivityTypeListRepository;
import ssl.ois.timelog.cucumber.common.UserStepDefinition;
import ssl.ois.timelog.model.activity.type.ActivityType;
import ssl.ois.timelog.model.activity.type.ActivityTypeList;
import ssl.ois.timelog.service.activity.type.remove.*;
import ssl.ois.timelog.service.repository.ActivityTypeListRepository;

import static org.junit.Assert.*;

public class RemoveActivityTypeStepDefinition {
    private RemoveActivityTypeUseCaseOutput removeActivityTypeUseCaseOutput;
    private ActivityTypeListRepository activityTypeListRepository;
    private UserStepDefinition userStepDefinition;
    private String activityTypeName;

    public RemoveActivityTypeStepDefinition(UserStepDefinition userStepDefinition) {
        this.userStepDefinition = userStepDefinition;
    }

    @Given("I have already had {string} in my activity type list")
    public void i_have_already_had_in_my_activity_type_list(String activityTypeName) {
        this.activityTypeName = activityTypeName;

        ActivityTypeList activityTypeList = new ActivityTypeList(this.userStepDefinition.getUserID());
        activityTypeList.newType(this.activityTypeName);

        this.activityTypeListRepository = new MemoryActivityTypeListRepository();
        this.activityTypeListRepository.save(activityTypeList);
    }

    @When("I want to remove it from my activity type list")
    public void i_want_to_remove_it_from_my_activity_type_list() {
        RemoveActivityTypeUseCase removeActivityTypeUseCase = new RemoveActivityTypeUseCase(this.activityTypeListRepository);
        RemoveActivityTypeUseCaseInput removeActivityTypeUseCaseInput = new RemoveActivityTypeUseCaseInput();
        this.removeActivityTypeUseCaseOutput = new RemoveActivityTypeUseCaseOutput();
        
        removeActivityTypeUseCaseInput.setActivityTypeName(this.activityTypeName);
        removeActivityTypeUseCaseInput.setUserID(this.userStepDefinition.getUserID());

        removeActivityTypeUseCase.execute(removeActivityTypeUseCaseInput, this.removeActivityTypeUseCaseOutput);
    }

    @Then("I should get the removed activity type name")
    public void i_should_get_the_removed_activity_type_name() {
        assertEquals(this.activityTypeName, this.removeActivityTypeUseCaseOutput.getActivityTypeName());
    }

    @Then("{string} is not in my activity type list")
    public void is_not_in_my_activity_type_list(String activityTypeName) {
         for(ActivityType activityType : this.activityTypeListRepository.findByUserID(this.userStepDefinition.getUserID()).getTypeList()) {
             if(activityType.getName().equals(activityTypeName)) {
                 fail("Activity Type is not removed from the repository");
             }
         }
    }
}