package utils;

import io.qameta.allure.internal.shadowed.jackson.databind.JsonNode;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import static utils.Constants.ENVIRONMENT_NAME;

public class JsonDataProviderMapper {

    private static JsonNode jsonNode;

    public JsonDataProviderMapper(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonNode = mapper.readTree(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data JSON: " + filePath, e);
        }
    }

    public String getValueOf(String key1, String key2) {
        if (jsonNode == null) throw new RuntimeException("JSON file not loaded.");
        JsonNode valueNode = jsonNode.path(key1).path(key2).path(ENVIRONMENT_NAME);
        if (valueNode.isMissingNode()) {
            throw new RuntimeException("Key '" + key2 + "' not found under '" + key1 + "' for env '" + ENVIRONMENT_NAME + "'");
        }
        return valueNode.asText();
    }
}
