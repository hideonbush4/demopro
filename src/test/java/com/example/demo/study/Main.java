package com.example.demo.study;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dengzhewen
 * @create 2022-03-07 10:03
 * @Version v1.0.0
 */
public class Main {

    // https://www.nowcoder.com/practice/22948c2cad484e0291350abad86136c3
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n1 = sc.nextInt();
        int n2 = sc.nextInt();
        for (int i = Math.min(n1, n2); i <= n1 * n2; i++) {
            if (i % n1 == 0 && i % n2 == 0) {
                System.out.println(i);
                break;
            }
        }

        sc.close();
    }

    // https://www.nowcoder.com/practice/c1f9561de1e240099bdb904765da9ad0
    public static void test15(String[] args) {
        Scanner sc = new Scanner(System.in);

        String n = sc.next();
        Map<Character, Integer> map = new HashMap<>();
        for (char c : n.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        map.entrySet().stream().sorted((entry1, entry2) -> {
            if (entry1.getValue() == entry2.getValue()) {
                return entry1.getKey() - entry2.getKey();
            } else {
                return entry2.getValue() - entry1.getValue();
            }
        }).map(Map.Entry::getKey).collect(Collectors.toList()).forEach(System.out::print);


        sc.close();
    }

    // https://www.nowcoder.com/practice/88ddd31618f04514ae3a689e83f3ab8e
    public static void test14(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nextInt = sc.nextInt();
        int count = 0;
        for (int i = 0; i < nextInt; i++) {
            int x = i*i;
            String length = String.valueOf(i);
            String xstr = String.valueOf(x);
            if (xstr.substring(xstr.length() - length.length()).equals(length)) {
                count++;
            }
        }
        System.out.println(count);
    }

    // https://www.nowcoder.com/practice/3350d379a5d44054b219de7af6708894
    public static void test13(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Map<String, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(sc.next(), 0);
        }
        int n2 = sc.nextInt();
        int error = 0;
        for (int i = 0; i < n2; i++) {
            String key = sc.next();
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
            } else {
                error++;
            }
        }
        for (String s : map.keySet()) {
            System.out.println(s + "：" + map.get(s));
        }
        System.out.println("Invalid：" + error);

        sc.close();
    }

    // https://www.nowcoder.com/practice/e2a22f0305eb4f2f9846e7d644dba09b
    public static void test12(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        System.out.println(digui(m + 1, n + 1));
        sc.close();
    }

    private static int digui(int m, int n) {
        if (m == 1 || n == 1) {
            return 1;
        }
        return digui(m - 1, n) + digui(m, n - 1);
    }

    // https://www.nowcoder.com/practice/52d382c2a7164767bca2064c1c9d5361
    public static void test11(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String next = sc.next();
            int score = 0;

            int length = next.length();
            if (length <= 4) {
                score += 5;
            } else if (length <= 7) {
                score += 10;
            } else if (length > 8) {
                score += 25;
            }
        }
        sc.close();
    }

    private static void printScore(byte b) {
        if (b >= 90) {
            System.out.println("VERY_SECURE");
        } else if (b >= 80) {
            System.out.println("SECURE");
        } else if (b >= 70) {
            System.out.println("VERY_STRONG");
        } else if (b >= 60) {
            System.out.println("STRONG");
        } else if (b >= 50) {
            System.out.println("AVERAGE");
        } else if (b >= 25) {
            System.out.println("WEAK");
        } else if (b >= 0) {
            System.out.println("VERY_WEAK");
        }
    }

    // https://www.nowcoder.com/practice/4b1658fd8ffb4217bc3b7e85a38cfaf2
    public static void test10(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int s = sc.nextInt();
            String str = Integer.toBinaryString(s);
            int max = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '1') {
                    max = Math.max(1, max);
                    for (int j = i + 1; j < str.length(); j++) {
                        if (str.charAt(j) == '1') {
                            max++;
                        } else {
                            break;
                        }

                    }
                }
            }
            System.out.println(max);
        }
    }

    // https://www.nowcoder.com/practice/12e081cd10ee4794a2bd70c7d68f5507
    public static void test9(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        int max = 1;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                if (ishuiwen(s.substring(i, j + 1))) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        System.out.println(max);
    }

    private static boolean ishuiwen(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            stack.push(c);
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.empty()) {
            sb.append(stack.pop());
        }

        return s.equals(sb.toString());
    }

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
