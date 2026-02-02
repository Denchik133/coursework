package org.example.logger;

import org.example.core.CipherParams;

import java.time.LocalDateTime;

public class MyEvent {

    private LogLevel logLevel;
    private LogCategory logCategory;
    private CipherParams cipherParams;
    private String cipherPayLoad;
    private String text;
    private LocalDateTime timestamp;
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";

    private String getAnsiColor() {
        switch (logCategory) {
            case CIPHER -> {
                return RED;
            }
            case SYSTEM -> {
                return GREEN;
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

    public MyEvent(LogCategory logCategory, LogLevel logLevel, String text, CipherParams params, String cipherPayLoad) {
        timestamp = LocalDateTime.now();
        this.logCategory = logCategory;
        this.logLevel = logLevel;
        this.text = text;
        this.cipherParams = params;
        this.cipherPayLoad = cipherPayLoad;
    }

    @Override
    public String toString() {
        return getAnsiColor() + timestamp + " " + logCategory + " " + logLevel + " " + text + RESET;
    }
}
