package org.example.core;

public class CipherParams {
    private String cesarShift;
    private String vigenereKey;
    private String key1;
    private String key2;

    public CipherParams(String cesarShift, String vigenereKey, String key1, String key2) {
        this.cesarShift = cesarShift;
        this.vigenereKey = vigenereKey;
        this.key1 = key1;
        this.key2 = key2;
    }

    public String getCesarShift() {
        return cesarShift;
    }

    public String getVigenereKey() {
        return vigenereKey;
    }

    public String getKey1() {
        return key1;
    }

    public String getKey2() {
        return key2;
    }
}
