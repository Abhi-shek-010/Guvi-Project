package com.chatapp.ui;

import com.chatapp.service.ChatService;
//import javafx.application.Application;
public class ChatUI {
    public static void main(String[] args) {
        try {
            ChatService chatService = new ChatService();
            chatService.startChat();
        } catch (Exception e) {
            System.err.println("Error starting chat application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
