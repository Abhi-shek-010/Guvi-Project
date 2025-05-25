package com.chatapp.service;

import com.chatapp.dao.MessageDAO;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class ChatService {
    private MessageDAO messageDAO;
    private List<String> messageHistory;
    private static final String HISTORY_FILE = "src/main/resources/chat_history.txt";

    public ChatService() {
        messageDAO = new MessageDAO();
        messageHistory = loadMessageHistory(); // Load history on startup
    }

    public void startChat() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Chat Application!");
        
        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("clear history")) {
                clearMessageHistory(); // Clear history command
                continue;
            }
            String botResponse = messageDAO.getResponse(userInput);
            messageHistory.add("You: " + userInput);
            messageHistory.add("Bot: " + botResponse);
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Chat ended.");
                saveMessageHistory(); // Save history on exit
                displayMessageHistory(); // Call to display message history
                break;
            }
            System.out.println("Bot: " + botResponse);
        }
        scanner.close();
    }

    // Load message history from file
    private List<String> loadMessageHistory() {
        List<String> history = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            System.out.println("No previous history found.");
        }
        return history;
    }

    // Save message history to file
    private void saveMessageHistory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            for (String message : messageHistory) {
                writer.write(message);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving message history.");
        }
    }

    // Clear message history
    private void clearMessageHistory() {
        messageHistory.clear();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            writer.write(""); // Clear the file
        } catch (IOException e) {
            System.out.println("Error clearing message history.");
        }
        System.out.println("Message history cleared.");
    }

    // New method to display message history
    private void displayMessageHistory() {
        System.out.println("\n--- Message History ---");
        for (String message : messageHistory) {
            System.out.println(message);
        }
        System.out.println("-----------------------\n");
    }
}
