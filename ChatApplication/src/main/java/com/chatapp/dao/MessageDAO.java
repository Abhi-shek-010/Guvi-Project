package com.chatapp.dao;

//import com.chatapp.model.Message;
//import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class MessageDAO {
    private static final String MESSAGES_FILE = "messages.json";
    private final Path messagesPath;
    private Map<String, String> responses;
    private static final String DEFAULT_RESPONSE = "I'm not sure how to respond to that.";

    public MessageDAO() {
        responses = new HashMap<>();
        // Create messages directory in user's home directory
        Path homeDir = Paths.get(System.getProperty("user.home"), ".chatapp");
        try {
            Files.createDirectories(homeDir);
        } catch (IOException e) {
            System.err.println("Error creating messages directory: " + e.getMessage());
        }
        messagesPath = homeDir.resolve(MESSAGES_FILE);
        loadMessages();
    }

    private void loadMessages() {
        if (Files.exists(messagesPath)) {
            try (Reader reader = Files.newBufferedReader(messagesPath)) {
                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                jsonObject.getAsJsonObject("responses").entrySet().forEach(entry -> 
                    responses.put(entry.getKey().toLowerCase(), entry.getValue().getAsString()));
                System.out.println("Messages loaded successfully.");
            } catch (IOException e) {
                System.err.println("Error reading messages file: " + e.getMessage());
                initializeDefaultResponses();
            } catch (JsonIOException | JsonSyntaxException e) {
                System.err.println("Error parsing messages file: " + e.getMessage());
                initializeDefaultResponses();
            }
        } else {
            System.out.println("Messages file not found. Creating default messages.");
            initializeDefaultResponses();
            saveDefaultMessages();
        }
    }

    private void saveDefaultMessages() {
        JsonObject jsonObject = new JsonObject();
        JsonObject responsesObject = new JsonObject();
        
        responses.forEach(responsesObject::addProperty);
        jsonObject.add("responses", responsesObject);

        try (Writer writer = Files.newBufferedWriter(messagesPath, 
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);
            System.out.println("Default messages saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving default messages: " + e.getMessage());
        }
    }

    private void initializeDefaultResponses() {
        responses.put("default", DEFAULT_RESPONSE);
        responses.put("hello", "Hello! How can I help you today?");
        responses.put("hi", "Hi there! How can I assist you?");
        responses.put("bye", "Goodbye! Have a great day!");
        responses.put("how are you", "I'm doing well, thank you for asking!");
        responses.put("what's your name", "I'm ChatBot, nice to meet you!");
        responses.put("help", "I can chat with you! Just type your message and I'll respond. Type 'exit' to end the chat.");
    }

    public String getResponse(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return DEFAULT_RESPONSE;
        }
        return responses.getOrDefault(userInput.toLowerCase(), responses.get("default"));
    }
}
