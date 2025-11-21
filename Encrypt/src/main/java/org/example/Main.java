package org.example;

import org.example.UI.MainFrame;
import org.example.core.ADFGVX;
import org.example.core.ADFGX;
import org.example.core.Cesar;

public class Main {
    public static void main(String[] args) throws Exception {
        String encrypted = Cesar.encrypt("abc", 32);
        System.out.println(encrypted);
        String decrypted = Cesar.decrypt(encrypted, 32);
        System.out.println(decrypted);

        String ADFGXencrypted = ADFGX.encrypt("gfbifgdbj", "usd", "bca");
        System.out.println(ADFGXencrypted);

        String ADFGXdecrypted = ADFGX.decrypt(ADFGXencrypted, "usd", "bca");
        System.out.println(ADFGXdecrypted);

        String ADFGVXencrypted = ADFGVX.encrypt("gfbifgdbj1652", "usd", "bca");
        System.out.println(ADFGVXencrypted);

        String ADFGVXdecrypted = ADFGVX.decrypt(ADFGVXencrypted, "usd", "bca");
        System.out.println(ADFGVXdecrypted);

//        MainFrame UI = new MainFrame();
//        UI.setVisible(true);
    }
}
