package org.example.UI;

import org.example.logger.MyEvent;

public interface CipherRestoreHandler {

    void restoreContextFromEvent(MyEvent event);
}
