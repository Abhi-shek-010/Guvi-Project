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
    private static final int MAX_MESSAGE_LENGTH = 500;
    private static final int MAX_HISTORY_SIZE = 1000;

    public ChatService() {
        messageDAO = new MessageDAO();
        Path homeDir = Paths.get(System.getProperty("user.home"), ".chatapp");
        try {
            Files.createDirectories(homeDir);
        } catch (IOException e) {
            System.err.println("Error: Could not create chat directory. Please check your permissions.");
            System.exit(1);
        }
        historyPath = homeDir.resolve(HISTORY_FILE);
        messageHistory = loadMessageHistory();
    }

    public void startChat() {
        Scanner scanner = new Scanner(System.in);
        displayWelcomeMessage();
        
        while (true) {
            try {
                System.out.print("You: ");
                String userInput = scanner.nextLine().trim();
                
                // Input validation
                if (userInput.isEmpty()) {
                    System.out.println("Error: Message cannot be empty. Please try again.");
                    continue;
                }
                
                if (userInput.length() > MAX_MESSAGE_LENGTH) {
                    System.out.println("Error: Message is too long. Maximum length is " + MAX_MESSAGE_LENGTH + " characters.");
                    continue;
                }

                if (userInput.equalsIgnoreCase("clear history")) {
                    clearMessageHistory();
                    continue;
                }

                if (userInput.equalsIgnoreCase("help")) {
                    displayHelp();
                    continue;
                }

                String botResponse = messageDAO.getResponse(userInput);
                if (botResponse == null) {
                    System.out.println("Error: Could not get response from bot. Please try again.");
                    continue;
                }

                String timestamp = LocalDateTime.now().format(TIME_FORMATTER);
                addToHistory(String.format("[%s] You: %s", timestamp, userInput));
                addToHistory(String.format("[%s] Bot: %s", timestamp, botResponse));
                
                if (userInput.equalsIgnoreCase("exit")) {
                    System.out.println("\nChat ended. Goodbye!");
                    saveMessageHistory();
                    break;
                }
                System.out.println("Bot: " + botResponse);
            } catch (Exception e) {
                System.err.println("Error: An unexpected error occurred. Please try again.");
            }
        }
        scanner.close();
    }

    private void addToHistory(String message) {
        messageHistory.add(message);
        if (messageHistory.size() > MAX_HISTORY_SIZE) {
            messageHistory.remove(0);
        }
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
        displayHelp();
    }

    private void displayHelp() {
        System.out.println("\nAvailable Commands:");
        System.out.println("- Type your message to chat with the bot");
        System.out.println("- Type 'exit' to end the chat");
        System.out.println("- Type 'clear history' to clear chat history");
        System.out.println("- Type 'help' to show this help message");
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
                System.err.println("Warning: Could not load chat history. Starting with empty history.");
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
            System.err.println("Error: Could not save chat history. Your messages may be lost.");
        }
    }

    private void clearMessageHistory() {
        messageHistory.clear();
        try {
            Files.deleteIfExists(historyPath);
            System.out.println("\nChat history has been cleared.");
        } catch (IOException e) {
            System.err.println("Error: Could not clear chat history file.");
        }
    }
}
