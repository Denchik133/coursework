package org.example.UI.screens;

import org.example.UI.buttons.MenuButton;
import org.example.UI.themes.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuScreen extends MyScreen {

    private Navigator navigator;
    private JButton cipherScreen;
    private JButton loggerScreen;
    private JButton settingsScreen;
    private JButton simulationScreen;
    private JButton cipherHistoryScreen;
    private JButton exit;
    private JPanel buttonContainer;
    private static final int PADDING_SIZE = 10;

    public MenuScreen(Navigator n) {
        navigator = n;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        cipherScreen = new MenuButton("Cipher");
        simulationScreen = new MenuButton("Simulation");
        settingsScreen = new MenuButton("Settings");
        loggerScreen = new MenuButton("Logger");
        cipherHistoryScreen = new MenuButton("Cipher History");
        exit = new MenuButton("Exit");
        buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonContainer.add(cipherScreen);
        buttonContainer.add(Box.createVerticalStrut(PADDING_SIZE));
        buttonContainer.add(simulationScreen);
        buttonContainer.add(Box.createVerticalStrut(PADDING_SIZE));
        buttonContainer.add(settingsScreen);
        buttonContainer.add(Box.createVerticalStrut(PADDING_SIZE));
        buttonContainer.add(loggerScreen);
        buttonContainer.add(Box.createVerticalStrut(PADDING_SIZE));
        buttonContainer.add(cipherHistoryScreen);
        buttonContainer.add(Box.createVerticalStrut(PADDING_SIZE));
        buttonContainer.add(exit);
        buttonContainer.setMaximumSize(buttonContainer.getPreferredSize());
        this.add(Box.createVerticalGlue());
        this.add(buttonContainer);
        this.add(Box.createVerticalGlue());
        initListeners();
    }

    private void initListeners() {
        cipherScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigator.show(Screens.CIPHER);
            }
        });
        simulationScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigator.show(Screens.SIMULATION);
            }
        });
        settingsScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigator.show(Screens.SETTINGS);
            }
        });
        loggerScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigator.show(Screens.LOGGER);
            }
        });
        cipherHistoryScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigator.show(Screens.CIPHER_HISTORY);
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
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
