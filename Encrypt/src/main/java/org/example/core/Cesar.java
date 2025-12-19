package org.example.core;

import org.example.UI.CypherType;
import org.example.core.exceptions.KeyNotValidException;
import org.example.core.exceptions.WrongCharacterException;

public class Cesar {
    public static String encrypt(String text, String shift) throws WrongCharacterException, KeyNotValidException {
        KeyValidator.validateWithThrows(CypherType.CESAR, new CypherParams(shift, null, null, null));
        int shiftNumber = Integer.parseInt(shift);
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String bAlphabet = alphabet.toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!alphabet.contains(String.valueOf(c)) & !bAlphabet.contains(String.valueOf(c))) {
                throw new WrongCharacterException("Wrong character input text");
            }
            if (Character.isUpperCase(c)) {
                c = getEncryptedChar(c, shiftNumber, bAlphabet);
            }
            else {
                c = getEncryptedChar(c, shiftNumber, alphabet);
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static char getEncryptedChar(char c, int shift, String alphabet){
        int index = alphabet.indexOf(c);
        return alphabet.charAt((shift + index) % alphabet.length());
    }

    public static String decrypt(String text, String shift){
        int shiftNumber = Integer.parseInt(shift);
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String bAlphabet = alphabet.toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isUpperCase(c)) {
                c = getDecryptedChar(c, shiftNumber, bAlphabet);
            }
            else {
                c = getDecryptedChar(c, shiftNumber, alphabet);
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
