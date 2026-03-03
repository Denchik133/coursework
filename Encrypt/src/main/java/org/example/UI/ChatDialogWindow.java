package org.example.UI;

import org.example.UI.renderers.MessageCellRenderer;
import org.example.UI.renderers.UserComboBoxRenderer;
import org.example.UI.themes.ThemeUtils;
import org.example.core.asymmetric.ChatMessage;
import org.example.core.asymmetric.ChatUser;
import org.example.core.asymmetric.MessageBus;
import org.example.core.asymmetric.exceptions.EmptyMessageException;
import org.example.core.asymmetric.exceptions.MessageIsForAnotherUserException;
import org.example.core.exceptions.ReceiverDoesntExistException;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ChatDialogWindow extends JDialog {
    private JLabel userName;
    private JLabel userPublicKey;
    private JPanel sendMessageContainer;
    private JPanel chatMessageContainer;
    private JPanel root = new JPanel();
    private JPanel topContainer = new JPanel();
    private DefaultListModel<ChatMessage> model = new DefaultListModel<>();
    private DefaultComboBoxModel<Object> userModel = new DefaultComboBoxModel<>();
    private JList<ChatMessage> list = new JList<>(model);
    private final ChatUser chatUser;
    private MessageBus messageBus = MessageBus.getInstance();
    private JComboBox<ChatUser> sendTo;
    private DefaultComboBoxModel<ChatUser> modelComboBox;
    private JComboBox<Object> comboBox1 = new JComboBox<>(userModel);
    private JComboBox<String> comboBox = new JComboBox<>(new String[]{"All messages", "Messages to me"});
    private static final String FROM_ALL = "From All";
    private static DialogWindowService windowService = DialogWindowService.getInstance();

    public ChatDialogWindow(ChatUser user) {
        chatUser = user;
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        sendMessageContainer = new JPanel();
        chatMessageContainer = new JPanel();
        buildTopContainer();
        buildSendMessageContainer();
        buildChatMessageContainer();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.add(topContainer);
        root.add(chatMessageContainer);
        root.add(Box.createVerticalStrut(UIContext.getMediumPadding()));
        root.add(sendMessageContainer);
        this.add(root);
        root.setBorder(BorderFactory.createEmptyBorder(UIContext.getLargePadding(), UIContext.getLargePadding(), UIContext.getLargePadding(), UIContext.getLargePadding()));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                windowService.hide(user);
            }
        });
        applyTheme();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChatDialogWindow) {
            ChatDialogWindow window = (ChatDialogWindow) obj;
            if (this.chatUser.equals(window.chatUser)) {
                return true;
            }
        }
        return false;
    }

    private void buildTopContainer() {
        userPublicKey = new JLabel("Sun RSA public key");
        userPublicKey.setForeground(new Color(186, 29, 197));
        userPublicKey.setFont(new Font(userPublicKey.getFont().getFontName(), Font.ITALIC, 16));
        userPublicKey.setToolTipText("Click to copy");
        userPublicKey.setOpaque(false);
        userPublicKey.setPreferredSize(new Dimension(400, 250));
        userPublicKey.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                StringSelection selection = new StringSelection(chatUser.getPublicKey().toString());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, null);
            }
        });
        userName = new JLabel(chatUser.getName());
        userName.setFont(new Font(userName.getFont().getFontName(), Font.BOLD, 20));
        JPanel userCard = new JPanel();
        modelComboBox = new DefaultComboBoxModel<>();
        for (ChatUser chatUser : messageBus.getChatUsers()) {
            modelComboBox.addElement(chatUser);
        }
        JLabel sendTo1 = new JLabel("Send to:");
        JPanel vBox = new JPanel();
        vBox.setLayout(new BoxLayout(vBox, BoxLayout.Y_AXIS));
        vBox.add(sendTo1);
        sendTo = new JComboBox<>(modelComboBox);
        sendTo.setRenderer(new UserComboBoxRenderer());
        sendTo.setMaximumSize(new Dimension(200, 40));
        vBox.add(sendTo);
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.X_AXIS));
        userCard.setLayout(new BoxLayout(userCard, BoxLayout.Y_AXIS));
        userCard.add(userName);
        userCard.add(userPublicKey);
        topContainer.add(userCard);
        topContainer.add(vBox);
        topContainer.setBorder(BorderFactory.createEmptyBorder(UIContext.getMediumPadding(), UIContext.getMediumPadding(), UIContext.getMediumPadding(), UIContext.getMediumPadding()));
    }

    private void buildChatMessageContainer() {
        JPanel topContainer = new JPanel();
        userModel.addElement(FROM_ALL);
        userModel.addAll(messageBus.getChatUsers());
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.X_AXIS));
        refreshChatMessageListModel(true);
        chatMessageContainer.setLayout(new BorderLayout());
        list.setCellRenderer(new MessageCellRenderer(chatUser));
        JScrollPane scrollPane = new JScrollPane(list);
        chatMessageContainer.add(scrollPane, BorderLayout.CENTER);
        topContainer.add(comboBox);
        topContainer.add(comboBox1);
        chatMessageContainer.add(topContainer, BorderLayout.NORTH);
        comboBox.setSelectedIndex(1);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) comboBox.getSelectedItem();
                switch (selected) {
                    case "All messages":
                        refreshChatMessageListModel(true);
                        break;
                        case "Messages to me":
                            refreshChatMessageListModel(false);
                            break;
                }
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) comboBox.getSelectedItem();
                boolean showAll = true;
                switch (selected) {
                    case "All messages":
                        showAll = true;
                        break;
                    case "Messages to me":
                        showAll = false;
                        break;
                }
                Object selected1 = comboBox1.getSelectedItem();
                if (selected1 instanceof String) {
                    if (selected1.equals(FROM_ALL)) {
                        refreshChatMessageListModel(showAll);
                    }
                }
                else if (selected1 instanceof ChatUser) {
                    refreshChatMessageListModel(showAll, (ChatUser) selected1);
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

    public void refreshChatMessageListModel(boolean showAllMessages, ChatUser user) {
        model.clear();
        List<ChatMessage> listMessage = new ArrayList<>(showAllMessages ? messageBus.getAllMessages() : messageBus.getMessagesForUser(chatUser));
        for (ChatMessage message : listMessage) {
            if (message.getFrom().equals(user)) {
                model.addElement(message);
            }
        }
    }

    private void buildSendMessageContainer() {
        refreshUserListModel();
        sendMessageContainer.setLayout(new BoxLayout(sendMessageContainer,BoxLayout.Y_AXIS));
        JTextField textField = new JTextField();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    chatUser.sendMessage((ChatUser) sendTo.getSelectedItem(), textField.getText());
                    windowService.updateAllWindows();
                } catch (EmptyMessageException | ReceiverDoesntExistException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error with sending message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        sendMessageContainer.add(textField);
        sendMessageContainer.add(sendButton);
    }

    public void refreshUserListModel() {
        List <ChatUser> users = messageBus.getChatUsers();
        modelComboBox.removeAllElements();
        modelComboBox.addAll(users);
    }

    public void onUpdate() {
        refreshChatMessageListModel(comboBox.getSelectedItem().toString().equals("All messages"));
    }

    public ChatUser getChatUser() {
        return chatUser;
    }

    public void applyTheme() {
        ThemeUtils.setBackgroundRecursive(this);
        ThemeUtils.setTextColorRecursive(this);
    }
}
