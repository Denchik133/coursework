package org.example;

import org.example.UI.MainFrame;
import org.example.core.ADFGX;
import org.example.core.Cesar;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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

        MainFrame UI = new MainFrame();
        UI.setVisible(true);
    }
}