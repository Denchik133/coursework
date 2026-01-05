package org.example.core;

import org.example.core.exceptions.KeyNotValidException;
import org.example.core.exceptions.WrongCharacterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CesarTest {
    @Test
    public void testCesar() throws WrongCharacterException, KeyNotValidException {
        String text = "abcd";
        String shift = "3";
        String encrypted = Cesar.encrypt(text, shift);
        String decrypted = Cesar.decrypt(encrypted, shift);
        assertEquals(text, decrypted);
    }
    @Test
    public void testEncrypt_throwsExceptionOnWrongCharacter() {
        String text = "abcd, ";
        String shift = "3";
        assertThrows(WrongCharacterException.class, () -> Cesar.encrypt(text, shift));
    }
}
