package org.example.UI;

import org.example.core.asymmetric.ChatUser;

import javax.swing.*;
import java.awt.*;

public class UserCard extends JPanel {
    private JLabel name;
    private JLabel publicKey;
    public UserCard(ChatUser user) {
        this.setLayout(new BorderLayout(5, 5));
        name = new JLabel(user.getName());
        publicKey = new JLabel(user.getPublicKey().toString().substring(0, 16) + "...");
        this.add(name, BorderLayout.NORTH);
        this.add(publicKey, BorderLayout.CENTER);
        this.setSize(180, 120);
    }
}
