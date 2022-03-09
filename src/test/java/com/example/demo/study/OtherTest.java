package com.example.demo.study;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * @author dengzhewen
 * @create 2022-03-07 10:03
 * @Version v1.0.0
 */
public class OtherTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int y= 1;
        for (int i = 1; i <= n; i++) {
            int x = y;
            int countJ = i+1;
            for (int j = 1; j <= n-i+1; j++) {
                System.out.print(x+" ");
                x += countJ++;

            }
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
