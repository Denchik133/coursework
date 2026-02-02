package org.example.core;

import org.example.UI.CipherType;
import org.example.core.exceptions.KeyNotValidException;
import org.example.core.exceptions.WrongCharacterException;
import org.example.core.exceptions.WrongCipherTypeException;

public class CipherService {
    public static String encrypt(String text, CipherParams params, CipherType type) throws WrongCharacterException, KeyNotValidException {
        switch (type){
            case CipherType.CESAR:{
                String shift = params.getCesarShift();
                return Cesar.encrypt(text, shift);
            }
            case CipherType.ADFGVX:{
                String key1 = params.getKey1();
                String key2 = params.getKey2();
                return ADFGVX.encrypt(text, key1, key2);
            }
            case CipherType.ADFGX:{
                String key1 = params.getKey1();
                String key2 = params.getKey2();
                return ADFGX.encrypt(text, key1, key2);
            }
            case CipherType.VIGENERE:{
                String key = params.getVigenereKey();
                return Vigenere.encrypt(text, key);
            }
            default:{
                throw new WrongCipherTypeException("Wrong Cipher Type");
            }
        }
    }

    public static String decrypt(String text, CipherParams params, CipherType type) throws WrongCharacterException, KeyNotValidException {
        switch (type){
            case CipherType.CESAR:{
                String shift = params.getCesarShift();
                return Cesar.decrypt(text, shift);
            }
            case CipherType.ADFGVX:{
                String key1 = params.getKey1();
                String key2 = params.getKey2();
                return ADFGVX.decrypt(text, key1, key2);
            }
            case CipherType.ADFGX:{
                String key1 = params.getKey1();
                String key2 = params.getKey2();
                return ADFGX.decrypt(text, key1, key2);
            }
            case CipherType.VIGENERE:{
                String key = params.getVigenereKey();
                return Vigenere.decrypt(text, key);
            }
            default:{
                throw new WrongCipherTypeException("Wrong cipher Type");
            }
        }
    }
}
