Feature: user record a time

    Scenario: Record a log
        Given My user ID is "12345678-90ab-cdef-1234-567812341234"
          And I "read design pattern" from "2020/04/10 10:00" to "2020/04/10 12:00", the detail is "I learned Composite Pattern."
          And No activity type has been selected
         When I record the activity to Timelog
         Then I get the ID of this log
          And I can get the complete log with this log ID
          And This log has title "read design pattern"
          And This log has start time with "2020/04/10 10:00"
          And This log has end time with "2020/04/10 12:00"
          And This log has detail "I learned Composite Pattern."
          And This log has activity type "Others"
