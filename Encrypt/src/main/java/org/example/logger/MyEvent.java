package org.example.logger;

import org.example.core.CipherType;
import org.example.core.CipherParams;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MyEvent implements Serializable {
    private LogLevel logLevel;
    private LogCategory logCategory;
    private CipherParams cipherParams;
    private CipherType cipherType;
    private String cipherPayLoad;
    private String text;
    private LocalDateTime timestamp;
    private boolean encrypt;
    private boolean showKeyEnabled;
    private static final long serialVersionUID = 1L;
    private static final String RESET = "\u001B[0m";
    private static final String GREY = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";

    public boolean isEncrypt() {
        return encrypt;
    }

    public CipherType getCipherType() {
        return cipherType;
    }

    public String getCipherPayLoad() {
        return cipherPayLoad;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public CipherParams getCipherParams() {
        return cipherParams;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public LogCategory getLogCategory() {
        return logCategory;
    }

    public String getText() {
        return text;
    }

    public boolean isShowKeyEnabled() {
        return showKeyEnabled;
    }

    public void setShowKeyEnabled(boolean showKeyEnabled) {
        this.showKeyEnabled = showKeyEnabled;
    }

    private String getAnsiColor() {
        switch (logCategory) {
            case CIPHER -> {
                return GREEN;
            }
            case SYSTEM -> {
                return GREY;
            }
            case SIMULATION -> {
                return CYAN;
            }
        }
        return RESET;
    }

    public MyEvent(LogCategory logCategory, LogLevel logLevel, String text) {
        timestamp = LocalDateTime.now();
        this.logCategory = logCategory;
        this.logLevel = logLevel;
        this.text = text;
    }

    public MyEvent(LogCategory logCategory, LogLevel logLevel, String text, CipherParams params, CipherType cipherType, String cipherPayLoad, boolean encrypt) {
        timestamp = LocalDateTime.now();
        this.logCategory = logCategory;
        this.logLevel = logLevel;
        this.text = text;
        this.cipherParams = params;
        this.cipherType = cipherType;
        this.cipherPayLoad = cipherPayLoad;
        this.encrypt = encrypt;
    }

    @Override
    public String toString() {
        return getAnsiColor() + timestamp + " " + logCategory + " " + logLevel + " " + text + RESET;
    }
}
