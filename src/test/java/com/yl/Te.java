package com.yl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Te {

    @Test
    public void f() {
        int money1 = 123;
        int money2 = 1688;
        System.out.println(MoneyConverter.moneyCast(money1));
        System.out.println(MoneyConverter.moneyCast(money2));
    }

    public class MoneyConverter {
        private static final String[] NUMBERS = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        private static final String[] UNITS = {"", "拾", "佰", "仟", "万"};

        public static String moneyCast(int money) {
            String moneyStr = String.valueOf(money);
            int len = moneyStr.length();
            StringBuilder result = new StringBuilder();
            int zeroCount = 0;
            for (int i = 0; i < len; i++) {
                int num = moneyStr.charAt(i) - '0';
                int unitIndex = len - i - 1;
                String unit = UNITS[unitIndex % 4];

                if (num == 0) {
                    zeroCount++;
                } else {
                    if (zeroCount > 0) {
                        result.append(NUMBERS[0]);
                        zeroCount = 0;
                    }
                    result.append(NUMBERS[num]).append(unit);
                }
                if (unitIndex % 4 == 0 && zeroCount < 4) {
                    zeroCount = 0;
                }
            }
            if (result.length() == 0) {
                return NUMBERS[0] + UNITS[0];
            }
            return result.toString();
        }
    }

    String f1(String input) {
        String regex = "\\d+(\\.\\d{1,2})?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String numberStr = matcher.group();
            double number = Double.parseDouble(numberStr);
            String formattedNumber = String.format("%.2f", number);
            if (result.length() > 0) {
                int n = result.indexOf(".");
                result.substring(0, n + 3);
                return result.toString();
            }
            result.append(formattedNumber);
        }

        return result.toString();
    }

    String f2(int tar) {
        String[] numbers = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String[] units = {"", "拾", "佰", "仟", "万"};
        List<Integer> list = new ArrayList<>();
        List<String> strList = new ArrayList<>();
        if (tar == 0) {
            return numbers[0];
        }
        int temp = tar;
        StringBuilder builder = new StringBuilder();
        while (temp > 0) {
            list.add(temp % 10);
            temp /= 10;
        }
        int x = -1;
        for(int i=0;i<list.size();i++){
            int j = list.get(i);
            String str = numbers[j];
            if(j == 0){
                if(i == 0) {
                    x = j;
                    continue;
                }
                if(x == 0) continue;
                strList.add(str);
                x = j;
                continue;
            }
            x = j;
            strList.add(str+units[i]);
        }
        for(int i=strList.size()-1;i>=0;i--){
            builder.append(strList.get(i));
        }
        builder.append("元");
        return builder.toString();
    }
    @Test
    public void test() {
        System.out.println(f2(10010));
    }
    @Test
    public void test2(){
        Book qaz = new Book("qaz", 1);
        Book qaz2 = new Book("qaz", 1);
        System.out.println(qaz.hashCode());
        System.out.println(qaz2.hashCode());
    }
    class Book{
        public String name;
        public int price;

        public Book(String name, int price) {
            this.name = name;
            this.price = price;
        }
    }
}
