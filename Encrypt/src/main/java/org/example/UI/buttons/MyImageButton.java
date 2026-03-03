package org.example.UI.buttons;

import javax.swing.*;

public class MyImageButton extends JButton {

    public MyImageButton(ImageIcon icon) {
        super(icon);
        setup();
    }

    private void setup() {
        setFocusable(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }
}
