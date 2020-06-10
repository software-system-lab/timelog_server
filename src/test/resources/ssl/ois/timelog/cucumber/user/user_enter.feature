Feature: user enter Timelog

    Scenario: A user first time enter Timelog
        Given My user ID is "12345678-90ab-cdef-1234-567812341234"
          And My name is "Patrick"
         When I first time enter Timelog
         Then I will get my activity type list
          And The activity type list only contains "Others"
          And I should have an activity type list in the database.

    # Scenario: A user first time enter Timelog
    #     Given My user ID is "12345678-90ab-cdef-1234-567812341234"
    #       And My username is "Patrick"
    #       And I haven't enter Timelog before
    #      When I enter Timelog with my user account
    #      Then I will get my activity type list
    #       And The activity type list only contains "Others"
    #       And I should have an activity type list in the database