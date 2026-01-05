package org.example.UI;

import org.example.core.asymmetric.ChatMessage;
import org.example.core.asymmetric.ChatUser;
import org.example.core.asymmetric.MessageBus;
import org.example.core.asymmetric.exceptions.EmptyMessageException;
import org.example.core.asymmetric.exceptions.MessageIsForAnotherUserException;
import org.example.core.exceptions.ReceiverDoesntExistException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChatDialogWindow extends JDialog {
    private JLabel userName;
    private JLabel userPublicKey;
    private JPanel sendMessageContainer;
    private JPanel chatMessageContainer;
    private JPanel root = new JPanel();
    private DefaultListModel<ChatMessage> model = new DefaultListModel<>();
    private JList<ChatMessage> list = new JList<>(model);
    private ChatUser chatUser;
    private DefaultListModel<ChatUser> userModel = new DefaultListModel<>();
    private JList<ChatUser> list2 = new JList<>(userModel);
    private MessageBus messageBus = MessageBus.getInstance();

    public ChatDialogWindow(ChatUser user) {
        chatUser = user;
        userPublicKey = new JLabel(user.getPublicKey().toString());
        userName = new JLabel(user.getName());
        JPanel userCard = new JPanel();
        userCard.setLayout(new BoxLayout(userCard, BoxLayout.Y_AXIS));
        userCard.add(userName);
        userCard.add(userPublicKey);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        sendMessageContainer = new JPanel();
        chatMessageContainer = new JPanel();
        buildSendMessageContainer();
        buildChatMessageContainer();
        root.setLayout(new BoxLayout(root,BoxLayout.Y_AXIS));
        root.add(userCard);
        root.add(sendMessageContainer);
        root.add(chatMessageContainer);
        this.add(root);
    }

    private void buildChatMessageContainer() {
        refreshChatMessageListModel(true);
        chatMessageContainer.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(list);
        chatMessageContainer.add(scrollPane, BorderLayout.CENTER);
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"All messages", "Messages to me"});
        chatMessageContainer.add(comboBox, BorderLayout.NORTH);
        comboBox.setSelectedIndex(1);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) comboBox.getSelectedItem();
                switch (selected) {
                    case "All messages":
                        refreshChatMessageListModel(false);
                        break;
                        case "Messages to me":
                            refreshChatMessageListModel(true);
                            break;
                }
            }
        });
        JButton showMessageButon = new JButton("Show message");
        showMessageButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatMessage message = (ChatMessage) list.getSelectedValue();
                if (message != null) {
                    try {
                        JOptionPane.showMessageDialog(ChatDialogWindow.this, chatUser.decrypt(message), "Message: ",  JOptionPane.INFORMATION_MESSAGE);
                    } catch (MessageIsForAnotherUserException ex) {
                        JOptionPane.showMessageDialog(ChatDialogWindow.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        chatMessageContainer.add(showMessageButon, BorderLayout.SOUTH);
    }

    public void refreshChatMessageListModel(boolean showAllMessages) {
        model.clear();
        model.addAll(showAllMessages ? messageBus.getAllMessages() : messageBus.getMessagesForUser(chatUser));
    }

    private void buildSendMessageContainer() {
        refreshUserListModel();
        sendMessageContainer.setLayout(new BoxLayout(sendMessageContainer,BoxLayout.Y_AXIS));
        JLabel sendTo = new JLabel("Send to:");
        JScrollPane scrollPane = new JScrollPane(list2);
        JTextField textField = new JTextField();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    chatUser.sendMessage(list2.getSelectedValue(), textField.getText());
                    // Оновити діалогові вікна
                } catch (EmptyMessageException | ReceiverDoesntExistException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error with sending message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        sendMessageContainer.add(sendTo);
        sendMessageContainer.add(scrollPane);
        sendMessageContainer.add(textField);
        sendMessageContainer.add(sendButton);
    }

    public void refreshUserListModel() {
        List <ChatUser> users = messageBus.getChatUsers();
        userModel.clear();
        userModel.addAll(users);
        list2.revalidate();
        list2.repaint();
    }
}
