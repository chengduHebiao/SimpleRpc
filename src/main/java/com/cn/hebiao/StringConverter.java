package com.cn.hebiao;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hebiao
 * @version $Id:StringConverter.java, v0.1 2018/9/3 11:30 hebiao Exp $$
 */
public class StringConverter implements Converter<String, Integer> {

    public static void main(String[] args) {
        Converter<String, Integer> converter = (from) -> String.valueOf(from);
        String result = converter.convert(3);
        System.out.println(result);
        Converter<List, Object> listConverter = o -> Arrays.asList(o);
        System.out.println(listConverter.convert(new Object[]{"52", "heb"}));
        List<String> data = new ArrayList<>();

        data.add("he");
        data.add("biao");
        Map map = data.stream().collect(Collectors.toMap(Function.identity(), Function.identity()));
        System.out.println(map);

        List<Person> persons = Lists.newArrayList();

        Person p1 = new Person(7L, "tony");
        Person p2 = new Person(2L, "Bob");
        Person p3 = new Person(15L, "yao");
        persons.add(p1);
        persons.add(p2);
        persons.add(p3);

        Map<Long, Person> personMap;

        personMap = persons.stream().sorted(Person::compareTo)
                .collect(Collectors.toMap(Person::getId, Function.identity()));

        System.out.println(personMap);

    }

    @Override
    public String convert(Integer integer) {
        return null;
    }
}
