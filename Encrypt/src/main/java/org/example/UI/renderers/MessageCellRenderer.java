package org.example.UI.renderers;

import org.example.UI.UIContext;
import org.example.UI.themes.ThemeUtils;
import org.example.core.asymmetric.ChatMessage;
import org.example.core.asymmetric.ChatUser;
import org.example.core.asymmetric.exceptions.MessageIsForAnotherUserException;

import javax.swing.*;
import java.awt.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MessageCellRenderer implements ListCellRenderer<ChatMessage> {
    private static final int IMAGE_SIZE = 40;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
    private ChatUser user;
    private final String messageIcon = "/Images/message.png";
    private final String encryptedMessageIcon = "/Images/message.png";
    private final String encryptedText = "This message is encrypted";

    public MessageCellRenderer(ChatUser user) {
        this.user = user;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ChatMessage> list, ChatMessage value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel root = new JPanel();
        if (value == null) {
            return root;
        }
        root.setLayout(new BorderLayout());
        JLabel userFrom = new JLabel();
        JLabel userTo = new JLabel();
        JLabel to = new JLabel("To:");
        JLabel from = new JLabel("From:");
        JLabel time = new JLabel();
        time.setText(value.getTimestamp().atZone(ZoneId.systemDefault()).toLocalTime().format(formatter));
        JPanel centerArea = new JPanel();
        centerArea.setLayout(new BoxLayout(centerArea, BoxLayout.X_AXIS));
        JPanel verticalContainer = new JPanel();
        verticalContainer.setLayout(new BoxLayout(verticalContainer, BoxLayout.Y_AXIS));
        verticalContainer.add(from);
        verticalContainer.add(userFrom);
        verticalContainer.add(to);
        verticalContainer.add(userTo);
        centerArea.add(verticalContainer);
        ImageIcon icon1;
        try {
            centerArea.add(new JLabel(user.decrypt(value)));
            icon1 = UIContext.getMessageIcon();
        } catch (MessageIsForAnotherUserException e) {
            centerArea.add(new JLabel(encryptedText));
            icon1 = UIContext.getEncryptedMessageIcon();
        }
        Image scaled = icon1.getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
        JLabel icon = new JLabel(new ImageIcon(scaled));
        root.add(icon, BorderLayout.WEST);
        root.add(centerArea, BorderLayout.CENTER);
        root.add(time, BorderLayout.EAST);
        root.setBorder(BorderFactory.createEmptyBorder(5,  10, 5, 10));
        verticalContainer.setBorder(BorderFactory.createEmptyBorder(0,  5, 0, 5));
        ThemeUtils.setTextColorRecursive(root);
        ThemeUtils.setBackgroundRecursive(root);
        return root;
    }
}
