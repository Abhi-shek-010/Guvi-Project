# Chat Application

## Overview
A simple console-based chat application that simulates a conversation with a bot. The application provides a WhatsApp-like interface in the console and maintains chat history for future reference.

## Features
- Interactive console-based chat interface with a bot
- Persistent chat history storage
- Timestamp for each message
- Clean and organized message display
- Command support for managing chat history
- Simple and intuitive user interface

## Technical Stack
- Java 11 or higher
- Maven for project management
- Gson for JSON handling

## Setup Instructions
1. Ensure you have Java 11 or higher installed
2. Install Maven
3. Clone the repository
4. Build the project using Maven: mvn clean install
5. Run the application using: mvn exec:java -Dexec.mainClass="com.chatapp.ui.ChatUI"

## Usage
1. Launch the application
2. Type your message and press Enter to chat with the bot
3. Available commands:
   - Type 'exit' to end the chat session
   - Type 'clear history' to clear chat history
   - Type 'help' to show available commands

## Project Structure
- src/: Contains all source code
  - dao/: Data Access Object for message handling
  - service/: Core chat functionality
  - ui/: User interface components
- lib/: External libraries
- target/: Compiled classes and build artifacts
- pom.xml: Maven configuration file

## Data Storage
- Chat history is stored in ~/.chatapp/chat_history.txt
- Bot responses are configured in ~/.chatapp/messages.json

## Contact
For any queries or support, please contact:
- GitHub: https://github.com/Abhi-shek-010
- Email: abhi0shek6@gmail.com

## License
This project is licensed under the MIT License.
