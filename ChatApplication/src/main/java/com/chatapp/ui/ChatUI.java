package com.chatapp.ui;

import com.chatapp.service.ChatService;
//import javafx.application.Application;
public class ChatUI {
    public static void main(String[] args) {
        ChatService chatService = new ChatService();
        chatService.startChat();
    }
}
