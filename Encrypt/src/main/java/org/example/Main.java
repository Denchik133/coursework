package org.example;

import org.example.UI.MainFrame;
import org.example.logger.EventLogger;

public class Main {
    public static void main(String[] args) throws Exception {
//        String encrypted = Cesar.encrypt("abc", "3");
//        System.out.println(encrypted);
//        String decrypted = Cesar.decrypt(encrypted, "3");
//        System.out.println(decrypted);

//        String ADFGXencrypted = ADFGX.encrypt("hello", "usd", "bca");
//        System.out.println(ADFGXencrypted);
//
//        String ADFGXdecrypted = ADFGX.decrypt(ADFGXencrypted, "usd", "bca");
//        System.out.println(ADFGXdecrypted);
//
//        String ADFGVXencrypted = ADFGVX.encrypt("gfbifgdbj1652", "usd", "bca");
//        System.out.println(ADFGVXencrypted);
//
//        String ADFGVXdecrypted = ADFGVX.decrypt(ADFGVXencrypted, "usd", "bca");
//        System.out.println(ADFGVXdecrypted);
//
//        String VigenereEncrypt = Vigenere.encrypt("vigenere", "usd");
//        System.out.println(VigenereEncrypt);
//
//        String VigenereDecrypted = Vigenere.decrypt(VigenereEncrypt, "usd");
//        System.out.println(VigenereDecrypted);

        MainFrame UI = new MainFrame();
        UI.setVisible(true);
    }
}