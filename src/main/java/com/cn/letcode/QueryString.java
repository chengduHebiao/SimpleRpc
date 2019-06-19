package com.cn.letcode;

import java.util.ArrayList;
import java.util.List;

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
}
