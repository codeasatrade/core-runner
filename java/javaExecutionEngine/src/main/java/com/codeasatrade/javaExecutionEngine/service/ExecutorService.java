package com.codeasatrade.javaExecutionEngine.service;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class ExecutorService {

    @Autowired
    private Environment environment;


    public String executeSingleProgram(String userCode) {
        var result = "";
        final String tempUserProgram = "Solution.java";
        final String tempUserProgramClass = "Solution";
        String dirPath = Objects.equals(environment.getProperty("spring.profiles.active"), "prod") ? "/app/java-projects" : "../java-projects";
        createUserProgramFile(userCode, dirPath, tempUserProgram);

        List<List<String>> lsCmds = List.of(
                List.of("ls"), List.of("javac", tempUserProgram), List.of("ls"), List.of("java", tempUserProgramClass), List.of("ls"));

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
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while (true) {
                String sl = reader.readLine();
                if (sl == null) break;
                line += sl + "\n";
//                System.out.println(line);
            }
            int exitCode = process.waitFor();
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
