package com.cn.letcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 停车场问题
 * @author hebiao
 * @version :Test.java, v0.1 2019/7/17  hebiao
 */
public class Test {

    private static List<Record> allData = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        while (true) {
            while (true) {
                StringBuilder input = new StringBuilder();
                System.out.println("input:");
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(System.in));
                String line;
                /**
                 * 以 end 字符作为输入结束标记
                 */
                while ((line = bufferedReader.readLine()) != null) {
                    if (line != null && !line.equals("end")) {
                        input.append(line).append("\n");
                    } else {
                        break;
                    }
                }
                String outPut = searchRecord(input);
                System.out.println("output:");
                System.out.println(outPut);
            }
        }
    }

    /**
     * 查找记录
     */
    private static String searchRecord(StringBuilder input) {
        StringBuilder outBuilder = new StringBuilder();
        String[] rows = input.toString().split("\n");
        for (String singleRow : rows) {
            String[] elements = singleRow.split(" ");
            Integer type = elements[0].equals("checkin") ? 1 : 0;
            Integer time = Integer.valueOf(elements[1].split("=")[1]);
            String card = elements[2].split("=")[1];
            Record record = new Record(type, card, time);
            allData.add(record);
        }
        List<Record> checkIn =
                allData.stream().filter((record -> record.getType().equals(1)))
                        .sorted(Comparator.comparing(Record::getTime)).collect(Collectors.toList());
        List<Record> checkOut =
                allData.stream().filter((record -> record.getType().equals(0)))
                        .sorted(Comparator.comparing(Record::getTime)).collect(Collectors.toList());

        for (int i = 0; i < checkIn.size(); i++) {

            Record inRecord = checkIn.get(i);

            String s ="record"+(i+1)+":"+inRecord.getCard()+" "+inRecord.getTime()+" in ";
            if(i<checkOut.size() && checkOut.get(i)!=null){
                Record outRecord = checkOut.get(i);
                s =s+outRecord.getTime()+" out";
            }

            outBuilder.append(s).append("\n");
        }

        return outBuilder.toString();
    }

    public static class Record {

        private Integer type;//1 checkin 0 chckout
        private String card; //车牌
        private Integer time;// 时间

        public Record(Integer type, String card, Integer time) {
            this.type = type;
            this.card = card;
            this.time = time;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }
    }
}
