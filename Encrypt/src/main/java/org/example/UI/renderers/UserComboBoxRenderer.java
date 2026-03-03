package org.example.UI.renderers;

import org.example.UI.UIContext;
import org.example.core.asymmetric.ChatUser;

import javax.swing.*;
import java.awt.*;

public class UserComboBoxRenderer implements ListCellRenderer<ChatUser> {
    private static final int IMAGE_SIZE = 40;

    @Override
    public Component getListCellRendererComponent(JList<? extends ChatUser> list, ChatUser value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel root = new JPanel();
        if (value == null) {
            return root;
        }
        root.setLayout(new BorderLayout());
        JLabel userName = new JLabel();
        userName.setText(value.getName());
        ImageIcon icon1 = UIContext.getUserIcon();
        Image scaled = icon1.getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
        JLabel icon = new JLabel(new ImageIcon(scaled));
        root.add(icon, BorderLayout.WEST);
        root.add(userName, BorderLayout.CENTER);
        root.setBorder(BorderFactory.createEmptyBorder(5,  5,  5,  5));
        return root;
    }
}
