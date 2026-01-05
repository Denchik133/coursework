package org.example.core;

import org.example.UI.CypherType;
import org.example.core.exceptions.KeyNotValidException;

import java.util.HashMap;
import java.util.Map;

public class KeyValidator {
    public static Map<KeyField, String> validate(CypherType type, CypherParams params) {
        Map<KeyField, String> map = new HashMap<>();
        switch (type) {
            case CypherType.CESAR -> validateCesar(params, map);
            case CypherType.VIGENERE -> validateVigenere(params, map);
            case CypherType.ADFGX -> validateADFGX(params, map);
            case CypherType.ADFGVX -> validateADFGVX(params, map);
        }
    return map;
    }

    public static void validateWithThrows(CypherType type, CypherParams params) throws KeyNotValidException {
        Map<KeyField, String> map = new HashMap<>();
        switch (type) {
            case CypherType.CESAR -> validateCesar(params, map);
            case CypherType.VIGENERE -> validateVigenere(params, map);
            case CypherType.ADFGX -> validateADFGX(params, map);
            case CypherType.ADFGVX -> validateADFGVX(params, map);
        }
        for (Map.Entry<KeyField, String> entry : map.entrySet()) {
            String errorMessage = entry.getValue();
            throw new KeyNotValidException(errorMessage);
        }
    }

    private static void validateADFGVX(CypherParams params, Map<KeyField, String> map) {
        String ADFGVXkey1 = params.getKey1();
        String ADFGVXkey2 = params.getKey2();
        if (ADFGVXkey1 == null || ADFGVXkey1.length() ==  0) {
            map.put(KeyField.ADFGVX_KEY1, "Key shouldn't be empty");
        }
        if (ADFGVXkey2 == null || ADFGVXkey2.length() ==  0) {
            map.put(KeyField.ADFGVX_KEY2, "Key shouldn't be empty");
        }
        for (char c : ADFGVXkey1.toCharArray()) {
            if (ADFGVX.alphabet.indexOf(c) == -1) {
                map.put(KeyField.ADFGVX_KEY1, "Key contains non-alphabetic characters");
            }
        }
        for (char c : ADFGVXkey2.toCharArray()) {
            if (ADFGVX.alphabet.indexOf(c) == -1) {
                map.put(KeyField.ADFGVX_KEY2, "Key contains non-alphabetic characters");
            }
        }
    }

    private static void validateADFGX(CypherParams params, Map<KeyField, String> map) {
        String ADFGXkey1 = params.getKey1();
        String ADFGXkey2 = params.getKey2();
        if (ADFGXkey1 == null || ADFGXkey1.length() ==  0) {
            map.put(KeyField.ADFGX_KEY1, "Key shouldn't be empty");
        }
        if (ADFGXkey2 == null || ADFGXkey2.length() ==  0) {
            map.put(KeyField.ADFGX_KEY2, "Key shouldn't be empty");
        }
        for (char c : ADFGXkey1.toCharArray()) {
            if (ADFGX.alphabet.indexOf(c) == -1) {
                map.put(KeyField.ADFGX_KEY1, "Key contains non-alphabetic characters");
            }
        }
        for (char c : ADFGXkey2.toCharArray()) {
            if (ADFGX.alphabet.indexOf(c) == -1) {
                map.put(KeyField.ADFGX_KEY2, "Key contains non-alphabetic characters");
            }
        }
    }

    private static void validateVigenere(CypherParams params, Map<KeyField, String> map) {
        String VigenereKey = params.getVigenereKey();
        if (VigenereKey == null || VigenereKey.length() == 0) {
            map.put(KeyField.VIGENERE_KEY, "Key is empty");
        }
        for (char c : VigenereKey.toCharArray()) {
            if (Vigenere.alphabet.indexOf(c) == -1) {
                map.put(KeyField.VIGENERE_KEY, "Key contains non-alphabetic characters");
            }
        }
    }

    private static void validateCesar(CypherParams params, Map<KeyField, String> map) {
        int cesarShift = 0;
        try {
            cesarShift = Integer.parseInt(params.getCesarShift());
            if (cesarShift == 0) {
                map.put(KeyField.CESAR_SHIFT, "Shift must not be 0");
            }
        } catch (NumberFormatException e) {
            map.put(KeyField.CESAR_SHIFT, "Shift must be an integer");
        }
    }
}
