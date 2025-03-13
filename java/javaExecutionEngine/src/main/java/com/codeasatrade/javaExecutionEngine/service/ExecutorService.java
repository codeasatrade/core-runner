package com.codeasatrade.javaExecutionEngine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
public class ExecutorService {

    @Autowired
    private Environment environment;
    private static final Logger log = LoggerFactory.getLogger(ExecutorService.class);

    public String executeSingleProgram(SingleProgramUserSolution userCode) {
        var result = "error";
        final String tempUserProgram = "Answer.java";
        final String tempUserProgramClass = "Answer";
        final String testerProgram = "Tester.java";
        final String testerProgramClass = "Tester";

        log.info("Execution single program");
        String dirPath = Objects.equals(environment.getProperty("spring.profiles.active"), "prod") ? "/app/java-projects" : "../java-projects";
        //1. Creating the program file
        createUserProgramFile(userCode.code(), dirPath, tempUserProgram);

        //2. Execute the program file
        List<List<String>> lsCmds = List.of(
                List.of("ls"),
                List.of("javac", tempUserProgram),
                List.of("javac", testerProgram)
                ,List.of("ls")
                ,List.of("java", testerProgramClass)
//                ,List.of("java", tempUserProgramClass)
                ,List.of("ls")
                );

        for (List<String> cmd : lsCmds) {
            String op = execute(dirPath, cmd);
            if (cmd.get(0).equals("java")) result = op;
        }



        //clean up
        List<List<String>> cleanUpCmds = List.of(List.of("rm", tempUserProgram), List.of("rm", tempUserProgramClass + ".class"));
        cleanUpCmds.forEach(cmd -> execute(dirPath, cmd));

        return result;
    }

    private void createUserProgramFile(String userCode, String dirPath, String fileName) {

        String filePath = dirPath + "/"+fileName;
        try {
            Path path = Paths.get(filePath);
            Files.writeString(path, userCode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String execute(String pathName, List<String> command) {
        ProcessBuilder pb = new ProcessBuilder(command);
        String line = "";

        pb.directory(new File(pathName));

        try {
            Process process = pb.start();
            int exitCode = process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(exitCode == 0? process.getInputStream() : process.getErrorStream()));
            while (true) {
                String sl = reader.readLine();
                if (sl == null) break;
                line += sl + "\n";
//                System.out.println(line);
            }

//            System.out.println("exitCode" + exitCode);
            process.destroy();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Program did exited with error " + e);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {

        }
        return line;
    }
}
