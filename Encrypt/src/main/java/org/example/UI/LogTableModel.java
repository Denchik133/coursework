package org.example.UI;

import org.example.logger.MyEvent;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LogTableModel extends AbstractTableModel {
    private List<MyEvent> events;
    private final String array[] = {"Time", "Level", "Category", "Text"};
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");

    public LogTableModel(List<MyEvent> events) {
        this.events = events;
    }

    @Override
    public int getRowCount() {
        return events.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MyEvent e = events.get(rowIndex);
        return switch(columnIndex) {
            case 0 -> e.getTimestamp().format(formatter);
            case 1 -> e.getLogLevel();
            case 2 -> e.getLogCategory();
            case 3 -> e.getText();
            default -> "";
        };
    }

    @Override
    public String getColumnName(int column) {
        return array[column];
    }
}
