package ssl.ois.timelog.adapter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssl.ois.timelog.service.log.*;

@RestController
@RequestMapping("/api/log")
public class LogRestAdapter {
    @Autowired
    LogRecord logRecord;

    @Autowired
    GetLogById getLogById;

    @PostMapping(value = "/record")
    public ResponseEntity<ResponseOutput> newLog(@RequestBody RecordInput input) {
        RecordOutput output = new RecordOutput();
        ResponseOutput responseOutput = new ResponseOutput();
        this.logRecord.execute(input, output);
        responseOutput.setLogID(output.getLogID());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(responseOutput);
    }

    @PostMapping(value = "/get/id")
    public ResponseEntity<GetByIdOutput> getLogByTitle(@RequestBody GetByIdInput input) {
        GetByIdOutput output = new GetByIdOutput();
        this.getLogById.execute(input, output);
        return ResponseEntity.status(HttpStatus.OK).body(output);
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
