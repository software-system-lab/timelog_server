# Feature: user remove a log

#     Scenario: Remove a log
#         Given I "read design pattern" from "2020/04/10 10:00" to "2020/04/10 12:00", the description is "I learned Composite Pattern."
#           And My user ID is "12345678-90ab-cdef-1234-567812341234"
#           And Activity type "Others" is selected
#          When I record the activity to Timelog
#          Then A new log is added
#           And This log has title "read design pattern"
#           And This log has start time with "2020/04/10 10:00"
#           And This log has end time with "2020/04/10 12:00"
#           And This log has description "I learned Composite Pattern."
#           And This log has activity type "Others"

#     Scenario: Remove a log
#         Given My uer ID is "12345678-90ab-cdef-1234-567812341234"
#           And I have added a log with title "read design pattern" and start time "2020/04/10 10:00" and end time "2020/04/10 12:00" and description "composite pattern" and activity type "Others"
#          When I remove the log from my log history
#          Then The log should be removed from my log history

