package org.example.UI;

import org.example.UI.buttons.MenuButton;
import org.example.UI.screens.*;
import org.example.core.*;
import org.example.core.asymmetric.ChatUser;
import org.example.core.asymmetric.MessageBus;
import org.example.core.asymmetric.exceptions.ChatUserException;
import org.example.core.exceptions.KeyNotValidException;
import org.example.core.exceptions.WrongCharacterException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame implements Navigator {
    private CipherScreen cipherScreen;
    private LoggerScreen loggerScreen;
    private MenuScreen menuScreen;
    private SettingsScreen settingsScreen;
    private SimulationScreen simulationScreen;
    private JPanel cardLayoutPanel = new JPanel(new CardLayout());

    public MainFrame() {
        this.setSize(900, 650);
        this.setLocationRelativeTo(null);
        menuScreen = new MenuScreen(this);
        cipherScreen = new CipherScreen(this);
        loggerScreen = new LoggerScreen(this);
        settingsScreen = new SettingsScreen(this);
        simulationScreen = new SimulationScreen(this);
        cardLayoutPanel.add(menuScreen, Screens.MENU);
        cardLayoutPanel.add(cipherScreen, Screens.CIPHER);
        cardLayoutPanel.add(loggerScreen, Screens.LOGGER);
        cardLayoutPanel.add(settingsScreen, Screens.SETTINGS);
        cardLayoutPanel.add(simulationScreen, Screens.SIMULATION);
        this.add(cardLayoutPanel);
        show(Screens.MENU);
        this.setVisible(true);
    }

    @Override
    public void show(String screenName) {
        CardLayout layout = (CardLayout) cardLayoutPanel.getLayout();
        layout.show(cardLayoutPanel, screenName);
    }
}
