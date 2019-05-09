package org.lzz.chat.test;

import java.util.List;

public class LetterCombinations {

    public static List<String> letterCombinations(String digits) {
        Integer.parseInt(digits);
        return null;
    }

    public static Character[]  deliveStringToChar(String str){
        Character[] a = new Character[str.length()];

        for(int i=0;i<str.length();i++) {
            a[i] = (char)str.charAt(i);
        }
        return a;
    }

    public static int[]  deliveStringToInt(String str){
        int[] a = new int[str.length()];

        for(int i=0;i<str.length();i++) {
            a[i] = (int)str.charAt(i);
        }
        return a;
    }

    public static void main(String[] args) {

    }
}
