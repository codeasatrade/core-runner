package com.codeasatrade.javaExecutionEngine.controller;

import com.codeasatrade.javaExecutionEngine.service.ExecutorService;
import com.codeasatrade.javaExecutionEngine.service.SingleProgramUserSolution;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/execute/v1")
public class ExecutorController {

    private final ExecutorService executorService;

    ExecutorController(ExecutorService executorService){
        this.executorService = executorService;
    }

    @PostMapping("/solution")
    public ResponseEntity<Object> executeSingleFileProgram(@RequestBody SingleProgramUserSolution userCode){
        return ResponseEntity.ok(executorService.executeSingleProgram(userCode.code()));
    }
}
