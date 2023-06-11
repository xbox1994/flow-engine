package com.wty.flowengine.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wty.flowengine.rest.demo.test.model.Process;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class ProcessJsonConverterTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode readJsonFile(String filename) throws Exception {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        return objectMapper.readTree(inputStream);
    }

    @Test
    public void testConvertJsonToProcess() throws Exception {
        JsonNode jsonNode = readJsonFile("process.json");
        Process process = ProcessConverter.convertToProcess(jsonNode);
        System.out.println(process);
        // todo convert to json
    }

}