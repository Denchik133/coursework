package org.example.UI.themes;

import java.awt.*;

public class DarkTheme implements Theme {
    @Override
    public Color getMainBackground() {
        return new Color(30, 30, 30);
    }

    @Override
    public Color getMainText() {
        return new Color(0xE8DAB4);
    }

    @Override
    public Color getSecondaryBackground() {
        return new Color(45, 45 ,45);
    }

    @Override
    public Color getSecondaryText() {
        return new Color(0xECDAAB);
    }

    @Override
    public Color getMainActionButtonBackground() {
        return new Color(0x531463);
    }

    @Override
    public Color getMainActionButtonText() {
        return new Color(0xEAD18F);
    }

    @Override
    public Color getSecondActionButtonBackground() {
        return new Color(50, 50, 50);
    }

    @Override
    public Color getSecondActionButtonText() {
        return getSecondaryText();
    }

    @Override
    public Color getCancelButtonBackground() {
        return new Color(0x631420);
    }

    @Override
    public Color getCancelButtonText() {
        return new Color(0xD5D5D5);
    }

    @Override
    public Color getMenuButtonBackground() {
        return new Color(55, 55, 55);
    }

    @Override
    public Color getMenuButtonText() {
        return new Color(0xFFC67F2D, true);
    }
}
