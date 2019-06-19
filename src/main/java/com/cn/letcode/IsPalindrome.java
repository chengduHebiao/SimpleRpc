/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.letcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author hebiao
 * @version $Id:IsPalindrome.java, v0.1 2019/2/28 9:00 hebiao Exp $$
 */
public class IsPalindrome {

    /**
     * 判断字符串是否是回文串
     */
    public static boolean isPalindrome(String s) {
        if (s == null) {
            return true;
        }

        //只考虑数字和字母
        String regex = "[^a-zA-Z0-9]";
        String filterString = s.replaceAll(regex, "").toLowerCase();

        int length = filterString.length();

        int i = 0;
        int j = length;

        while (i >= 0 && i < j) {
            if (filterString.charAt(i) != filterString.charAt(j - 1)) {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }

    public static void main(String[] args) {
       /* System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println(partition("aab"));
        Set<String> list = new HashSet<>();
        list.add("cats");
        list.add("dog");
        list.add("sand");
        list.add("and");
        list.add("cat");
        System.out.println(wordBreak("catsandog", list));*/

       //System.out.println(lengthOfLongestSubString("abc"));
       System.out.println(convert("LEETCODEISHIRING",3));
    }

    public static int countSubstrings(String s) {
        int len = s.length();
        if (len <= 1) {
            return len;
        }

        int ans = 0;
        for (int i = 0; i < len; i++) {
            ans += getPalin(s, i, i);
            ans += getPalin(s, i, i + 1);
        }

        return ans;
    }

    public static int getPalin(String s, int l, int r) {
        int cnt = 0;
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
            cnt++;
        }

        return cnt;
    }


    public static List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        if (s == null) {
            return result;
        }
        List<String> temp = new ArrayList<>();
        if (s.length() == 0) {
            result.add(temp);
            return result;
        }
        search(s, result, temp, 0);
        return result;

    }

    public static void search(String s, List<List<String>> result, List<String> temp, int start) {
        if (start == s.length()) {
            List<String> p = new ArrayList<>(temp);
            result.add(p);
            return;
        }
        for (int i = start; i < s.length(); i++) {
            if (isPalindrome(s.substring(start, i + 1))) {
                temp.add(s.substring(start, i + 1));
                search(s, result, temp, i + 1);
                temp.remove(temp.size() - 1);
            }
        }
    }




    public static boolean wordBreak(String s, Set<String> dict) {
        if (s.length() == 0) {
            return false;
        }
        boolean[] canBreak = new boolean[s.length() + 1]; // canBreak[i]: whether s[0,i) can be break
        canBreak[0] = true;     // base case

        for (int i = 1; i <= s.length(); i++) {  // last case: s[0,s.length()) == s
            boolean flag = false;
            for (int j = 0; j < i; j++) {    // j should be smaller than i to include at least character
                // s[0,i) can be break only when s[0,j) can be break AND s[j,i) is in the dict
                if (canBreak[j] && dict.contains(s.substring(j, i))) {
                    flag = true;
                    break;
                }
            }
            canBreak[i] = flag;
        }

        return canBreak[s.length()];
    }

    private static boolean repeatChar(String chars){
        StringBuilder stringBuilder = new StringBuilder();
        for(Character c:chars.toCharArray()){
            String str = stringBuilder.toString();
            if(str.contains(c.toString())){
                return true;
            }
             stringBuilder.append(c);
        }
        return false;
    }

    private static int lengthOfLongestSubString(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < n; end++) {
            char alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha), start);
            }
            ans = Math.max(ans, end - start + 1);
            map.put(s.charAt(end), end + 1);
        }
        return ans;
    }

    /**
     * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
     *
     * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
     *
     * L   C   I   R
     * E T O E S I I G
     * E   D   H   N
     * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"
     * 整体的思路是遍历字符串，遍历过程中将每行都看成新的字符串构成字符串数组，最后再将该数组拼接起来即可
     * 如果numRows=1则说明当前字符串即为结果，直接返回
     * 否则整个字符串需要经历，向下向右，向下向右，这样的反复循环过程，设定down变量表示是否向下，loc变量表示当前字符串数组的下标
     * 如果down为true，则loc+=1，字符串数组下标向后移动，将当前字符加入当前字符串中
     * 如果down为false，则表示向右，则loc-=1，字符串数组下标向前移动，将当前字符加入当前字符串中
     * 时间复杂度：O(n)，n为字符串s的长度
     */
    public static String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }

        int len = Math.min(s.length(), numRows);
        String[] rows = new String[len];
        for (int i = 0; i < len; i++) {
            rows[i] = "";
        }
        int loc = 0;
        boolean down = false;

        for (int i = 0; i < s.length(); i++) {
            rows[loc] += s.substring(i, i + 1);
            if (loc == 0 || loc == numRows - 1) {
                down = !down;
            }
            loc += down ? 1 : -1;
        }

        String ans = "";
        for (String row : rows) {
            ans += row;
        }
        return ans;
    }
}
