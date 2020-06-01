package ssl.ois.timelog.cucumber.activity;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ssl.ois.timelog.service.activity.type.add.AddActivityTypeUseCaseInput;

public class AddActivityTypeStepDefinition {
    private AddActivityTypeUseCaseInput addActivityTypeUseCaseInput;

    @Given("I have {string} course in this semester")
    public void i_have_course_in_this_semester(String activityTypeName) {
        this.addActivityTypeUseCaseInput = new AddActivityTypeUseCaseInput();
        this.addActivityTypeUseCaseInput.setActivityTypeName(activityTypeName);
    }

    @When("I want to add it to my activity type list")
    public void i_want_to_add_it_to_my_activity_type_list() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("{string} is in my activity type list")
    public void is_in_my_activity_type_list(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
