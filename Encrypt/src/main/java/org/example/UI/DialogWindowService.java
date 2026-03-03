package org.example.UI;

import org.example.core.asymmetric.ChatUser;
import org.example.logger.EventLogger;
import org.example.logger.LogCategory;
import org.example.logger.LogLevel;
import org.example.logger.MyEvent;

import java.util.ArrayList;
import java.util.List;

public class DialogWindowService {
    private List<ChatDialogWindow> dialogWindows;
    private EventLogger eventLogger = EventLogger.getInstance();
    private static DialogWindowService dialogWindowService;

    private DialogWindowService() {
        dialogWindows = new ArrayList<ChatDialogWindow>();
    }

    public static DialogWindowService getInstance() {
        if (dialogWindowService == null) {
            dialogWindowService = new DialogWindowService();
        }
        return dialogWindowService;
    }

    public void updateAllWindows() {
        for (ChatDialogWindow window : dialogWindows) {
            window.onUpdate();
        }
        eventLogger.addEvent(new MyEvent(LogCategory.SYSTEM, LogLevel.INFO, "All Dialog Windows Updated"));
    }

//    public void addWindow(ChatDialogWindow window) throws Exception {
//        if (dialogWindows.contains(window)) {
//            throw new Exception("This window already exists");
//        }
//        dialogWindows.add(window);
//    }

    public void show(ChatUser user) {
        for (ChatDialogWindow window : dialogWindows) {
            if (window.getChatUser().equals(user)) {
                window.setVisible(true);
                return;
            }
        }
        ChatDialogWindow window = new ChatDialogWindow(user);
        window.setVisible(true);
        dialogWindows.add(window);
    }

    public void hide(ChatUser user) {
        for (ChatDialogWindow window : dialogWindows) {
            if (window.getChatUser().equals(user)) {
                window.setVisible(false);
                return;
            }
        }
    }

    public void setupTheme() {
        for (ChatDialogWindow window : dialogWindows) {
            window.applyTheme();
        }
    }
}
