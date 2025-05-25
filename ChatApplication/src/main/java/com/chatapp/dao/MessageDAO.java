package com.chatapp.dao;

//import com.chatapp.model.Message;
//import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MessageDAO {
    private static final String JSON_FILE = "src/main/resources/messages.json";
    private Map<String, String> responses;

    public MessageDAO() {
        loadMessages();
    }

    private void loadMessages() {
        try (FileReader reader = new FileReader(JSON_FILE)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            responses = new HashMap<>();
            jsonObject.getAsJsonObject("responses").entrySet().forEach(entry -> 
                responses.put(entry.getKey(), entry.getValue().getAsString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getResponse(String userInput) {
        return responses.getOrDefault(userInput.toLowerCase(), responses.get("default"));
    }
}
