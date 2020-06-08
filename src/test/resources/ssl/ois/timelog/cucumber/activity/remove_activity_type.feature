Feature: User can remove an activity

    Scenario: Remove an activity type
        Given My user ID is "12345678-90ab-cdef-1234-567812341234"
          And I have already had "Design Pattern" in my activity type list
         When I want to remove it from my activity type list
         Then I should get the removed activity type name
          And "Design Pattern" is not in my activity type list