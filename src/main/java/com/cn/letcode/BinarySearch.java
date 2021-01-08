package com.cn.letcode;

/**
 * @author hebiao
 * @title 二分查找，注意前提是数组有序
 * @date 2021/1/8
 * @since 1.0.0
 */
public class BinarySearch {
    
    public static int search(int[] data, int fromIndex, int endIndex, int value) {
        if (endIndex < fromIndex) {
            return -1;
        }
        int mid = (fromIndex + endIndex) >>> 1;
        if (data[mid] < value) {
            return search(data, mid+1, endIndex, value);
        } else if (data[mid] > value) {
            return search(data, fromIndex, mid-1, value);
        }
        return mid;
    }
    
    public static void main(String[] args) {
        int[] data =new int[]{1,2,5,7,10,27,36};
        System.out.println(search(data,0,data.length,10));
    }
}
