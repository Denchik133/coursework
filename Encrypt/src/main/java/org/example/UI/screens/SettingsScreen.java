package org.example.UI.screens;

import org.example.UI.buttons.MenuButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsScreen extends MyScreen {
    private Navigator navigator;
    private JButton backButton;
    private JPanel bottomPanel = new JPanel();

    public SettingsScreen(Navigator n) {
        navigator = n;
        backButton = new MenuButton("Back to Menu");
        this.setLayout(new BorderLayout());
        this.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(backButton);
        initListeners();
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
