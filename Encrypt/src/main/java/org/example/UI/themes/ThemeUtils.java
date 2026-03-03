package org.example.UI.themes;

import org.example.UI.buttons.*;

import javax.swing.*;
import java.awt.*;

public class ThemeUtils {

    public static void setTextColorRecursive(Component component) {
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setTextColorRecursive(child);
            }
        }
        component.setForeground(ThemeManager.getCurrentTheme().getMainText());
        if (component instanceof SecondActionButton) {
            component.setForeground(ThemeManager.getCurrentTheme().getSecondActionButtonText());
        }
        else if (component instanceof MainActionButton) {
            component.setForeground(ThemeManager.getCurrentTheme().getMainActionButtonText());
        }
        else if (component instanceof CancelButton) {
            component.setForeground(ThemeManager.getCurrentTheme().getCancelButtonText());
        }
        else if (component instanceof MenuButton) {
            component.setForeground(ThemeManager.getCurrentTheme().getMenuButtonText());
        }
        else if (component instanceof JTextArea || component instanceof JTextField) {
            component.setForeground(ThemeManager.getCurrentTheme().getSecondaryText());
        }
    }

    public static void setBackgroundRecursive(Component component) {
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setBackgroundRecursive(child);
            }
        }
        component.setBackground(ThemeManager.getCurrentTheme().getMainBackground());
        if (component instanceof SecondActionButton) {
            component.setBackground(ThemeManager.getCurrentTheme().getSecondActionButtonBackground());
        }
        else if (component instanceof MainActionButton) {
            component.setBackground(ThemeManager.getCurrentTheme().getMainActionButtonBackground());
        }
        else if (component instanceof CancelButton) {
            component.setBackground(ThemeManager.getCurrentTheme().getCancelButtonBackground());
        }
        else if (component instanceof MenuButton) {
            component.setBackground(ThemeManager.getCurrentTheme().getMenuButtonBackground());
        }
        else if (component instanceof JTextArea || component instanceof JTextField) {
            component.setBackground(ThemeManager.getCurrentTheme().getSecondaryBackground());
        }
        else if (component instanceof MyImageButton) {
            component.setBackground(null);
        }
    }
}
