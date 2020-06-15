Feature: user add activity

    Scenario: Add an activity type
        Given I log in to Timelog with user ID "12345678-90ab-cdef-1234-567812341234"
          And I have "Design Pattern" course in this semester
         When I want to add it to my activity type list
         Then "Design Pattern" is in my activity type list