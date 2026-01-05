package org.example.core.asymmetric;

import java.time.Instant;

public class ChatMessage {
    private ChatUser from;
    private ChatUser to;
    private byte[] message;
    private Instant timestamp;

    public ChatMessage(ChatUser from, ChatUser to, byte[] message, Instant timestamp) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message from: " + from + " to: " + to + " time: " + timestamp;
    }

    public ChatUser getFrom() {
        return from;
    }

    public ChatUser getTo() {
        return to;
    }

    public byte[] getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
