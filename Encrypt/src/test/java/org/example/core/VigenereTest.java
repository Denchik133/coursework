package org.example.core;

import org.example.core.exceptions.KeyNotValidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VigenereTest {
    @Test
    public void testVigenere() throws KeyNotValidException {
        String text = "abcd";
        String key = "key";
        String encrypted = Vigenere.encrypt(text, key);
        String decrypted = Vigenere.decrypt(encrypted, key);
        assertEquals(text, decrypted);
    }
    @Test
    public void testVigenereWhenMeetsNonAlphabetSymbols() throws KeyNotValidException {
        String text = "abcd adgyhut, 742!";
        String key = "key";
        String encrypted = Vigenere.encrypt(text, key);
        String decrypted = Vigenere.decrypt(encrypted, key);
        assertEquals(text, decrypted);
    }
}
