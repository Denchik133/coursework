package org.example.UI.buttons;

import org.example.UI.themes.ColorUtils;
import org.example.UI.themes.ThemeManager;

import java.awt.*;

public class CancelButton extends MyButton {

    public CancelButton(String text) {
        super(text);
    }

    @Override
    void initColors() {
        baseColor = ThemeManager.getCurrentTheme().getCancelButtonBackground();
        hoverColor = ColorUtils.getBrighterColor(baseColor, 0.15f);
        pressedColor = ColorUtils.getBrighterColor(baseColor, 0.08f);
        disableColor = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 128);
    }
}
