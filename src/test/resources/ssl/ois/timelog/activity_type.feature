Feature: user manage activity

    Scenario: Add an activity type
        Given I have "Design Pattern" course in this semester
          And My user ID is "12345678-90ab-cdef-1234-567812341234"
         When I want to add it as my own activity type
         Then "Design Pattern" is in my activity type list