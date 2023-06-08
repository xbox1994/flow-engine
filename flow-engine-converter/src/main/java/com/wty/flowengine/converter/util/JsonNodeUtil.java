package com.wty.flowengine.converter.util;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonNodeUtil {

    public static String getString(JsonNode jsonNode, String field) {
        JsonNode fieldNode = jsonNode.get(field);
        return fieldNode != null ? fieldNode.asText() : null;
    }

    public static boolean getBoolean(JsonNode jsonNode, String field) {
        JsonNode fieldNode = jsonNode.get(field);
        return fieldNode != null && fieldNode.asBoolean();
    }
}
