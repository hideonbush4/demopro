package com.example.demo.study;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author dengzhewen
 * @create 2022-03-07 10:03
 * @Version v1.0.0
 */
public class Main {



    // https://www.nowcoder.com/practice/dbace3a5b3c4480e86ee3277f3fe1e85
    public static void test8(String[] args) {
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        int first = i * (i - 1) + 1;
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < i; j++) {
            sb.append("+" + first);
            first = first + 2;
        }
        System.out.println(sb.substring(1));
    }

    // https://www.nowcoder.com/practice/769d45d455fe40b385ba32f97e7bcded
    public static void test7(String[] args) {
        Scanner sc = new Scanner(System.in);
        int year = sc.nextInt();
        int month = sc.nextInt();
        int day = sc.nextInt();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        System.out.println(calendar.get(Calendar.DAY_OF_YEAR));
    }

    // https://www.nowcoder.com/practice/1b46eb4cf3fa49b9965ac3c2c1caf5ad
    public static void test6(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            Integer n = sc.nextInt();
            String s = Integer.toBinaryString(n);
            String s1 = s.replaceAll("0", "");
            System.out.println(s1.length());
        }
    }

    public static void test5(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int count = 0;
        if (n < 6) {
            System.out.println(0);
            return;
        }
        for (int i = 6; i < n; i++) {
            int sum = 0;
            for (int j = 1; j <= i/2; j++) {
                if (i % j == 0) {
                    sum += j;
                }
            }
            if (sum == i) {
                count++;
            }
        }
        System.out.println(count);
    }

    public static void test4(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            int one = 1;
            int two = 0;
            int three = 0;
            int attr = 0;
            for (int i = 2; i <= n; i++) {
                three += two;
                two = one;
                one = attr;
                attr = three + two;
            }
            System.out.println(one + two + three);
        }
    }

    // https://www.nowcoder.com/practice/2de4127fda5e46858aa85d254af43941
    public static void test3(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            Character[] characters = s.chars().mapToObj(c -> (char) c).sorted(Character::compareTo).toArray(Character[]::new);
            Arrays.stream(characters).forEach(System.out::print);
            System.out.println();
        }
    }

    // https://www.nowcoder.com/practice/fe298c55694f4ed39e256170ff2c205f
    public static void test2(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            if (n == 0) {
                break;
            }
            System.out.println(getsum(0, n));
        }
    }

    private static int getsum(int sum, int n) {
        if (n == 2){
            return sum + 1;
        } else if (n <= 1) {
            return sum;
        } else {
            sum += n/3 + getsum(sum, n%3+n/3);
            return sum;
        }

    }

    // https://www.nowcoder.com/practice/2baa6aba39214d6ea91a2e03dff3fbeb
    public static void test1(String[] args) {
        Scanner sc = new Scanner(System.in);
//        Stack<Map<String, Integer>> stack = new Stack<>();
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            String[] strLine = s.split(" ");
            String[] left = strLine[0].split("\\\\");
            String str = getSplitStr(left[left.length - 1]);
            String key = str + " " + strLine[1];
            map.put(key, map.getOrDefault(key, 0) + 1);
        }
        // 反转LinkedHashMap
//        ListIterator<Map.Entry<String, Integer>> i = new ArrayList<>(map.entrySet()).listIterator(map.size());
//        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
//        while (i.hasPrevious()) {
//            Map.Entry<String, Integer> entry = i.previous();
//            linkedHashMap.put(entry.getKey(), entry.getValue());
//        }

        int j = 0;
        for (String s : map.keySet()) {
            if (map.size() - j <= 8) {
                System.out.println(s + " " + map.get(s));
            }
            j++;
        }
    }

    private static String getSplitStr(String s) {
        if (s.length() <= 16) {
            return s;
        } else {
            return s.substring(s.length() - 16);
        }
    }

}
