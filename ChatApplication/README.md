## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

# Chat Application

A simple console-based chat application that simulates a conversation with a bot. The application maintains chat history and provides a WhatsApp-like interface in the console.

## Features

- Interactive chat interface with a bot
- Persistent chat history
- Timestamp for each message
- Clean, organized message display
- Command support for managing chat history

## Prerequisites

- Java 11 or higher
- Maven

## Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd ChatApplication
```

2. Build the project using Maven:
```bash
mvn clean install
```

## Running the Application

Run the application using Maven:
```bash
mvn exec:java -Dexec.mainClass="com.chatapp.ui.ChatUI"
```

Or run the compiled JAR file:
```bash
java -jar target/chat-application-1.0-SNAPSHOT.jar
```

## Usage

### Chat Commands

- Type your message and press Enter to chat with the bot
- Type `exit` to end the chat session
- Type `clear history` to clear the chat history

### Chat History

- Chat history is automatically saved in your home directory under `.chatapp/chat_history.txt`
- Previous chat history is displayed when you start a new session
- Each message is timestamped with the time it was sent

### Message Format

Messages are displayed in the following format:
```
[HH:mm] You: <your message>
[HH:mm] Bot: <bot response>
```

## Project Structure

```
ChatApplication/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── chatapp/
│       │           ├── dao/
│       │           │   └── MessageDAO.java         # Handles message storage and retrieval
│       │           ├── service/
│       │           │   └── ChatService.java        # Core chat functionality and history management
│       │           └── ui/
│       │               └── ChatUI.java             # Main application entry point
│       └── resources/                              # Default configuration files
│           └── messages.json                       # Default bot responses
├── target/                                         # Compiled files and build output
├── pom.xml                                         # Maven project configuration
└── README.md                                       # Project documentation
```

### Key Components

1. **MessageDAO (Data Access Object)**
   - Manages bot responses
   - Handles JSON file operations
   - Stores messages in user's home directory

2. **ChatService (Service Layer)**
   - Implements core chat functionality
   - Manages chat history
   - Handles user input and bot responses

3. **ChatUI (User Interface)**
   - Entry point of the application
   - Handles user interaction
   - Displays chat interface

### File Storage

The application stores its data in the user's home directory:
```
~/.chatapp/
├── chat_history.txt    # Chat history file
└── messages.json       # Bot responses configuration
```

## Configuration

### Messages

Bot responses are configured in `messages.json` located in your home directory under `.chatapp/`. The file is automatically created with default responses if it doesn't exist.

### Chat History

Chat history is stored in `chat_history.txt` in your home directory under `.chatapp/`. The file is automatically created when you start chatting.

## Dependencies

- Gson (for JSON handling)

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
