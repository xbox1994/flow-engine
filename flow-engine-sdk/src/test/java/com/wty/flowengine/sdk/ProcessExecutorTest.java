package com.wty.flowengine.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wty.flowengine.rest.demo.test.model.Process;
import com.wty.flowengine.converter.ProcessConverter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = ProcessExecutorTest.class)
public class ProcessExecutorTest {

    @Configuration
    static class TestConfiguration {

    }

    ObjectMapper objectMapper = new ObjectMapper();

    ProcessExecutor processExecutor = new ProcessExecutorConfiguration().build();

    @Test
    void testStart() throws IOException {
        Process process = readProcess("processes/process.json");
        processExecutor.start(process, null);
    }

    public Process readProcess(String filename) throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        JsonNode jsonNode = objectMapper.readTree(inputStream);
        return ProcessConverter.convertToProcess(jsonNode);
    }

    @Test
    void testStart2() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("processes/updateDoc.json");
        JsonNode jsonNode = objectMapper.readTree(inputStream);
        Process process = ProcessConverter.convertToProcess(jsonNode);
        Map<String, Object> variables = new HashMap<>();
        variables.put("role", "admin");
        Map<String, Object> data = new HashMap<>();
        data.put("name", "测试文件.pdf");
        Map<String, Object> session = new HashMap<>();
        session.put("employeeNo", "100001");
        variables.put("data", data);
        variables.put("session", session);
        processExecutor.start(process, variables);
    }

}