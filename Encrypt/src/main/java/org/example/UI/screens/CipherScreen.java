package org.example.UI.screens;

import org.example.UI.OverlayScrollWrapper;
import org.example.UI.UIContext;
import org.example.UI.buttons.MyImageButton;
import org.example.UI.themes.ThemeManager;
import org.example.core.CipherType;
import org.example.UI.MyErrorLabel;
import org.example.UI.buttons.SecondActionButton;
import org.example.core.CipherParams;
import org.example.core.CipherService;
import org.example.core.KeyField;
import org.example.core.KeyValidator;
import org.example.core.exceptions.KeyNotValidException;
import org.example.core.exceptions.WrongCharacterException;
import org.example.logger.EventLogger;
import org.example.logger.LogCategory;
import org.example.logger.LogLevel;
import org.example.logger.MyEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CipherScreen extends MyScreen {
    private Navigator navigator;
    private EventLogger logger = EventLogger.getInstance();
    private Map<KeyField, JComponent> map = new HashMap<>();
    private JPanel cesarPanel;
    private JPanel adfgxPanel;
    private JPanel adfgvxPanel;
    private JPanel vigenerePanel;
    private JPanel bottomPanel;
    private JPanel errorPanel;
    private JPanel buttonPanel;
    private JPanel cardLayoutPanel = new JPanel(new CardLayout());
    private JTextField inputField0;
    private JTextField inputField1;
    private JTextField inputField2;
    private JTextField inputField3;
    private JTextField inputField4;
    private JTextField inputField5;
    private final JTextArea inputArea = new JTextArea();
    private final JTextArea outputArea = new JTextArea();
    private JLabel errorMessage = new JLabel();
    private boolean isEncryptModOn = true;
    private final JComboBox<CipherType> comboBox = new JComboBox<>(CipherType.values());
    private static final String ENCRYPT = "encrypt";
    private static final String DECRYPT = "decrypt";
    private final JComboBox<String> comboBox1 = new JComboBox<>(new String[]{ENCRYPT, DECRYPT});
    private ActionListener encryptListener;
    private JButton backButton = new SecondActionButton("Back to Menu");
    private JButton saveButton = new SecondActionButton("Save");
    private OverlayScrollWrapper inputAreaOverlay;
    private OverlayScrollWrapper outputAreaOverlay;

    public CipherScreen (Navigator n) {
        navigator = n;
        buildPanel();
        initListeners();
    }

    private void buildPanel() {
        JPanel topPanel = buildTopPanel();
        JPanel textPanel = new JPanel();
        errorMessage = new JLabel("");
        bottomPanel = new JPanel();
        errorPanel = new JPanel();
        buttonPanel = new JPanel();
        errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.Y_AXIS));
        errorPanel.add(errorMessage);
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(errorPanel);
        bottomPanel.add(buttonPanel);
        this.setLayout(new BorderLayout());
        inputArea.setLineWrap(true);
        outputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        JScrollPane inputScroll = new JScrollPane(inputArea);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        inputAreaOverlay = new OverlayScrollWrapper(inputScroll);
        outputAreaOverlay = new OverlayScrollWrapper(outputScroll);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputAreaOverlay, outputAreaOverlay);
        textPanel.add(splitPane);
        splitPane.setResizeWeight(0.5);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(textPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
        textPanel.setLayout(new GridLayout(1, 2));
        buildOverlayPanels();
    }

    private void buildOverlayPanels() {
        // Input area overlay
        ImageIcon iconClear = UIContext.getClearIcon();
        JButton clearButton = new MyImageButton(iconClear);
        LinkedList<JButton> list = new LinkedList<>();
        list.add(clearButton);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputArea.setText("");
            }
        });
        inputAreaOverlay.addButtons(list);
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
        JLabel leftLabel = new JLabel("Cipher:");
        JPanel right = new JPanel();
        right.add(comboBox1);
        left.add(leftLabel);
        left.add(comboBox);
        cardLayoutPanel.add(buildCesarPanel(), CipherType.CESAR.name());
        cardLayoutPanel.add(buildAdfgxPanel(), CipherType.ADFGX.name());
        cardLayoutPanel.add(buildAdfgvxPanel(), CipherType.ADFGVX.name());
        cardLayoutPanel.add(buildVigenerePanel(), CipherType.VIGENERE.name());
        topPanel.add(left, BorderLayout.WEST);
        topPanel.add(cardLayoutPanel, BorderLayout.CENTER);
        topPanel.add(right, BorderLayout.EAST);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CipherType type = (CipherType) comboBox.getSelectedItem();
                CardLayout layout = (CardLayout) cardLayoutPanel.getLayout();
                layout.show(cardLayoutPanel, type.name());
                encryptListener.actionPerformed(e);
            }
        });
        return topPanel;
    }

    private void updateOutputArea() throws WrongCharacterException, KeyNotValidException {
        errorPanel.removeAll();
        errorPanel.revalidate();
        errorPanel.repaint();
        for (JComponent component : map.values()) {
            component.setBorder(UIManager.getBorder("TextField.border"));
        }
        CipherType type = (CipherType) comboBox.getSelectedItem();
        String input = inputArea.getText();
        CipherParams params = buildCipherParams(type);
        Map<KeyField, String> errorsMap = KeyValidator.validate(type, params);
        if (errorsMap.isEmpty()) {
            String result = isEncryptModOn ? CipherService.encrypt(input, params, type) : CipherService.decrypt(input, params, type);
            outputArea.setText(result);
            saveButton.setEnabled(true);
        }
        else {
            for (KeyField keyField : errorsMap.keySet()) {
                errorPanel.add(new MyErrorLabel(errorsMap.get(keyField)));
                JComponent component = map.get(keyField);
                component.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
            saveButton.setEnabled(false);
            bottomPanel.revalidate();
            bottomPanel.repaint();
        }
    }

    private CipherParams buildCipherParams(CipherType type) {
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
        CipherParams params = new CipherParams(cesarShift, vigenereKey, key1, key2);
        return params;
    }


    private void initListeners() {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigator.show(Screens.MENU);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CipherType type = (CipherType) comboBox.getSelectedItem();
                String input = inputArea.getText();
                CipherParams params = buildCipherParams(type);
                logger.addEvent(new MyEvent(LogCategory.CIPHER, LogLevel.INFO, "Encryption completed", params, type, input, comboBox1.getSelectedItem().equals(ENCRYPT)));
            }
        });

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
        inputField4.addKeyListener(keyAdapter);
        inputField5.addKeyListener(keyAdapter);

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

    public void restoreContext(MyEvent event) {
        if (event.isEncrypt()) {
            comboBox1.setSelectedItem(ENCRYPT);
        }
        else {
            comboBox1.setSelectedItem(DECRYPT);
        }
        comboBox.setSelectedItem(event.getCipherType());
        if (event.getCipherParams().getCesarShift() != null) {
            inputField0.setText(event.getCipherParams().getCesarShift());
        }
        if (event.getCipherParams().getVigenereKey() != null) {
            inputField3.setText(event.getCipherParams().getVigenereKey());
        }
        if (event.getCipherType() == CipherType.ADFGX) {
            inputField2.setText(event.getCipherParams().getKey1());
            inputField1.setText(event.getCipherParams().getKey2());
        }
        else if (event.getCipherType() == CipherType.ADFGVX) {
            inputField4.setText(event.getCipherParams().getKey1());
            inputField5.setText(event.getCipherParams().getKey2());
        }
        inputArea.setText(event.getCipherPayLoad().toString());
        try {
            updateOutputArea();
        } catch (WrongCharacterException | KeyNotValidException e) {
            e.printStackTrace();
            logger.addEvent(new MyEvent(LogCategory.SYSTEM, LogLevel.ERROR, "Some error with restoring cipher context"));
        }
    }



    @Override
    protected void applyTheme() {
        super.applyTheme();
    }

    @Override
    protected void updateData() {

    }
}
