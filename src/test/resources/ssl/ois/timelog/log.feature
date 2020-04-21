Feature: RecordTime

    Scenario: Record a log
        Given My user ID is "u1234"
            And I "do reading" from "2020/04/10 10:00" to "2020/04/10 12:00", the detail is "learn to write cucumber test"
        When I record the activity
        Then the system should have the log record