package org.example.UI;

import org.example.UI.themes.Theme;
import org.example.UI.themes.ThemeManager;

import javax.swing.*;

public class UIContext {
    private static final int SMALL_PADDING = 7;
    private static final int MEDIUM_PADDING = 12;
    private static final int LARGE_PADDING = 15;

    private UIContext() {

    }

    public static Theme getTheme() {
        return ThemeManager.getCurrentTheme();
    }

    public static ImageIcon getClearIcon() {
        return IconFactory.getClearIcon(getTheme());
    }

    public static ImageIcon getMessageIcon() {
        return IconFactory.getMessageIcon(getTheme());
    }

    public static ImageIcon getEncryptedMessageIcon() {
        return IconFactory.getEncryptedMessageIcon(getTheme());
    }

    public static ImageIcon getUserIcon() {
        return IconFactory.getUserIcon(getTheme());
    }

    public static int getSmallPadding() {
        return SMALL_PADDING;
    }

    public static int getMediumPadding() {
        return MEDIUM_PADDING;
    }

    public static int getLargePadding() {
        return LARGE_PADDING;
    }

}
