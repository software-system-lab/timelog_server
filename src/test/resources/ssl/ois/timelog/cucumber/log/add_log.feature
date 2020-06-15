Feature: user add a log

    Scenario: Add a log
        Given I log in to Timelog with user ID "12345678-90ab-cdef-1234-567812341234"
          And I "read design pattern" from "2020/04/10 10:00" to "2020/04/10 12:00", the description is "I learned Composite Pattern."
         When I add a log with activity type "Others" to Timelog
         Then The log should be stored in the Timelog
          And The log has title "read design pattern"
          And The log has start time with "2020/04/10 10:00"
          And The log has end time with "2020/04/10 12:00"
          And The log has description "I learned Composite Pattern."
          And The log has activity type "Others"

