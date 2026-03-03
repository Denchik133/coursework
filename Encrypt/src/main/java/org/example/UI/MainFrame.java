package org.example.UI;

import org.example.UI.buttons.MenuButton;
import org.example.UI.screens.*;
import org.example.core.*;
import org.example.core.asymmetric.ChatUser;
import org.example.core.asymmetric.MessageBus;
import org.example.core.asymmetric.exceptions.ChatUserException;
import org.example.core.exceptions.KeyNotValidException;
import org.example.core.exceptions.WrongCharacterException;
import org.example.data.MyDataException;
import org.example.logger.EventLogger;
import org.example.logger.MyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame implements Navigator, CipherRestoreHandler {
    private CipherScreen cipherScreen;
    private LoggerScreen loggerScreen;
    private MenuScreen menuScreen;
    private SettingsScreen settingsScreen;
    private SimulationScreen simulationScreen;
    private CipherHistoryScreen cipherHistoryScreen;
    private JPanel cardLayoutPanel = new JPanel(new CardLayout());
    private HashMap<String, MyScreen> screens = new HashMap<>();

    public MainFrame() {
        this.setSize(900, 650);
        this.setLocationRelativeTo(null);
        menuScreen = new MenuScreen(this);
        cipherScreen = new CipherScreen(this);
        loggerScreen = new LoggerScreen(this);
        settingsScreen = new SettingsScreen(this);
        simulationScreen = new SimulationScreen(this);
        cipherHistoryScreen = new CipherHistoryScreen(this, this);
        cardLayoutPanel.add(menuScreen, Screens.MENU);
        screens.put(Screens.MENU, menuScreen);
        cardLayoutPanel.add(cipherScreen, Screens.CIPHER);
        screens.put(Screens.CIPHER, cipherScreen);
        cardLayoutPanel.add(loggerScreen, Screens.LOGGER);
        screens.put(Screens.LOGGER, loggerScreen);
        cardLayoutPanel.add(settingsScreen, Screens.SETTINGS);
        screens.put(Screens.SETTINGS, settingsScreen);
        cardLayoutPanel.add(simulationScreen, Screens.SIMULATION);
        screens.put(Screens.SIMULATION, simulationScreen);
        cardLayoutPanel.add(cipherHistoryScreen, Screens.CIPHER_HISTORY);
        screens.put(Screens.CIPHER_HISTORY, cipherHistoryScreen);
        this.add(cardLayoutPanel);
        show(Screens.MENU);
        this.setVisible(true);
    }

    @Override
    public void show(String screenName) {
        try {
            EventLogger.getInstance().saveLogs();
        } catch (MyDataException ex) {
            JOptionPane.showMessageDialog(this, "Couldn't save");
            ex.printStackTrace();
        }
        CardLayout layout = (CardLayout) cardLayoutPanel.getLayout();
        MyScreen screen = screens.get(screenName);
        screen.updateView();
        layout.show(cardLayoutPanel, screenName);
    }

    @Override
    public void restoreContextFromEvent(MyEvent event) {
        cipherScreen.restoreContext(event);
    }
}
