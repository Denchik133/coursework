package org.example.core;

import org.example.UI.CypherType;
import org.example.core.exceptions.KeyNotValidException;
import org.example.core.exceptions.WrongCharacterException;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class ADFGVX {
    public static final String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890";

    public static String encrypt(String text, String key1, String key2) throws KeyNotValidException, WrongCharacterException {
        KeyValidator.validateWithThrows(CypherType.ADFGVX, new CypherParams(null, null, key1, key2));
        char[][] tabel = createSquare(key1);
        // Перший етап шифрування
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < text.length(); k++){
            char c = text.charAt(k);
            String bigRam = getEncryptedChar(c, tabel);
            sb.append(bigRam);
        }
        String result = sb.toString();
        // Другий етап шифрування
        int cols = key2.length();
        int rows = result.length() / cols + 1;
        if (result.length() % cols != 0){
            rows++;
        }
        char[][] shuffle = new char[rows][cols];
        // Заповнити масив shuffle
        shuffle[0] = key2.toCharArray();
        int k = 1;
        int l = 0;
        for (char c : result.toCharArray()) {
            shuffle[k][l] = c;
            l++;
            if (l == cols) {
                l = 0;
                k++;
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (shuffle[i][j] == '\u0000'){
                    shuffle[i][j] = 'X';
                }
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
        char[][] tabel = new char[6][6];
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
            tabel[i][j] = c;
            j++;
            if (j == 6){
                j = 0;
                i++;
            }
        }
        for (int k = 0; k < 6; k++){
            for (int l = 0; l < 6; l++){
                System.out.print(tabel[k][l]);
            }
            System.out.println();
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

    private static String getEncryptedChar(char c, char[][] square) throws WrongCharacterException {
        char[] ADFGVX = {'A', 'D', 'F', 'G', 'V', 'X'};
        for (int m = 0; m < 6; m++){
            for (int n = 0; n < 6; n++){
                if (square[m][n] == c) {
                    char[] tmp = new char[2];
                    tmp[0] = ADFGVX[m];
                    tmp[1] = ADFGVX[n];
                    return new String(tmp);
                }
            }
        }
        throw new WrongCharacterException("Text contains wrong character:" + c);
    }

//    private static boolean verifyFirstKey(String key1) throws KeyNotValidException {
//        String key = key1.toLowerCase();
//        for (int i = 0; i < key.length(); i++){
//            if (!alphabet.contains(String.valueOf(key.charAt(i)))){
//                throw new KeyNotValidException(key.charAt(i) + " Не входить до алфавіту");
//            }
//        }
//        return false;
//    }

    public static String decrypt(String text, String key1, String key2) throws KeyNotValidException, WrongCharacterException {
        KeyValidator.validateWithThrows(CypherType.ADFGVX, new CypherParams(null, null, key1, key2));
        int cols = key2.length();
        int rows = text.length() / cols + 1;
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
        System.out.println(text);
        for (int k = 1; k < shuffle.length; k++){
            for (int l = 0; l < shuffle[0].length; l++){
                System.err.print(shuffle[k][l]);
            }
            System.out.println();
        }
        char[][] recoveredShuffle = new char[rows][cols];
        recoveredShuffle[0] = key2.toCharArray();

        for (int k = 0; k < shuffle[0].length; k++){
            int index = findIndex(recoveredShuffle, shuffle[0][k]);
            for (int m = 0; m < shuffle.length; m++){
                recoveredShuffle[m][index] = shuffle[m][k];
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int k = 1; k < recoveredShuffle.length; k++){
            for (int m = 0; m < recoveredShuffle[0].length; m++){
                sb.append(recoveredShuffle[k][m]);
            }
        }
        String result = sb.toString();
        StringBuilder finalResult = new StringBuilder();
        char[][] square = createSquare(key1);
        for (int h = 0; h + 1 < result.length(); h += 2){
            finalResult.append(getDecryptedChar(result.charAt(h),
                    result.charAt(h + 1), square));
        }
        return finalResult.toString();
    }

    private static char getDecryptedChar(char c, char c1, char[][] square) throws WrongCharacterException {
        char[] ADFGVX = {'A', 'D', 'F', 'G', 'V', 'X'};
        String array = new String(ADFGVX);
        if (array.indexOf(c) == -1){
            throw new WrongCharacterException("Encrypted text contains invalid character:" + c);
        }
        if (array.indexOf(c1) == -1){
            throw new WrongCharacterException("Encrypted text contains invalid character:" + c1);
        }
        return square[array.indexOf(c)][array.indexOf(c1)];
    }

    private static int findIndex(char[][] recoveredShuffle, char c) throws KeyNotValidException {
        for (int i = 0; i < recoveredShuffle.length; i++){
            if (recoveredShuffle[0][i] == c){
                return i;
            }
        }
        throw new KeyNotValidException("Some error with key");
    }
}
