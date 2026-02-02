package org.example.UI.buttons;

import java.awt.*;

public class MenuButton extends MyButton {

    public MenuButton(String text) {
        super(text);
    }

    @Override
    void initColors() {
        baseColor = new Color(255, 255, 255);
        hoverColor = new Color(200, 200, 200, 65);
        pressedColor = new Color(200, 200, 200, 150);
        disableColor = new Color(0, 0, 0);
    }
}
