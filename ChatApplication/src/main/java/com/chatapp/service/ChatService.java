package com.chatapp.service;

import com.chatapp.dao.MessageDAO;
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatService {
    private MessageDAO messageDAO;
    private List<String> messageHistory;
    private static final String HISTORY_FILE = "chat_history.txt";
    private final Path historyPath;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ChatService() {
        messageDAO = new MessageDAO();
        Path homeDir = Paths.get(System.getProperty("user.home"), ".chatapp");
        try {
            Files.createDirectories(homeDir);
        } catch (IOException e) {
            // Silently handle directory creation error
        }
        historyPath = homeDir.resolve(HISTORY_FILE);
        messageHistory = loadMessageHistory();
    }

    public void startChat() {
        Scanner scanner = new Scanner(System.in);
        displayWelcomeMessage();
        
        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("clear history")) {
                clearMessageHistory();
                continue;
            }
            String botResponse = messageDAO.getResponse(userInput);
            String timestamp = LocalDateTime.now().format(TIME_FORMATTER);
            messageHistory.add(String.format("[%s] You: %s", timestamp, userInput));
            messageHistory.add(String.format("[%s] Bot: %s", timestamp, botResponse));
            
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("\nChat ended. Goodbye!");
                saveMessageHistory();
                break;
            }
            System.out.println("Bot: " + botResponse);
        }
        scanner.close();
    }

    private void displayWelcomeMessage() {
        System.out.println("\n=== Chat Application ===");
        if (!messageHistory.isEmpty()) {
            System.out.println("\nPrevious Chat History:");
            for (String message : messageHistory) {
                System.out.println(message);
            }
            System.out.println("\n=== New Chat Session ===");
        }
        System.out.println("\nType your message and press Enter to chat.");
        System.out.println("Type 'exit' to end the chat.");
        System.out.println("Type 'clear history' to clear chat history.");
        System.out.println("========================\n");
    }

    private List<String> loadMessageHistory() {
        List<String> history = new ArrayList<>();
        if (Files.exists(historyPath)) {
            try (BufferedReader reader = Files.newBufferedReader(historyPath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    history.add(line);
                }
            } catch (IOException e) {
                // Silently handle file reading error
            }
        }
        return history;
    }

    private void saveMessageHistory() {
        try (BufferedWriter writer = Files.newBufferedWriter(historyPath, 
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (String message : messageHistory) {
                writer.write(message);
                writer.newLine();
            }
        } catch (IOException e) {
            // Silently handle file writing error
        }
    }

    private void clearMessageHistory() {
        messageHistory.clear();
        try {
            Files.deleteIfExists(historyPath);
            System.out.println("\nChat history has been cleared.");
        } catch (IOException e) {
            // Silently handle file deletion error
        }
    }
}
