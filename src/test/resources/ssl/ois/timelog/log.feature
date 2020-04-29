Feature: user record a time

    Scenario: Record a log
        Given My user ID is "12345678-90ab-cdef-1234-567812341234"
          And I "read design pattern" from "2020/04/10 10:00" to "2020/04/10 12:00", the detail is "I learned Composite Pattern."
         When I record the activity to the Timelog
         Then the system should have the log record
