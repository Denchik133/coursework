package org.example.UI.screens;

import org.example.UI.CipherRestoreHandler;
import org.example.UI.renderers.HistoryCellRenderer;
import org.example.UI.buttons.SecondActionButton;
import org.example.logger.EventLogger;
import org.example.logger.LogCategory;
import org.example.logger.LogLevel;
import org.example.logger.MyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CipherHistoryScreen extends MyScreen {
    private Navigator navigator;
    private JList<MyEvent> lastHourList;
    private DefaultListModel<MyEvent> listModel;
    private JPanel bottomPanel = new JPanel();
    private JButton backButton = new SecondActionButton("Back to Menu");
    private JButton showKeyButton = new SecondActionButton("Show Key");
    private CipherRestoreHandler restoreHandler;
    private EventLogger logger = EventLogger.getInstance();

    public CipherHistoryScreen(Navigator n, CipherRestoreHandler restoreHandler) {
        navigator = n;
        listModel = new DefaultListModel<>();
        lastHourList = new JList<>(listModel);
        this.restoreHandler = restoreHandler;
        lastHourList.setCellRenderer(new HistoryCellRenderer());
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(lastHourList), BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(showKeyButton);
        showKeyButton.setEnabled(false);
        bottomPanel.add(backButton);
        updateListModel();
        initListeners();
    }

    public void updateListModel() {
        listModel.clear();
        listModel.addAll(logger.getAllCipherEvents());
    }

    private void initListeners() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigator.show(Screens.MENU);
            }
        });

        showKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyEvent event = lastHourList.getSelectedValue();
                event.setShowKeyEnabled(!event.isShowKeyEnabled());
                lastHourList.repaint();
            }
        });

        lastHourList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                if (lastHourList.getSelectedValue() == null) {
                    showKeyButton.setEnabled(false);
                }
                else {
                    showKeyButton.setEnabled(true);
                }
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    int index = lastHourList.locationToIndex(e.getPoint());
                    if (index < 0) {
                        return;
                    }
                    MyEvent event = lastHourList.getModel().getElementAt(index);
                    restoreHandler.restoreContextFromEvent(event);
                    navigator.show(Screens.CIPHER);
                    logger.addEvent(new MyEvent(LogCategory.SYSTEM, LogLevel.INFO, "Cipher context was restored"));
                }
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
