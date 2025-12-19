package org.example.UI;

import org.example.core.*;
import org.example.core.exceptions.KeyNotValidException;
import org.example.core.exceptions.WrongCharacterException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private JPanel cesarPanel;
    private JPanel adfgxPanel;
    private JPanel adfgvxPanel;
    private JPanel vigenerePanel;
    private boolean isEncryptModOn = true;
    private final JComboBox<CypherType> comboBox = new JComboBox<>(CypherType.values());
    private final JComboBox<String> comboBox1 = new JComboBox<>(new String[]{"encrypt", "decrypt"});
    private JPanel cardLayoutPanel = new JPanel(new CardLayout());
    private final JTextArea inputArea = new JTextArea();
    private final JTextArea outputArea = new JTextArea();
    private final JButton encryptButton = new JButton("Encrypt");
    private final JButton decryptButton = new JButton("Decrypt");
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private JTextField inputField0;
    private JTextField inputField1;
    private JTextField inputField2;
    private JTextField inputField3;
    private JTextField inputField4;
    private JTextField inputField5;
    private JLabel errorMessage = new JLabel();
    private Map<KeyField, JComponent> map = new HashMap<>();
    private JPanel bottomPanel;

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
        errorMessage = new JLabel("");
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(errorMessage);
        root.setLayout(new BorderLayout());
        inputArea.setLineWrap(true);
        outputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        JScrollPane inputScroll = new JScrollPane(inputArea);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputScroll, outputScroll);
        textPanel.add(splitPane);
        splitPane.setResizeWeight(0.5);
        root.add(topPanel, BorderLayout.NORTH);
        root.add(textPanel, BorderLayout.CENTER);
        root.add(bottomPanel, BorderLayout.SOUTH);
        textPanel.setLayout(new GridLayout(1, 2));
        return root;
    }

    private JPanel buildCesarPanel(){
        JPanel topPanel = new JPanel();
        JLabel hint = new JLabel("Shift:");
        inputField0 = new JTextField();
        map.put(KeyField.CESAR_SHIFT, inputField0);
        topPanel.add(hint);
        topPanel.add(inputField0);
        inputField0.setColumns(12);
        return topPanel;
    }

    private JPanel buildAdfgxPanel(){
        JPanel topPanel = new JPanel();
        JLabel hint = new JLabel("First key:");
        JLabel hint1 = new JLabel("Second key:");
        inputField2 = new JTextField();
        map.put(KeyField.ADFGX_KEY1, inputField2);
        topPanel.add(hint);
        topPanel.add(inputField2);
        inputField1 = new JTextField();
        map.put(KeyField.ADFGX_KEY2, inputField1);
        topPanel.add(hint1);
        topPanel.add(inputField1);
        inputField2.setColumns(12);
        inputField1.setColumns(12);
        return topPanel;
    }

    private JPanel buildAdfgvxPanel() {
        JPanel topPanel = new JPanel();
        JLabel hint = new JLabel("First key:");
        JLabel hint1 = new JLabel("Second key:");
        inputField4 = new JTextField();
        topPanel.add(hint);
        topPanel.add(inputField4);
        map.put(KeyField.ADFGVX_KEY1, inputField4);
        inputField5 = new JTextField();
        topPanel.add(hint1);
        topPanel.add(inputField5);
        map.put(KeyField.ADFGVX_KEY2, inputField5);
        inputField4.setColumns(12);
        inputField5.setColumns(12);
        return topPanel;
    }

    private JPanel buildVigenerePanel() {
        JPanel topPanel = new JPanel();
        JLabel hint = new JLabel("Key:");
        inputField3 = new JTextField();
        map.put(KeyField.VIGENERE_KEY, inputField3);
        topPanel.add(hint);
        topPanel.add(inputField3);
        inputField3.setColumns(12);
        return topPanel;
    }

    private JPanel buildTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        JPanel left = new JPanel();
        JLabel leftLabel = new JLabel("Cypher:");
        JPanel right = new JPanel();
        right.add(comboBox1);
        left.add(leftLabel);
        left.add(comboBox);
        cardLayoutPanel.add(buildCesarPanel(), CypherType.CESAR.name());
        cardLayoutPanel.add(buildAdfgxPanel(), CypherType.ADFGX.name());
        cardLayoutPanel.add(buildAdfgvxPanel(), CypherType.ADFGVX.name());
        cardLayoutPanel.add(buildVigenerePanel(), CypherType.VIGENERE.name());
        topPanel.add(left, BorderLayout.WEST);
        topPanel.add(cardLayoutPanel, BorderLayout.CENTER);
        topPanel.add(right, BorderLayout.EAST);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CypherType type = (CypherType) comboBox.getSelectedItem();
                CardLayout layout = (CardLayout) cardLayoutPanel.getLayout();
                layout.show(cardLayoutPanel, type.name());
                encryptListener.actionPerformed(e);
            }
        });
        return topPanel;
    }

    private void updateOutputArea() throws WrongCharacterException, KeyNotValidException {
        bottomPanel.removeAll();
        bottomPanel.revalidate();
        bottomPanel.repaint();
        for (JComponent component : map.values()) {
            component.setBorder(UIManager.getBorder("TextField.border"));
        }
        CypherType type = (CypherType) comboBox.getSelectedItem();
        String input = inputArea.getText();
        String cesarShift = "";
        String vigenereKey = "";
        String key1 = "";
        String key2 = "";
        switch (type) {
            case CESAR: {
                cesarShift = inputField0.getText();
                break;
            }
            case VIGENERE: {
                vigenereKey = inputField3.getText();
                break;
            }
            case ADFGX: {
                key1 = inputField2.getText();
                key2 = inputField1.getText();
                break;
            }
            case ADFGVX: {
                key1 = inputField4.getText();
                key2 = inputField5.getText();
                break;
            }
        }
        CypherParams params = new CypherParams(cesarShift, vigenereKey, key1, key2);
        Map<KeyField, String> errorsMap = KeyValidator.validate(type, params);
        if (errorsMap.isEmpty()) {
            String result = isEncryptModOn ? CypherService.encrypt(input, params, type) : CypherService.decrypt(input, params, type);
            outputArea.setText(result);
        }
        else {
            for (KeyField keyField : errorsMap.keySet()) {
                bottomPanel.add(new MyErrorLabel(errorsMap.get(keyField)));
                JComponent component = map.get(keyField);
                component.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
            bottomPanel.revalidate();
            bottomPanel.repaint();
        }
    }

    private ActionListener encryptListener;

    private void initListeners() {
        encryptListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateOutputArea();
                } catch (WrongCharacterException ex) {
                    errorMessage.setText(ex.getMessage());
                } catch (KeyNotValidException ex) {
                    errorMessage.setText(ex.getMessage());
                }
            }
        };

        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                encryptListener.actionPerformed(null);
            }
        };

        inputArea.addKeyListener(keyAdapter);
        inputField0.addKeyListener(keyAdapter);
        inputField1.addKeyListener(keyAdapter);
        inputField2.addKeyListener(keyAdapter);
        inputField3.addKeyListener(keyAdapter);

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mode = comboBox1.getSelectedItem().toString();
                switch (mode) {
                    case "encrypt": {
                        if (!isEncryptModOn) {
                            switchTextAreas();
                        }
                        isEncryptModOn = true;
                        break;
                    }
                    case "decrypt": {
                        if (isEncryptModOn) {
                            switchTextAreas();
                        }
                        isEncryptModOn = false;
                        break;
                    }
                }
            }
        });
    }

    private void switchTextAreas() {
        String text = outputArea.getText();
        outputArea.setText(inputArea.getText());
        inputArea.setText(text);
    }
}
