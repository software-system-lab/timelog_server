Feature: User can remove an activity

    Scenario: Remove an activity type
        Given I log in to Timelog with user ID "12345678-90ab-cdef-1234-567812341234"
          And I have already had "Design Pattern" in my activity type list
         When I remove it from my activity type list
         Then "Design Pattern" is not in my activity type list