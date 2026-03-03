package org.example.logger;

import org.example.data.DataInterface;
import org.example.data.MyDataException;
import org.example.data.SerialisationRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EventLogger {
    private static EventLogger instance;
    private List<MyEvent> events;
    private boolean consoleLogOn = true;
    private DataInterface<List<MyEvent>> repository;

    public static EventLogger getInstance() {
        if (instance == null) {
            instance = new EventLogger();
        }
        return instance;
    }

    private EventLogger() {
        events = new ArrayList<>();
        repository = new SerialisationRepository<>("Logger.ser");
        try {
           List<MyEvent> list = repository.loadData();
           events.addAll(list);
        } catch (MyDataException e) {
            e.printStackTrace();
            System.err.println("Logger.ser not found");
        }
    }

    public List<MyEvent> getAllEvents() {
        return events;
    }

    public void addEvent(MyEvent event) {
        events.add(event);
        if (consoleLogOn) {
            System.out.println(event);
        }
    }

    public void saveLogs() throws MyDataException {
        repository.saveData(events);
    }

    public Collection<? extends MyEvent> getAllCipherEvents() {
        List<MyEvent> list = new ArrayList<>();
        for (MyEvent event : events) {
            if (event.getLogCategory().equals(LogCategory.CIPHER)) {
                list.add(event);
            }
        }
        return list;
    }
}
