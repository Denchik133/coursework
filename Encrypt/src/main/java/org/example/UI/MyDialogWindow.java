package org.example.UI;

import javax.swing.*;

public class MyDialogWindow extends javax.swing.JDialog {
    public MyDialogWindow(java.awt.Frame parent, String text) {
        super(parent, true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JLabel textLabel = new JLabel(text);
        this.add(textLabel);
    }
}
