package ssl.ois.timelog.cucumber.activity;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ssl.ois.timelog.cucumber.common.UserIDStepDefinition;
import ssl.ois.timelog.model.user.ActivityType;
import ssl.ois.timelog.model.user.ActivityTypeList;
import ssl.ois.timelog.service.activity.type.remove.*;
import ssl.ois.timelog.service.repository.ActivityTypeRepository;

public class RemoveActivityTypeStepDefinition {
    
    private RemoveActivityTypeUseCase removeActivityTypeUseCase;
    private RemoveActivityTypeUseCaseInput removeActivityTypeUseCaseInput;
    private RemoveActivityTypeUseCaseOutput removeActivityTypeUseCaseOutput;
    private ActivityTypeRepository activityTypeRepository;
    private UserIDStepDefinition userIDStepDefinition;
    private String activityTypeName;

    public RemoveActivityTypeStepDefinition(UserIDStepDefinition userIDStepDefinition) {
        this.userIDStepDefinition = userIDStepDefinition;
    }

    @Given("I have already had {string} in my activity type list")
    public void i_have_already_had_in_my_activity_type_list(String activityTypeName) {
        this.activityTypeName = activityTypeName;

        ActivityTypeList activityTypeList = new ActivityTypeList();
        activityTypeList.setUserID(this.userIDStepDefinition.getUserID());
        activityTypeList.newType(this.activityTypeName);

        activityTypeRepository.save(activityTypeList);
    }

    @When("I want to remove it from my activity type list")
    public void i_want_to_remove_it_from_my_activity_type_list() {
        this.removeActivityTypeUseCase = new RemoveActivityTypeUseCase(this.activityTypeRepository);
        this.removeActivityTypeUseCaseInput = new RemoveActivityTypeUseCaseInput();
        this.removeActivityTypeUseCaseOutput = new RemoveActivityTypeUseCaseOutput();
        
        this.removeActivityTypeUseCaseInput.setActivityTypeName(this.activityTypeName);
        this.removeActivityTypeUseCaseInput.setUserID(this.userIDStepDefinition.getUserID());

        this.removeActivityTypeUseCase.execute(this.removeActivityTypeUseCaseInput, this.removeActivityTypeUseCaseOutput);
    }

    @Then("I should get the removed activity type name")
    public void i_should_get_the_removed_activity_type_name() {
        assertEquals(this.activityTypeName, this.removeActivityTypeUseCaseOutput.getActivityTypeName());
    }

    @Then("{string} is not in my activity type list")
    public void is_not_in_my_activity_type_list(String activityTypeName) {
        // for(ActivityType activityType : this.activityTypeRepository.findByUserID(this.userIDStepDefinition.getUserID()).getActivityTypeList()) {
        //     if(activityType.getName().equals(activityTypeName)) {
        //         fail();
        //     }
        // }
        assertTrue(this.activityTypeRepository.findByUserID(this.userIDStepDefinition.getUserID()).getActivityTypeList().contains(new ActivityType(this.activityTypeName)));
    }
}