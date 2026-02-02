package org.example.UI.screens;

import org.example.UI.ChatDialogWindow;
import org.example.UI.MainFrame;
import org.example.UI.UserCard;
import org.example.UI.UserCardListener;
import org.example.UI.buttons.MenuButton;
import org.example.core.asymmetric.ChatUser;
import org.example.core.asymmetric.MessageBus;
import org.example.core.asymmetric.exceptions.ChatUserException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimulationScreen extends JPanel {

    private JPanel centerArea = new JPanel();
    private MessageBus messageBus = MessageBus.getInstance();
    private Navigator navigator;

    public SimulationScreen(Navigator n) {
        navigator = n;
        buildPanel();
    }

    private void buildPanel() {
        this.setLayout(new BorderLayout());
        this.add(centerArea, BorderLayout.CENTER);
        JButton addUser = new MenuButton("Add User");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addUser);
        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter User's Name:");
                try {
                    UserCard card = new UserCard(messageBus.registerUser(name.toUpperCase()));
                    addUserCardsListeners(card);
                    centerArea.add(card);
                    centerArea.revalidate();
                    centerArea.repaint();
                } catch (ChatUserException ex) {
                    JOptionPane.showMessageDialog(SimulationScreen.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        for (ChatUser user : messageBus.getChatUsers()) {
            UserCard card = new UserCard(user);
            addUserCardsListeners(card);
            centerArea.add(card);
        }
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addUserCardsListeners(UserCard card) {
        card.setListener(new UserCardListener() {
            @Override
            public void onUserSelected(ChatUser user) {
                ChatDialogWindow dialogWindow = new ChatDialogWindow(user);
                dialogWindow.setVisible(true);
                card.setSelected(true);
                dialogWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        card.setSelected(false);
                    }
                });
            }
        });
    }

}
