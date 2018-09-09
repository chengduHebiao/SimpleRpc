
package com.cn.hebiao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author hebiao
 * @version $Id:SortMaps.java, v0.1 2018/8/31 22:26 hebiao Exp $$
 */
public class SortMaps {

  /**
   * List<String> 中找出出现次数TOP N的记录和其对应的次数
   */

  public static void parseList(List<String> data, Integer top) {

    Map<String, Integer> dataMap = new HashMap<>();
    data.forEach(str -> {
      Integer count = dataMap.get(str);
      dataMap.put(str, count == null ? 1 : count + 1);
    });

    List<Entry<String, Integer>> list = new ArrayList<>(dataMap.entrySet());

    //升序排序
    Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

    System.out.println("------------map按照value降序排序--------------------");
    int count = 0;
    for (Map.Entry<String, Integer> entry : list) {
      if (count++ < top) {
        System.out.println(entry.getKey() + ":" + entry.getValue());
      }
    }
  }

  public static void main(String[] args) {

    String[] strings = {"he", "biao", "1", "2", "2", "2", "78", "78", "0", "q", "sb", "sb", "sb", "sb", "sb"};

    parseList(Arrays.asList(strings), 3);
  }

}
