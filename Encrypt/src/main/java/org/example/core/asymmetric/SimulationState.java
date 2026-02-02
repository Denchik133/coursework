package org.example.core.asymmetric;

import java.io.Serializable;
import java.util.List;

public class SimulationState implements Serializable {
    public List<ChatUser> getUsers() {
        return users;
    }

    public void setUsers(List<ChatUser> users) {
        this.users = users;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    private List<ChatUser> users;
    private List<ChatMessage> messages;

}
