package org.lzz.chat.test;

import java.util.Arrays;

public class FindMedianSortedArrays {

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] res = new int[nums1.length+nums2.length];
        int x=0;
        int y=0;
        for(int i=0;i<res.length;i++) {
            if (x==nums1.length){
                res[i]=nums2[y++];
                continue;
            }
            if(y==nums2.length){
                res[i]=nums1[x++];
                continue;
            }
            if(nums1[x]<=nums2[y]){
                res[i]=nums1[x++];
            } else {
                res[i]=nums2[y++];
            }
        }
        if(res.length%2==0){
            double a = res[res.length/2-1];
            double b = res[res.length/2];
            return (a+b)/2;
        } else {
            return res[res.length/2];
        }
    }

    public static void main(String[] args) {
        int[] nums1 = new int[]{1,2};
        int[] nums2 = new int[]{3,4};
        System.out.println(findMedianSortedArrays(nums1,nums2));
    }
}
