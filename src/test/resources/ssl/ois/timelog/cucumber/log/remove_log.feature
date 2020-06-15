Feature: user remove a log

    Scenario: Remove a log
        Given I log in to Timelog with user ID "12345678-90ab-cdef-1234-567812341234"
          And I have added a log with title "read design pattern" and start time "2020/04/10 10:00" and end time "2020/04/10 12:00" and description "composite pattern" and activity type "Others" before
         When I remove the log from my log history
         Then The log should be removed from my log history

