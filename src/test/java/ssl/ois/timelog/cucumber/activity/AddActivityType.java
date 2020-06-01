package ssl.ois.timelog.cucumber.activity;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddActivityType {
    private AddActivityTypeRequestBody addActivityTypeRequestBody;

    @Given("I have {string} course in this semester")
    public void i_have_course_in_this_semester(String userID) {
//        this.addActivityTypeRequestBody.setUserID(userID);
        throw new io.cucumber.java.PendingException();
    }

    @When("I want to add it as my own activity type")
    public void i_want_to_add_it_as_my_own_activity_type() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("{string} is in my activity type list")
    public void is_in_my_activity_type_list(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    private class AddActivityTypeRequestBody {
        private String userID;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }
    }
}
