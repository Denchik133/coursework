package org.example.UI;

import org.example.core.asymmetric.ChatUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserCard extends JPanel {
    private static final int CARD_HEIGHT = 120;
    private static final int CARD_WIDTH = 200;
    private static final int PADING = 25;
    private ChatUser user;
    private Color baseColor;
    private boolean hovered = false;
    private boolean selected = false;
    private UserCardListener listener;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setListener(UserCardListener listener) {
        this.listener = listener;
    }

    public UserCard(ChatUser user) {
        this.user = user;
        this.baseColor = pickColor();
        this.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        initListeners();
    }

    private void initListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (listener != null) {
                    listener.onUserSelected(user);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }
        });
    }

    private static final Color[] PALETTE = {
            new Color(0x4A90E2), // blue
            new Color(0x50E3C2), // mint
            new Color(0xF5A623), // orange
            new Color(0xBD10E0), // purple
            new Color(0x7ED321), // green
            new Color(0xD0021B), // red
            new Color(0x417505), // dark green
            new Color(0x9013FE)  // violet
    };

    private Color pickColor() {
        int hash = Math.abs(user.getName().hashCode());
        return PALETTE[hash % (PALETTE.length)];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBackgroundColor());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        if (selected) {
            g2d.setColor(new Color(0xE2E593));
            g2d.drawRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        }
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(user.getName());
        int textHeight = fm.getHeight();
        g2d.drawString(user.getName(), (CARD_WIDTH - textWidth) / 2, PADING);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        String publicKey = (user.getPublicKey().toString().substring(55, 76) + "...");
        g2d.drawString(publicKey, PADING, PADING * 2 + textHeight);
    }

    private Color getBackgroundColor() {
        if (hovered) {
            return getHoveredColor(35);
        }
        return baseColor;
    }

    private Color getHoveredColor(int delta) {
        int r = limitColor(baseColor.getRed() - delta);
        int g = limitColor(baseColor.getGreen() - delta);
        int b = limitColor(baseColor.getBlue() - delta);
        return new Color(r, g, b);
    }

    private int limitColor(int brightest) {
        if (brightest < 0) {
            return 0;
        }
        if (brightest > 255) {
            return 255;
        }
        return brightest;
    }
}
