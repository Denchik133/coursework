package org.example.UI;

import org.example.core.Cesar;
import org.example.core.KeyNotValidException;
import org.example.core.WrongCharacterException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private final JTextArea inputArea = new JTextArea();
    private final JTextArea outputArea = new JTextArea();
    private final JButton encryptButton = new JButton("Encrypt");
    private final JButton decryptButton = new JButton("Decrypt");
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private JTextField inputField;
    private JTextField inputField1;

    public MainFrame() {
        tabbedPane.addTab("Text", buildTextTab());
        this.add(tabbedPane, BorderLayout.CENTER);
        initListeners();
        this.setSize(800, 600);
    }

    private JPanel buildTextTab() {
        JPanel root = new JPanel();
        JPanel topPanel = buildTopPanel();
        JPanel textPanel = new JPanel();
        textPanel.add(inputArea);
        textPanel.add(outputArea);
        root.add(topPanel);
        root.add(textPanel);
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        textPanel.setLayout(new GridLayout(1, 2));
        return root;
    }

    private CypherType selectedType = CypherType.CESAR;

    private JPanel buildTopPanel() {
        JPanel topPanel = new JPanel();
        switch (selectedType) {
            case CESAR: {
                JLabel hint = new JLabel("Shift:");
                inputField = new JTextField();
                topPanel.add(hint);
                topPanel.add(inputField);
                topPanel.add(encryptButton);
                topPanel.add(decryptButton);
                inputField.setColumns(12);
                break;
            }
            case ADFGX: {
                JLabel hint = new JLabel("First key:");
                JLabel hint1 = new JLabel("Second key:");
                inputField = new JTextField();
                topPanel.add(hint);
                topPanel.add(inputField);
                inputField1 = new JTextField();
                topPanel.add(hint1);
                topPanel.add(inputField1);
                topPanel.add(encryptButton);
                topPanel.add(decryptButton);
                inputField.setColumns(12);
                inputField1.setColumns(12);
                break;
            }
        }
        return topPanel;
    }

    private void initListeners() {
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = inputField.getText();
                    int shift = Integer.parseInt(text);
                    String inputText = inputArea.getText();
                    if (inputText.equals("")) {
                        throw new WrongCharacterException("Enter text");
                    }
                    String outputText = Cesar.encrypt(inputText, shift);
                    outputArea.setText(outputText);
                } catch (NumberFormatException ex) {
                    MyDialogWindow dialog = new MyDialogWindow(MainFrame.this, "Shift must be an integer");
                    dialog.setVisible(true);
                }
                catch (WrongCharacterException ex){
                    MyDialogWindow dialog = new MyDialogWindow(MainFrame.this, ex.getMessage());
                    dialog.setVisible(true);
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = inputField.getText();
                    int shift = Integer.parseInt(text);
                    String inputText = inputArea.getText();
                    if (inputText.equals("")) {
                        throw new WrongCharacterException("Enter text");
                    }
                    String outputText = Cesar.decrypt(inputText, shift);
                    outputArea.setText(outputText);
                } catch (NumberFormatException ex) {
                    MyDialogWindow dialog = new MyDialogWindow(MainFrame.this, "Shift must be an integer");
                    dialog.setVisible(true);
                }
                catch (WrongCharacterException ex){
                    MyDialogWindow dialog = new MyDialogWindow(MainFrame.this, ex.getMessage());
                    dialog.setVisible(true);
                }
            }
        });
    }
}
