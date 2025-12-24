package org.example.core;

import org.example.UI.CypherType;
import org.example.core.exceptions.KeyNotValidException;
import org.example.core.exceptions.WrongCharacterException;
import org.example.core.exceptions.WrongCypherTypeException;

public class CypherService {
    public static String encrypt(String text, CypherParams params, CypherType type) throws WrongCharacterException, KeyNotValidException {
        switch (type){
            case CypherType.CESAR:{
                String shift = params.getCesarShift();
                return Cesar.encrypt(text, shift);
            }
            case CypherType.ADFGVX:{
                String key1 = params.getKey1();
                String key2 = params.getKey2();
                return ADFGVX.encrypt(text, key1, key2);
            }
            case CypherType.ADFGX:{
                String key1 = params.getKey1();
                String key2 = params.getKey2();
                return ADFGX.encrypt(text, key1, key2);
            }
            case CypherType.VIGENERE:{
                String key = params.getVigenereKey();
                return Vigenere.encrypt(text, key);
            }
            default:{
                throw new WrongCypherTypeException("Wrong Cypher Type");
            }
        }
    }

    public static String decrypt(String text, CypherParams params, CypherType type) throws WrongCharacterException, KeyNotValidException {
        switch (type){
            case CypherType.CESAR:{
                String shift = params.getCesarShift();
                return Cesar.decrypt(text, shift);
            }
            case CypherType.ADFGVX:{
                String key1 = params.getKey1();
                String key2 = params.getKey2();
                return ADFGVX.decrypt(text, key1, key2);
            }
            case CypherType.ADFGX:{
                String key1 = params.getKey1();
                String key2 = params.getKey2();
                return ADFGX.decrypt(text, key1, key2);
            }
            case CypherType.VIGENERE:{
                String key = params.getVigenereKey();
                return Vigenere.decrypt(text, key);
            }
            default:{
                throw new WrongCypherTypeException("Wrong Cypher Type");
            }
        }
    }
}
