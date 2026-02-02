package org.example.logger;

import java.util.ArrayList;
import java.util.List;

public class EventLogger {
    private static EventLogger instance;
    private List<MyEvent> events;
    private boolean consoleLogOn = true;

    public static EventLogger getInstance() {
        if (instance == null) {
            instance = new EventLogger();
        }
        return instance;
    }

    private EventLogger() {
        events = new ArrayList<>();
    }

    public void addEvent(MyEvent event) {
        events.add(event);
        if (consoleLogOn) {
            System.out.println(event);
        }
    }
}
