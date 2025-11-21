package org.example.core;

public class Cesar {
    public static String encrypt(String text, int shift) throws WrongCharacterException {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String bAlphabet = alphabet.toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!alphabet.contains(String.valueOf(c)) & !bAlphabet.contains(String.valueOf(c))) {
                throw new WrongCharacterException("Wrong character input text");
            }
            if (Character.isUpperCase(c)) {
                c = getEncryptedChar(c, shift, bAlphabet);
            }
            else {
                c = getEncryptedChar(c, shift, alphabet);
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static char getEncryptedChar(char c, int shift, String alphabet){
        int index = alphabet.indexOf(c);
        return alphabet.charAt((shift + index) % alphabet.length());
    }

    public static String decrypt(String text, int shift){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String bAlphabet = alphabet.toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isUpperCase(c)) {
                c = getDecryptedChar(c, shift, bAlphabet);
            }
            else {
                c = getDecryptedChar(c, shift, alphabet);
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static char getDecryptedChar(char c, int shift, String alphabet) {
        int index = alphabet.indexOf(c);
        while(index - shift < 0){
            shift -= alphabet.length();
        }
        return alphabet.charAt(Math.abs((index - shift)) % alphabet.length());
    }
}
