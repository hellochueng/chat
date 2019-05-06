package org.lzz.chat.test;

public class StringMatch {

    public static boolean isMatch(String s, String p) {
        boolean result = true;
        for (int i = 0;i<p.length()-1;i++) {
            if('.'==p.charAt(i)){
                continue;
            }
            if('*'==p.charAt(i)) {

            }
        }

        return result;
    }

    public static void main(String[] args) {
    }
}
