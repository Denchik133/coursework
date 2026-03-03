package org.example.UI.renderers;

import org.example.core.CipherParams;
import org.example.logger.MyEvent;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class HistoryCellRenderer implements ListCellRenderer<MyEvent> {
    private JPanel root;
    private JLabel time;
    private JLabel key = new JLabel("Key: ");
    private JTextArea text;
    private Color selectedColor = new Color((0x96C8C8C8), true);
    private Color baseColor = new Color(0xD3D3D3);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
    private JPanel container;


    public HistoryCellRenderer() {
        root = new JPanel();
        time = new JLabel();
        text = new JTextArea();
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel mid = new JPanel(new BorderLayout());
        text.setOpaque(false);
        right.setOpaque(false);
        left.setOpaque(false);
        mid.setOpaque(false);
        container.setOpaque(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        left.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        right.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mid.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        root.setLayout(new BorderLayout());
        root.add(left, BorderLayout.WEST);
        root.add(right, BorderLayout.EAST);
        root.add(mid, BorderLayout.CENTER);
        left.add(Box.createVerticalGlue());
        left.add(time);
        left.add(Box.createVerticalGlue());
        right.add(container);
        mid.add(text);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends MyEvent> list, MyEvent value, int index, boolean isSelected, boolean cellHasFocus) {
        container.removeAll();
        time.setText(value.getTimestamp().format(formatter));
        text.setText(value.getCipherPayLoad());
        if (value.isShowKeyEnabled()) {
            CipherParams params = value.getCipherParams();
            if (params.getCesarShift() != null) {
                key.setText("Shift: ");
                container.add(key);
                container.add(new JLabel(params.getCesarShift()));
            }
            else if (params.getVigenereKey() != null) {
                key.setText("Key: ");
                container.add(key);
                container.add(new JLabel(params.getVigenereKey()));
            }
            else if (params.getKey1() != null && params.getKey2() != null) {
                key.setText("Keys: ");
                container.add(key);
                container.add(new JLabel(params.getKey1()));
                container.add(new JLabel(params.getKey2()));
            }
        }
        else {
            container.add(new JLabel("*****"));
        }
        root.setBackground(isSelected ? selectedColor : baseColor);
        root.setPreferredSize(new Dimension(list.getWidth(), 95));
        return root;
    }
}
