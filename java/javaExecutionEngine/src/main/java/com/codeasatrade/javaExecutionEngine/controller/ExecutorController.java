package com.codeasatrade.javaExecutionEngine.controller;

import com.codeasatrade.javaExecutionEngine.service.ExecutorService;
import com.codeasatrade.javaExecutionEngine.service.SingleProgramUserSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/execute")
public class ExecutorController {

    private final ExecutorService executorService;

    ExecutorController(ExecutorService executorService){
        this.executorService = executorService;
    }
    private static final Logger log = LoggerFactory.getLogger(ExecutorController.class);


    @PostMapping("/solution")
    public ResponseEntity<String> executeSingleFileProgram(@RequestBody SingleProgramUserSolution userCode){
//        public ResponseEntity<String> executeSingleFileProgram(@RequestBody String userCode){
//        ExecutionResponse response = new ExecutionResponse();
//        response.setStatus("success");
//        response.setOutput("Hello, World!");
//        response.setExecutionTime(0.05); // Example execution time in seconds
//
//        return ResponseEntity.ok(response);
//        return ResponseEntity.ok(executorService.executeSingleProgram(userCode.code()));
//        log.info("Received userCode{}", userCode.code());
                log.info("Received userCode{}", userCode);
        return new ResponseEntity<>(executorService.executeSingleProgram(userCode), HttpStatus.OK);
//        return new ResponseEntity<>(executorService.executeSingleProgram(userCode), HttpStatus.OK);
    }
}
