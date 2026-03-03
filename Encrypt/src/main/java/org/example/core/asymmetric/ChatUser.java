package org.example.core.asymmetric;

import org.example.core.asymmetric.exceptions.EmptyMessageException;
import org.example.core.asymmetric.exceptions.ChatUserException;
import org.example.core.asymmetric.exceptions.MessageIsForAnotherUserException;
import org.example.core.exceptions.ReceiverDoesntExistException;
import org.example.logger.EventLogger;
import org.example.logger.LogCategory;
import org.example.logger.LogLevel;
import org.example.logger.MyEvent;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PublicKey;
import java.time.Instant;

public class ChatUser implements Serializable {
    private String name;
    private final KeyPair keyPair;
    private static final EventLogger LOGGER = EventLogger.getInstance();
    private static final String NEW_USER_INFO = "User created";
    private static final String NEW_USER_MESSAGE = "Message sent";
    @java.io.Serial
    private static final long serialVersionUID = -1L;

    public String getName() {
        return name;
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public ChatUser(String name) throws ChatUserException {
        this.name = name;
        keyPair = RSAService.generateKeyPair();
        if (name == null || name.isEmpty()) {
            throw new ChatUserException("Name cannot be empty!");
        }
        LOGGER.addEvent(new MyEvent(LogCategory.SIMULATION, LogLevel.INFO, NEW_USER_INFO));
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String decrypt(ChatMessage message) throws MessageIsForAnotherUserException {
        if (message.getTo().equals(this)) {
            return RSAService.decrypt(message.getMessage(), keyPair.getPrivate());
        }
        throw new MessageIsForAnotherUserException("Message for another user");
    }

    public void sendMessage(ChatUser user, String message) throws EmptyMessageException, ReceiverDoesntExistException {
        if (message == null || message.isEmpty()) {
            throw new EmptyMessageException("Message is empty");
        }
        if (user == null) {
            throw new ReceiverDoesntExistException("Receiver is not selected");
        }
        MessageBus messageBus = MessageBus.getInstance();
        byte[] encryptedMessage = RSAService.encrypt(message, user.getPublicKey());
        ChatMessage chatMessage = new ChatMessage(this, user, encryptedMessage, Instant.now());
        messageBus.addMessage(chatMessage);
        LOGGER.addEvent(new MyEvent(LogCategory.SIMULATION, LogLevel.INFO, NEW_USER_MESSAGE));
    }
}
