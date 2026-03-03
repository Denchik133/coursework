package org.example.UI.themes;

public class ThemeManager {
    private static Theme currentTheme = new DarkTheme();

    private ThemeManager() {

    }

    public static Theme getCurrentTheme() {
        return currentTheme;
    }

    public static void setCurrentTheme(Theme currentTheme) {
        ThemeManager.currentTheme = currentTheme;
    }
}
