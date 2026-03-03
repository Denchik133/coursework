package org.example.core.asymmetric;

import org.example.core.asymmetric.exceptions.ChatUserException;
import org.example.data.MyDataException;
import org.example.data.SerialisationRepository;
import org.example.logger.EventLogger;
import org.example.logger.LogCategory;
import org.example.logger.LogLevel;
import org.example.logger.MyEvent;

import java.util.ArrayList;
import java.util.List;

public class MessageBus {
    private List<ChatMessage> messages;
    private static MessageBus instance;
    private List <ChatUser> chatUsers;
    private static EventLogger logger = EventLogger.getInstance();
    private static SerialisationRepository<SimulationState> repository = new SerialisationRepository<>("State.ser");

    public List<ChatUser> getChatUsers() {
        return chatUsers;
    }

    public static MessageBus getInstance() {
        if (instance == null) {
            instance = new MessageBus();
            try {
                instance.restoreFromState( (SimulationState) repository.loadData());
            } catch (MyDataException e) {
                logger.addEvent(new MyEvent(LogCategory.SYSTEM, LogLevel.INFO, "Simulation state wasn't found."));
            }
        }
        return instance;
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
        save();
    }

    public List<ChatMessage> getMessagesForUser(ChatUser user) {
        List<ChatMessage> messages = new ArrayList<>();
        for (ChatMessage message : this.messages) {
            if (message.getTo().equals(user)) {
                messages.add(message);
            }
        }
        return messages;
    }

    public List<ChatMessage> getAllMessages() {
        return messages;
    }

    private MessageBus() {
        messages = new ArrayList<>();
        chatUsers = new ArrayList<>();
    }

    public SimulationState getSimulationState() {
        SimulationState state = new SimulationState();
        state.setMessages(messages);
        state.setUsers(chatUsers);
        return state;
    }

    public void restoreFromState(SimulationState state) {
        messages = state.getMessages();
        chatUsers = state.getUsers();
    }

    public boolean userExist(String name) {
        for (ChatUser chatUser : chatUsers) {
            if (chatUser.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public ChatUser registerUser(String name) throws ChatUserException {
        if (userExist(name)) {
            throw new ChatUserException("User with name " + name + " already exists!");
        }
        ChatUser chatUser = new ChatUser(name);
        chatUsers.add(chatUser);
        save();
        return chatUser;
    }

    public void save() {
        try {
            repository.saveData(getSimulationState());
        }
        catch (MyDataException e) {
            logger.addEvent(new MyEvent(LogCategory.SYSTEM, LogLevel.ERROR, "Saving state failed."));
        }
    }
}
