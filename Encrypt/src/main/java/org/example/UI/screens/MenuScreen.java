package org.example.UI.screens;

import org.example.UI.buttons.MenuButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuScreen extends JPanel {

    private Navigator navigator;
    private JButton cipherScreen;
    private JButton loggerScreen;
    private JButton settingsScreen;
    private JButton simulationScreen;
    private JButton exit;
    private JPanel buttonContainer;

    public MenuScreen(Navigator n) {
        navigator = n;
        cipherScreen = new MenuButton("Cipher");
        loggerScreen = new MenuButton("Logger");
        settingsScreen = new MenuButton("Settings");
        simulationScreen = new MenuButton("Simulation");
        exit = new MenuButton("Exit");
        buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.add(cipherScreen);
        buttonContainer.add(simulationScreen);
        buttonContainer.add(settingsScreen);
        buttonContainer.add(loggerScreen);
        buttonContainer.add(exit);
        this.add(buttonContainer);
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
    }
}
