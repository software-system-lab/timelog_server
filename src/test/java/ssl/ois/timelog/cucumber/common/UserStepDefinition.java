package ssl.ois.timelog.cucumber.common;

import io.cucumber.java.en.Given;

public class UserStepDefinition {
    private String userID;
    private String userName;
    
    @Given("My user ID is {string}")
    public void my_user_ID_is(String userID) {
        this.userID = userID;
    }

    @Given("My name is {string}")
    public void my_name_is(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }
}
