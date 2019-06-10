package org.lzz.chat.test;

import java.util.UUID;

public class Trap {

    public static int trap(int[] height) {
        int trapNum = 0;
        int i=0,j=1;
        while (j<height.length){
            if(height[i]==0) {
                i++;
                j++;
            }else {
                if(height[i]<=height[j]){
                    trapNum+=height[i]*(j-i-1);
                    i=j;
                    j++;
                }else{
                    j++;
                }
            }
        }

        return trapNum;
    }

    public static void main(String[] args) {
        int[] ar = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};

        System.out.println(UUID.randomUUID().toString());
    }
}
