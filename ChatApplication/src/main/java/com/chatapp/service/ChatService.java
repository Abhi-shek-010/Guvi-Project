package com.chatapp.service;

import com.chatapp.dao.MessageDAO;
import com.chatapp.model.Message;

import java.util.Scanner;

public class ChatService {
    private MessageDAO messageDAO;

    public ChatService() {
        messageDAO = new MessageDAO();
    }

    public void startChat() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Chat Application!");
        
        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Chat ended.");
                break;
            }
            String botResponse = messageDAO.getResponse(userInput);
            System.out.println("Bot: " + botResponse);
        }
        scanner.close();
    }
}

