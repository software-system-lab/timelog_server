Feature: user record a time

    Scenario: Record a log
        Given My user ID is "12345678-90ab-cdef-1234-567812341234"
          And I "read design pattern" from "2020/04/10 10:00" to "2020/04/10 12:00", the detail is "I learned Composite Pattern."
          And No activity type has been selected
         When I record the activity to the Timelog
         Then I will have a log with title "read design pattern"
          And The log has the start time with "2020/04/10 10:00"
          And The log has the end time with "2020/04/10 12:00"
          And The log has the detail "I learned Composite Pattern"
          And The log has the activity type "others"
