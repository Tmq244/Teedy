package com.sismics.util;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

/**
 * JSON utilities.
 * 
 * @author bgamard
 */
public class JsonUtil {
    /**
     * Returns a JsonValue from a String.
     * 
     * @param value Value
     * @return JsonValue
     */
    public static JsonValue nullable(String value) {
        if (value == null) {
            return JsonValue.NULL;
        }
        return Json.createObjectBuilder().add("_", value).build().get("_");
    }
    
    /**
     * Returns a JsonValue from an Integer.
     * 
     * @param value Value
     * @return JsonValue
     */
    public static JsonValue nullable(Integer value) {
        if (value == null) {
            return JsonValue.NULL;
        }
        return Json.createObjectBuilder().add("_", value).build().get("_");
    }

    /**
     * Returns a JsonValue from an Long.
     *
     * @param value Value
     * @return JsonValue
     */
    public static JsonValue nullable(Long value) {
        if (value == null) {
            return JsonValue.NULL;
        }
        return Json.createObjectBuilder().add("_", value).build().get("_");
    }
    
    /**
     * Extract a string value from a JSON string using a path.
     * Path format: "key1.key2[0].key3"
     * 
     * @param jsonString JSON string
     * @param path Path to the value
     * @return String value or null if not found
     */
    public static String extractString(String jsonString, String path) {
        if (jsonString == null || path == null) {
            return null;
        }
        
        try (JsonReader reader = Json.createReader(new StringReader(jsonString))) {
            JsonObject jsonObject = reader.readObject();
            
            String[] parts = path.split("\\.");
            JsonValue current = jsonObject;
            
            for (String part : parts) {
                if (current == null) {
                    return null;
                }
                
                if (part.contains("[") && part.contains("]")) {
                    // Handle array access
                    String key = part.substring(0, part.indexOf("["));
                    int index = Integer.parseInt(part.substring(part.indexOf("[") + 1, part.indexOf("]")));
                    
                    if (current instanceof JsonObject) {
                        JsonArray array = ((JsonObject) current).getJsonArray(key);
                        if (array == null || index >= array.size()) {
                            return null;
                        }
                        current = array.get(index);
                    } else {
                        return null;
                    }
                } else {
                    // Handle object access
                    if (current instanceof JsonObject) {
                        current = ((JsonObject) current).get(part);
                    } else {
                        return null;
                    }
                }
            }
            
            if (current == null || current == JsonValue.NULL) {
                return null;
            }
            
            // Convert the value to string
            if (current instanceof JsonObject) {
                return current.toString();
            } else {
                return current.toString().replaceAll("\"", ""); // Remove quotes for string values
            }
        } catch (Exception e) {
            return null;
        }
    }
}

// AI-Generation: by Cursor
// prompt： Translating a document to other languages after opening it.