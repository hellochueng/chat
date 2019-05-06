package org.lzz.chat.test;

public class turnNum {

    public static int reverse(int x) {

        StringBuilder stringBuilder;
        String xx = x+"";
        if(x<0) {
            stringBuilder = new StringBuilder("-");
            for (int i = xx.length()-1; i > 0; i--)
                stringBuilder.append(xx.charAt(i));
        }else {
            stringBuilder = new StringBuilder();
            for (int i = xx.length()-1; i >= 0; i--)
                stringBuilder.append(xx.charAt(i));
        }

        try {
            return Integer.parseInt(stringBuilder.toString());
        } catch (NumberFormatException e) {
            return 0;
        }

    }

    public static void main(String[] args) {
        System.out.println(reverse1(-1232133));
    }

    public static int reverse1(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;
    }
}
