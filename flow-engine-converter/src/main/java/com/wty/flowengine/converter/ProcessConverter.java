package com.wty.flowengine.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wty.flow.model.Process;
import com.wty.flowengine.converter.exception.ConvertException;
import com.wty.flowengine.converter.factory.ProcessFactory;

import java.io.IOException;
import java.io.InputStream;

public class ProcessConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static Process convertToProcess(JsonNode jsonNode) {
        return ProcessFactory.instance.createProcess(jsonNode);
    }
    public static Process convertToProcess(InputStream inputStream) {
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(inputStream);
        } catch (IOException e) {
            throw new ConvertException("Read inputStream failed", e);
        }
        return convertToProcess(jsonNode);
    }

}
