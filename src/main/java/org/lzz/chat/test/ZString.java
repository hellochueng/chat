package org.lzz.chat.test;

import java.util.*;

public class ZString {

    public static String convert(String s, int numRows) {

        List<Map<Integer,Character>> result = new ArrayList<>();

        for(int i = 0;i < numRows;i++) {

            result.add(new HashMap<>());
        }

        int row = 1;

        for(int i=0;i<s.length();i++) {

            for (int j = 0; j < numRows; j++) {
                result.get(j).put(result.get(j).size(), s.charAt(i));
                i++;
            }

            row++;
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (Map<Integer,Character> map:result) {
            for(Character character : map.values())
                stringBuilder.append(character);
        }

        return stringBuilder.toString();

    }

    public static void main(String[] args) {
        System.out.println(convert1("LEETCODEISHIRING",3));
    }

    public static String convert1(String s, int numRows) {

        if (numRows == 1) return s;

        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(numRows, s.length()); i++)
            rows.add(new StringBuilder());

        int curRow = 0;
        boolean goingDown = false;

        for (char c : s.toCharArray()) {
            rows.get(curRow).append(c);
            if (curRow == 0 || curRow == numRows - 1) goingDown = !goingDown;
            curRow += goingDown ? 1 : -1;
        }

        StringBuilder ret = new StringBuilder();
        for (StringBuilder row : rows) ret.append(row);
        return ret.toString();

    }
//    LEETCODEISHIRING
//    LCIRETOESIIGEDHN
//    LCIREOSIEDHN
}
