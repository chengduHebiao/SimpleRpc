/**
 * BBD Service Inc All Rights Reserved @2018
 */

package com.cn.letcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println(partition("aab"));
        Set<String> list = new HashSet<>();
        list.add("cats");
        list.add("dog");
        list.add("sand");
        list.add("and");
        list.add("cat");
        System.out.println(wordBreak("catsandog", list));
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


}
