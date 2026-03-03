package org.example.UI.screens;

import org.example.UI.LogTableModel;
import org.example.UI.buttons.MainActionButton;
import org.example.logger.EventLogger;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoggerScreen extends MyScreen {
    private Navigator navigator;
    private JButton backButton;
    private JPanel bottomPanel = new JPanel();
    private JTable logsTable;

    public LoggerScreen(Navigator n) {
        navigator = n;
        backButton = new MainActionButton("Back to Menu");
        this.setLayout(new BorderLayout());
        LogTableModel model = new LogTableModel(EventLogger.getInstance().getAllEvents());
        logsTable = new JTable(model);
        logsTable.setShowGrid(false);
        logsTable.setRowHeight(30);
        TableRowSorter<LogTableModel> sorter = new TableRowSorter<>(model);
        logsTable.setRowSorter(sorter);
        this.add(new JScrollPane(logsTable), BorderLayout.CENTER);
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
