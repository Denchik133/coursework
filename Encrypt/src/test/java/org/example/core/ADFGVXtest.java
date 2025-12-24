package org.example.core;

import org.example.core.exceptions.KeyNotValidException;
import org.example.core.exceptions.WrongCharacterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ADFGVXtest {
    @Test
    public void testADFGVX() throws WrongCharacterException, KeyNotValidException {
        String text = "abcd";
        String key1 = "key";
        String key2 = "bad";
        String encrypted = ADFGVX.encrypt(text, key1, key2);
        String decrypted = ADFGVX.decrypt(encrypted, key1, key2);
        assertEquals(text, decrypted);
    }
    @Test
    public void testADFGXKey1() {
        String text = "abcd";
        String key1 = "key#!";
        String key2 = "bad";
        assertThrows(KeyNotValidException.class, () -> ADFGX.encrypt(text, key1, key2));
    }
}
