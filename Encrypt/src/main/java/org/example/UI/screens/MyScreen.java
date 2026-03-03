package org.example.UI.screens;

import org.example.UI.buttons.*;
import org.example.UI.themes.ThemeManager;

import javax.swing.*;
import java.awt.*;

import static org.example.UI.themes.ThemeUtils.setBackgroundRecursive;
import static org.example.UI.themes.ThemeUtils.setTextColorRecursive;

public abstract class MyScreen extends JPanel {

    public void updateView() {
        applyTheme();
        updateData();
    }

    protected void applyTheme() {
        setTextColorRecursive(this);
        setBackgroundRecursive(this);
    }
    protected abstract void updateData();
}
