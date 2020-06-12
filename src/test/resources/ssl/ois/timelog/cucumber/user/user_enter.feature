Feature: user enter Timelog

    Scenario: A user first time enter Timelog
        Given My user ID is "12345678-90ab-cdef-1234-567812341234"
          And My name is "Patrick"
         When I first time enter Timelog
         Then I will get my activity type list
          And The activity type list only contains "Others"

    Scenario: A user re-enter Timelog
        Given My user ID is "12345678-90ab-cdef-1234-567812341234"
          And My name is "Patrick"
          And I have added new activity type "OIS" before
          And I have added a log with title "read design pattern" and start time "2020/04/10 10:00" and end time "2020/04/10 12:00" and description "composite pattern" and activity type "Others"
         When I enter Timelog
         Then I will get my activity type list and log list
          And The activity type list contains "Others" and "OIS"
          And The log list contains a log with title "read design pattern" and start time "2020/04/10 10:00" and end time "2020/04/10 12:00" and description "composite pattern" and activity type "Others"

