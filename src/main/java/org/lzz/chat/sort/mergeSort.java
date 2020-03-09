package org.lzz.chat.sort;

public class mergeSort {

    public static void mergeSort(int[] arr,int start,int end){
        if(start<end){
            int mid = (start+end)/2;
            mergeSort(arr,start,mid);
            mergeSort(arr,mid+1,end);
            merge(arr,start,end,mid);
        }
    }
    public static void merge(int[] arry,int start,int end,int mid){
        int[] temp = new int[end-start+1];
        int i=start,k=mid+1,p=0;
        while (i<=mid&&k<=end) {
            if (arry[i] <= arry[k])
                temp[p++] = arry[i++];
            else
                temp[p++] = arry[k++];
        }
        while (i<=mid)  temp[p++] = arry[i++];
        while (k<=end)  temp[p++] = arry[k++];
        i=0;
        for (int x=start;x<=end;x++){
            arry[x]=temp[i++];
        }
    }

    public static void main(String[] args) {
        int[] a = new int[]{0,0,0,0,0,3,6,9,4,5,7,8};
        mergeSort(a,0,11);
        for (int x :a){
            System.out.print(x+",");
        }
    }
}
