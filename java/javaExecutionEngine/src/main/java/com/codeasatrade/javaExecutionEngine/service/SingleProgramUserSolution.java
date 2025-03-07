package com.codeasatrade.javaExecutionEngine.service;

import java.util.List;
import java.util.Map;

public record SingleProgramUserSolution(String code, Map<String, List<String>> testCases) {
}
