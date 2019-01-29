/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.letcode;

/**
 * @author hebiao
 * @version $Id:ReverseString.java, v0.1 2018/11/16 10:15 hebiao Exp $$
 */
public class ReverseString {

    public static String reverseWords(String s) {

        StringBuffer sb = new StringBuffer();

        String[] array = s.split(" ");

        for (int index = 0; index < array.length; index++) {

            String single = array[index];

            String buffer = new StringBuffer(single).reverse().toString();
            sb.append(buffer);
            if (index < array.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static String getBackStringLength(String originString){

        Integer maxLength = 0;

        String maxLengthStr ="";
        //首先获取所有的子串


        for(int i=0;i<originString.length()-1;i++){
            for(int j=i+1;j<=originString.length();j++){
                String subString = originString.substring(i,j);
                Integer length = getLength(subString);

                if(length>maxLength){
                    maxLength = length;
                    maxLengthStr = subString;
                }
            }
        }

        return  maxLengthStr;

    }

    private static Integer getLength(String subString) {

        String backString =new StringBuffer(subString).reverse().toString();
        if(backString.equals(subString)){
            return subString.length();
        }

        return 0;
    }

    public static void main(String[] args) {
        String originString = "bcbassdolk";

        System.out.println("最长回文子串为："+ new StringBuffer(originString).reverse().toString());
    }
}
