package com.cn.letcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hebiao
 * @version :QueryString.java, v0.1 2019/6/14 13:59 hebiao Exp $$
 */
public class QueryString {

    /**
     * 给定仅有小写字母组成的字符串数组 A，返回列表中的每个字符串中都显示的全部字符（包括重复字符）组成的列表。例如，如果一个字符在每个字符串中出现 3 次，但不是 4 次，则需要在最终答案中包含该字符 3 次。
     *
     * 你可以按任意顺序返回答案。
     *
     * 来源：力扣（LeetCode） 链接：https://leetcode-cn.com/problems/find-common-characters 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public List<String> coommonChars(String[] A) {

        List<String> list = new ArrayList<>();
        int[] res = new int[26];

        char[] chars = A[0].toCharArray();

        for (char c : chars) {
            res[c - 'a']++;
        }

        for (int i = 0; i < A.length; i++) {
            int[] temp = new int[26];
            for (char c : A[i].toCharArray()) {
                temp[c - 'a']++;

            }
            for (int j = 0; j < 26; j++) {
                res[j] = Math.min(res[j], temp[j]);
            }
        }

        for (int i = 0; i < res.length; i++) {

            if (res[i] > 0) {
                for (int j = 0; j < res[i]; j++) {
                    list.add(((char) ('a' + i) + ""));
                }
            }
        }

        return list;
    }

    public static int roamanTOint(String s) {
        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("IX", 9);
        map.put("X", 10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
        map.put("CM", 900);
        map.put("M", 1000);

        Integer result = 0;
        for (int i = 0; i < s.length(); ) {
            if (i + 1 < s.length() && map.containsKey(s.substring(i, i + 2))) {
                result += map.get(s.substring(i, i + 2));
                i = i + 2;
            } else {
                result += map.get(s.substring(i, i + 1));
                i++;
            }
        }
        return result;

    }

    /**
     * 查找最长公共前缀
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0 || strs[0] == null || strs[0].isEmpty()) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }
        int minLength = strs[0].length();
        for (int temp = 0; temp < strs.length; temp++) {
            minLength = Math.min(minLength, strs[temp].length());
        }
        String result = "";
        for (int j = 0; j < minLength; j++) {
            String subString = strs[0].substring(0, j + 1);
            boolean ok = true;
            for (int k = 1; k < strs.length; k++) {
                if (!strs[k].substring(0, j + 1).equals(subString)) {
                    ok = false;
                }
            }
            if (ok) {
                result = subString;
            }

        }
        return result;

    }

    /**
     * 电话号码的字母组合
     *
     */
    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if(null == digits || ""==digits){
            return result;
        }
        Map<String, String> nums = new HashMap<String, String>() {
            {
                put("2", "abc");
                put("3", "def");
                put("4", "ghi");
                put("5", "jkl");
                put("6", "mno");
                put("7", "pqrs");
                put("8", "tuv");
                put("9", "wxyz");
            }
        };
        //回溯法
        backtrack(nums, "", digits, result);
        return result;
    }

    public static void backtrack(Map<String, String> phone, String combination, String next_digits,
            List<String> output) {
        // if there is no more digits to check
        if (next_digits.length() == 0) {
            // the combination is done
            output.add(combination);
        }
        // if there are still digits to check
        else {
            // iterate over all letters which map
            // the next available digit
            String digit = next_digits.substring(0, 1);
            String letters = phone.get(digit);
            for (int i = 0; i < letters.length(); i++) {
                String letter = phone.get(digit).substring(i, i + 1);
                // append the current letter to the combination
                // and proceed to the next digits
                backtrack(phone, combination + letter, next_digits.substring(1), output);
            }
        }
    }


    public static void main(String[] args) {
        // roamanTOint("III");
        String[] strings = new String[]{"c", "acc", "ccc"};
        System.out.println(letterCombinations("237"));
    }
}
