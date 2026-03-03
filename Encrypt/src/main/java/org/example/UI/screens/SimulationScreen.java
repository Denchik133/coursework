package org.example.UI.screens;

import org.example.UI.*;
import org.example.UI.buttons.MenuButton;
import org.example.UI.buttons.CancelButton;
import org.example.core.asymmetric.ChatUser;
import org.example.core.asymmetric.MessageBus;
import org.example.core.asymmetric.exceptions.ChatUserException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationScreen extends MyScreen {
    private JPanel centerArea = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private MessageBus messageBus = MessageBus.getInstance();
    private Navigator navigator;
    private JButton backButton;

    public SimulationScreen(Navigator n) {
        navigator = n;
        backButton = new CancelButton("Back to Menu");
        buildPanel();
        initListeners();
    }

    private void buildPanel() {
        this.setLayout(new BorderLayout());
        this.add(centerArea, BorderLayout.CENTER);
        JButton addUser = new MenuButton("Add User");
        buttonPanel.add(backButton);
        buttonPanel.add(addUser);
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
        bottomPanel.add(buttonPanel);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addUserCardsListeners(UserCard card) {
        card.setListener(new UserCardListener() {
            @Override
            public void onUserSelected(ChatUser user) {
                DialogWindowService service = DialogWindowService.getInstance();
                card.setSelected(true);
                service.show(card.getUser());
            }
        });
    }

    private void initListeners() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigator.show(Screens.MENU);
            }
        });
    }

    @Override
    protected void applyTheme() {
        super.applyTheme();
    }

    @Override
    protected void updateData() {

    }
}
