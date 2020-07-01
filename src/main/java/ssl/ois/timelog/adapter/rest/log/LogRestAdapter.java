package ssl.ois.timelog.adapter.rest.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssl.ois.timelog.service.exception.log.GetLogErrorException;
import ssl.ois.timelog.service.exception.log.SaveLogErrorException;
import ssl.ois.timelog.service.log.add.*;
import ssl.ois.timelog.service.log.get.*;

@RestController
@RequestMapping("/api/log")
public class LogRestAdapter {
    @Autowired
    AddLogUseCase addLogUseCase;

    @Autowired
    GetLogByIdUseCase getLogById;

    @PostMapping(value = "/record")
    public ResponseEntity<ResponseOutput> newLog(@RequestBody NewLogRequestInput requestBody) {
        AddLogUseCaseInput input = new AddLogUseCaseInput();
        AddLogUseCaseOutput output = new AddLogUseCaseOutput();

        input.setUserID(requestBody.getUserID());
        input.setTitle(requestBody.getTitle());
        input.setStartTime(requestBody.getStartTime());
        input.setEndTime(requestBody.getEndTime());
        input.setDescription(requestBody.getDescription());
        input.setActivityTypeName(requestBody.getActivityTypeName());

        try {
            this.addLogUseCase.execute(input, output);
        } catch (SaveLogErrorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseOutput());
        }

        ResponseOutput responseOutput = new ResponseOutput();
        responseOutput.setLogID(output.getLogID());
        return ResponseEntity.status(HttpStatus.OK).body(responseOutput);
    }

    @PostMapping(value = "/get/id")
    public ResponseEntity<GetLogByIdUseCaseOutput> getLogByTitle(@RequestBody GetLogByIdUseCaseInput input) {
        GetLogByIdUseCaseOutput output = new GetLogByIdUseCaseOutput();
        try {
            this.getLogById.execute(input, output);
        } catch (GetLogErrorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(output);
        }
        return ResponseEntity.status(HttpStatus.OK).body(output);
    }

    public static class NewLogRequestInput {
        private String userID;
        private String title;
        private String startTime;
        private String endTime;
        private String description;
        private String activityTypeName;

        public NewLogRequestInput(String userID, String title, String startTime, String endTime, String description, String activityTypeName) {
            this.userID = userID;
            this.title = title;
            this.startTime = startTime;
            this.endTime = endTime;
            this.description = description;
            this.activityTypeName = activityTypeName;
        }

        public String getUserID() {
            return userID;
        }

        public String getTitle() {
            return title;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getDescription() {
            return description;
        }

        public String getActivityTypeName() {
            return activityTypeName;
        }
    }

    private class ResponseOutput {
        
        private String logID;

        public String getLogID() {
            return logID;
        }

        public void setLogID(String logID) {
            this.logID = logID;
        }
    }

}
