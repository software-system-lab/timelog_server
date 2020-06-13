Feature: user add a log

    Scenario: Add a log
        Given I "read design pattern" from "2020/04/10 10:00" to "2020/04/10 12:00", the description is "I learned Composite Pattern."
          And My user ID is "12345678-90ab-cdef-1234-567812341234"
          And Activity type "Others" is selected
         When I record the activity to Timelog
         Then A new log is added
          And This log has title "read design pattern"
          And This log has start time with "2020/04/10 10:00"
          And This log has end time with "2020/04/10 12:00"
          And This log has description "I learned Composite Pattern."
          And This log has activity type "Others"

