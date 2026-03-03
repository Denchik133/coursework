package org.example.UI;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class OverlayScrollWrapper extends JLayeredPane {
    private JScrollPane scrollPane;
    private JPanel overlayPanel;

    public OverlayScrollWrapper(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        createOverlayPanel();
        this.add(scrollPane, JLayeredPane.DEFAULT_LAYER);
        this.add(overlayPanel, JLayeredPane.PALETTE_LAYER);
        this.setLayout(null);
        initListeners();
    }

    private void createOverlayPanel() {
        overlayPanel = new JPanel();
        overlayPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        overlayPanel.setOpaque(false);
    }

    public void addButtons(List<JButton> buttons) {
        overlayPanel.removeAll();
        for (JButton button : buttons) {
            overlayPanel.add(button);
        }
        overlayPanel.revalidate();
        overlayPanel.repaint();
    }

    private void initListeners() {
    }

    @Override
    public void doLayout() {
        scrollPane.setBounds(0, 0, getWidth(), getHeight());
        overlayPanel.setBounds(0, 0, getWidth(), overlayPanel.getPreferredSize().height);
    }
}
