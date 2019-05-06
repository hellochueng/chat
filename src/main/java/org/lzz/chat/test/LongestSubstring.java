package org.lzz.chat.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestSubstring {

    public static int lengthOfLongestSubstring(String s) {

        int i=0,x=0,y=x;

        Set set = new HashSet();

        if(s.length()==1){
            return 1;
        }
        while(x<s.length() && y<s.length()) {

            if(set.contains(s.charAt(y))){
                x++;
                y=x;
                if(set.size()>i){
                    i=set.size();
                }
                set.removeAll(set);
                continue;
            }else{
                set.add(s.charAt(y));
                y++;
                if(set.size()>i){
                    i=set.size();
                }
            }
        }
        return i;
    }
    public static int lengthOfLongestSubstring2(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();

        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }
    public static void main(String[] args) {
        System.out.println(Math.max(2,1));
        System.out.println(lengthOfLongestSubstring2("abcabcbb"));
    }



}

