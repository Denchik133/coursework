package org.example.UI;

import javax.swing.*;
import java.awt.*;

public class MyErrorLabel extends JLabel {
    public MyErrorLabel(String s) {
        super(s);
        setForeground(Color.RED);
        setFont(new Font("Arial", Font.BOLD, 16));
        setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
    }
}
