package org.example.core;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class ADFGX {
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public static String encrypt(String text, String key1, String key2) throws KeyNotValidException {
        char[][] tabel = createSquare(key1);
        // Перший етап шифрування
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < text.length(); k++){
            char c = text.charAt(k);
            sb.append(getEncryptedChar(c, tabel));
        }
        String result = sb.toString();
        // Другий етап шифрування
        int cols = key2.length();
        int rows = result.length() / cols;
        if (result.length() % cols != 0){
            rows++;
        }
        char[][] shuffle = new char[rows][cols];
        // Заповнити масив shuffle
        int k = 0;
        int l = 0;
        for (char c : result.toCharArray()){
            shuffle[k][l] = c;
            l++;
            if (l == cols){
                l = 0;
                k++;
            }
        }
        for (int m = 0; m < shuffle[0].length - 1; m++){
            for (int n = 0; n < shuffle[0].length - 1 - m; n++){
                if (shuffle[0][n] > shuffle[0][n + 1]){
                    swapColoumns(shuffle, n, n + 1);
                }
            }
        }
        StringBuilder finalResult = new StringBuilder();
        int i = 1;
        int j = 0;
        for (; i < rows; i++){
            for (j = 0; j < cols; j++){
                finalResult.append(shuffle[i][j]);
            }
        }
        return finalResult.toString();
    }

    private static char[][] createSquare(String key1) throws KeyNotValidException {
        char[][] tabel = new char[5][5];
        verifyFirstKey(key1);
        Set<Character> set = new LinkedHashSet<Character>();
        for (int i = 0; i < key1.length(); i++) {
            set.add(key1.charAt(i));
        }
        for (int i = 0; i < alphabet.length(); i++){
            set.add(alphabet.charAt(i));
        }
        int i = 0;
        int j = 0;
        for (char c : set){
            if (c == 'j'){
                continue;
            }
            tabel[i][j] = c;
            j++;
            if (j == 5){
                j = 0;
                i++;
            }
        }
        return tabel;
    }

    private static void swapColoumns(char[][] shuffle, int i, int j){
        for(int k = 0; k < shuffle.length; k++){
            char tmp = shuffle[k][i];
            shuffle[k][i] = shuffle[k][j];
            shuffle[k][j] = tmp;
        }
    }

    private static String getEncryptedChar(char c, char[][] square){
        char[] ADFGX = {'A', 'D', 'F', 'G', 'X'};
        for (int m = 0; m < 5; m++){
            for (int n = 0; n < 5; n++){
                if (square[m][n] == c) {
                    char[] tmp = new char[2];
                    tmp[0] = ADFGX[m];
                    tmp[1] = ADFGX[n];
                    return new String(tmp);
                }
            }
        }
        return null;
    }

    private static boolean verifyFirstKey(String key1) throws KeyNotValidException {
        String key =  key1.toLowerCase();
        for (int i = 0; i < key.length(); i++){
            if (!alphabet.contains(String.valueOf(key.charAt(i)))){
                throw new KeyNotValidException(key.charAt(i) + " Не входить до алфавіту");
            }
        }
        return false;
    }

    public static String decrypt(String text, String key1, String key2) throws Exception {
        verifyFirstKey(key1);
        int cols = key2.length();
        int rows = text.length() / cols;
        if (text.length() % cols != 0){
            rows++;
        }
        char[][] shuffle = new char[rows][cols];
        char[] keyArray = key2.toCharArray();
        Arrays.sort(keyArray);
        shuffle[0] = keyArray;
        int i = 1;
        int j = 0;
        for (char c : text.toCharArray()){
            shuffle[i][j] = c;
            j++;
            if (j == cols){
                j = 0;
                i++;
            }
        }
        char[][] recoveredShuffle = new char[rows][cols];
        recoveredShuffle[0] = key2.toCharArray();

        for (int k = 0; k < shuffle[0].length; k++){
            int index = findIndex(recoveredShuffle, shuffle[0][k]);
            for (int m = 0; m < shuffle.length; m++){
                recoveredShuffle[m][index] = shuffle[m][k];
            }
        }
        char[][] tabel = createSquare(key1);
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < recoveredShuffle.length; k++){
            for (int m = 0; m < recoveredShuffle[0].length; m++){
                sb.append(recoveredShuffle[k][m]);
            }
        }
        String result = sb.toString();
    }

    private static int findIndex(char[][] recoveredShuffle, char c) throws Exception {
        for (int i = 0; i < recoveredShuffle.length; i++){
            if (recoveredShuffle[0][i] == c){
                return i;
            }
        }
        throw new Exception();
    }
}
