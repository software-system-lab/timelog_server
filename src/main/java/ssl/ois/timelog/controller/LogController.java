// package csie.ntut.edu.tw.timelog.controller;

// import csie.ntut.edu.tw.timelog.model.Log;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import csie.ntut.edu.tw.timelog.service.LogService;

// @RestController
// @RequestMapping("/log")
// public class LogController {
//     private LogService logService;

//     @Autowired
//     LogController(LogService logService) {
//         this.logService = logService;
//     }

//     @PostMapping(value = "/new")
//     public ResponseEntity<String> newLog(@RequestBody Log log) {
//         boolean result = this.logService.newLog(log);
//         if (result) {
//             return ResponseEntity.status(HttpStatus.OK)
//                     .body(log.toString());
//         }
//         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                 .body("Message: Error");
//     }
// }
