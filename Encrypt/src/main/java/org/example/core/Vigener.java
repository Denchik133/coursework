package org.example.core;

public class Vigener {
    private final static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public static String encrypt(String text, String key) throws KeyNotValidException {
        for (int i = 0; i < key.length(); i++) {
            if (!alphabet.contains(String.valueOf(key.charAt(i)))) {
                throw new KeyNotValidException("Key contains a symbol(-s) that does not belong to the alphabet");
            }
        }
        StringBuilder normalisedKey = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            normalisedKey.append(key.charAt(i % key.length()));
        }
        String newKey = normalisedKey.toString();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!alphabet.contains(String.valueOf(c))) {
                result.append(c);
                continue;
            }
            int shift = alphabet.indexOf(newKey.charAt(i));
            int newIndex = (alphabet.indexOf(c) + shift) % alphabet.length();
            result.append(alphabet.charAt(newIndex));
        }
        return result.toString();
    }

    public static String decrypt(String text, String key) throws KeyNotValidException {
        for (int i = 0; i < key.length(); i++) {
            if (!alphabet.contains(String.valueOf(key.charAt(i)))) {
                throw new KeyNotValidException("Key contains a symbol(-s) that does not belong to the alphabet");
            }
        }
        StringBuilder normalisedKey = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            normalisedKey.append(key.charAt(i % key.length()));
        }
        String newKey = normalisedKey.toString();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!alphabet.contains(String.valueOf(c))) {
                result.append(c);
                continue;
            }
            int shift = alphabet.indexOf(newKey.charAt(i));
            int newIndex = (alphabet.indexOf(c) - shift);
            if (newIndex < 0) {
                newIndex += alphabet.length();
            }
            result.append(alphabet.charAt(newIndex));
        }
        return result.toString();
    }
}
