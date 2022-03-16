package com.example.demo.study;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dengzhewen
 * @create 2022-03-07 10:03
 * @Version v1.0.0
 */
public class Main {

    public static void main(String[] args) {

    }

    public static void jikao(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] split = s.split(",");
        int[][] x = new int[2][10000];
        for (int i = 0; i < split.length; i++) {
            x[0][i] = Integer.valueOf(split[i]);
        }

        int count = 0;
        for (int i = 0; i < split.length; i++) {
            if (isBigest(x[0][i], Arrays.asList(x[0][i]))) {
                x[1][i] = x[1][i] + count;
                count++;
            } else {
                jiaohuan(i, x);
            }
        }



        sc.close();
    }

    public static void jiaohuan(int index, int[][] arr) {
        int temp1 = arr[0][index];
        int temp2 = arr[1][index];
        for (int i = index; i < arr[0].length-1; i++) {
            arr[0][i] = arr[0][i + 1];
        }
        arr[0][arr.length-1] = temp1;
        arr[1][arr.length-1] =temp2;
    }

    public static void jikao3(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] split = s.split(",");
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < split.length; i++) {
            list.add(Integer.valueOf(split[i]));
        }

        LinkedList<Integer> temp = new LinkedList<>();
        temp.addAll(list);

        int x = 0;
        HashMap<String, Integer> map = new HashMap<>();
        int len = list.size();
        for (int i = 0; i < len; i++) {
            if(list.size() > 0){
                Integer tt = list.get(i);
                if (isBigest(tt, list)) {
                    list.remove(tt);
                    i--;
                    map.put(tt+"", x);
                    x++;
                } else {
                    list.remove(tt);
                    list.add(tt);
                    i--;
                }
            }
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < temp.size(); i++) {
            String key = temp.get(i)+"";
            sb.append("," + map.get(key));
        }
        System.out.println(sb.substring(1));

        sc.close();
    }

    public static boolean isBigest(Integer i, List<Integer> list) {
        LinkedList<Integer> temp = new LinkedList<>();
        temp.addAll(list);
        temp.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        return i == temp.get(0);
    }

    public static void jikao2(String[] args) {

        double[][] xishu = new double[300][500];
        int[] x = new int[500];
        double[] b = new double[300];
        String[] dayu = new String[300];

        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] split = line.split(";");
        String[] first = split[0].split(",");
        int lie = first.length;
        int hang = line.substring(line.lastIndexOf(";"), line.length()).split(",").length;
        int i = 0, j = 0;
        for (; i < hang; i++) {
            String[] split1 = split[i].split(",");
            for (; j < lie; j++) {
                xishu[i][j] = Double.parseDouble(split1[j]);
            }
        }
        String[] xzu = split[j].split(",");
        j++;
        for (int k = 0; k < lie; k++) {
            x[k] = Integer.valueOf(xzu[k]);
        }
        String[] bzu = split[j].split(",");
        j++;
        for (int k = 0; k < hang; k++) {
            b[k] = Double.parseDouble(bzu[k]);
        }
        String[] dayuzu = split[j].split(",");
        for (int k = 0; k < hang; k++) {
            dayu[k] = dayuzu[k];
        }

        boolean b1 = true;
        double max = -100000;
        for (int k = 0; k < xishu.length; k++) {
            int sum = 0;
            for (int l = 0; l < xishu[0].length; l++) {
                sum += xishu[k][l];
            }
            if (!jisuan(sum, b[k], dayu[k])) {
                b1 = false;
                break;
            }
            max = Math.max(max, sum - b[k]);
        }
        System.out.println(b1 + " " + max);


        sc.close();
    }

    public static boolean jisuan(double a, double b, String fuhao){
        if ("=".equals(fuhao)) {
            return a == b;
        } else if ("<".equals(fuhao)) {
            return a < b;
        } else if (">".equals(fuhao)) {
            return a > b;
        } else if ("<=".equals(fuhao)) {
            return a <= b;
        } else if (">=".equals(fuhao)) {
            return a >= b;
        }
        return false;
    }

    public static void bishi1(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();
        String next = scanner.nextLine();
        String[] s = next.split(" ");
        for (String char1 : s) {
            list.add(Integer.valueOf(char1));
        }
        StringBuffer sb = new StringBuffer();
        String temp = "";
        boolean b = false;

        for (int i = 0; i < list.size(); i++) {
            Integer x = list.get(i);
            if (x == 1 && i == 0) {
                sb.append("a");
                b = false;
            } else if (x == 1 && i > 0) {
                if (b) {
                    sb = new StringBuffer("a");
                } else {
                    sb.append("a");
                }
                b = false;
            } else if (x == 2 && i > 0 && b) {
                temp = sb.toString();
            } else if (x == 3 && i > 0 && b) {
                temp = sb.toString();
                sb = new StringBuffer();
                b = false;
            } else if (x == 4) {
                if (b) {
                    sb = new StringBuffer(temp);
                } else {
                    sb.append(temp);
                }
                b = false;
            } else if (x == 5) {
                b = true;
            }


        }
        System.out.println(sb.length());
        scanner.close();
    }

    // 动态规划
    public static void test27() {
        // N表示物体的个数，V表示背包的载重
        int N=4,V=10;
        //用于存储每个物体的重量，下标从1开始
        int[] weight = new int[N];
        //存储每个物体的收益，下标从1开始
         int[] value = new int[N];
        //二维数组，用来保存每种状态下的最大收益
         int[][] F = new int[N][V];
        //注意边界问题，i是从1开始的，j是从0开始的
        //因为F[i - 1][j]中i要减1
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= V; j++) {
                //如果容量为j的背包放得下第i个物体
                if (j >= weight[i]) {
                    F[i][j] = Math.max(F[i - 1][j - weight[i]] + value[i], F[i - 1][j]);
                } else {
                    //放不下，只能选择不放第i个物体
                    F[i][j] = F[i - 1][j];
                }
            }
        }
    }

    // https://www.nowcoder.com/practice/98dc82c094e043ccb7e0570e5342dd1b
    public static void test26(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s1 = sc.next();
        String s2 = sc.next();
        String shortstr = s1.length() < s2.length() ? s1 : s2;
        String longstr = s2.length() > s1.length() ? s2 : s1;
        int n = 0;
        for (int i = 0; i < shortstr.length(); i++) {
            for (int j = shortstr.length(); j > i ; j--) {
                if (longstr.contains(shortstr.substring(i, j))) {
                    n = Math.max(n, j - i);
                    continue;
                }
            }
        }
        System.out.println(n);
        sc.close();
    }

    // https://www.nowcoder.com/practice/2c81f88ecd5a4cc395b5308a99afbbec
    public static void test25(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String next = sc.nextLine();
            char[] chars = next.toCharArray();
            StringBuffer sb = new StringBuffer();
            int max = 0;
            for (int i = 0; i < chars.length; i++) {
                if (!Character.isDigit(chars[i])) {
                    continue;
                }
                int maxcount = 0;
                String str = "";
                for (int j = i + 1; j < chars.length; j++) {
                    if (Character.isDigit(chars[j])) {
                        maxcount++;
                        str = next.substring(i, j + 1);
                    } else {
                        break;
                    }
                }
                if (maxcount > max) {
                    max = maxcount;
                    sb = new StringBuffer(str);
                } else if (maxcount == max) {
                    sb.append(str);
                    max = maxcount;
                }
            }
            System.out.println(sb + "," + (max+1));
        }
        sc.close();
    }

    // https://www.nowcoder.com/practice/9999764a61484d819056f807d2a91f1e
    public static void test24(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        str = str.replace("[", "(");
        str = str.replace("]", ")");
        str = str.replace("{", "(");
        str = str.replace("}", ")");
        ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");
        System.out.println(se.eval(str));
        sc.close();
    }

    public static void test23(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            List<String> list = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                list.add(sc.next());
            }
            for (String s : list) {
                char[] chars = s.toCharArray();
                TreeMap<Character, Integer> map = new TreeMap<>();
                for (char aChar : chars) {
                    map.put(aChar, map.getOrDefault(aChar, 0) + 1);
                }
                int sum = 0;
                List<Integer> collect = map.entrySet().stream().sorted(new Comparator<Map.Entry<Character, Integer>>() {
                    @Override
                    public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                        return o2.getValue() - o1.getValue();
                    }
                }).map(e -> e.getValue()).collect(Collectors.toList());
                int count = 26;
                for (Integer integer : collect) {
                    sum += integer * count;
                    count--;
                }
                System.out.println(sum);
            }
        }
        sc.close();
    }

    // https://www.nowcoder.com/practice/cf24906056f4488c9ddb132f317e03bc
    public static void test22(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int m = sc.nextInt();
            int n = sc.nextInt();
            int[][] arr = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    arr[i][j] = sc.nextInt();
                }
            }
            List<Point> list = new ArrayList<>();
            bfs(arr, 0, 0, list);
            list.forEach(e -> {
                System.out.println("(" + e.x + "," + e.y + ")");
            });
        }
        sc.close();
    }

    public static boolean bfs(int[][] arr, int x, int y, List<Point> list) {
        list.add(new Point(x, y));
        arr[x][y] = 1;
        if (x == arr.length - 1 && y == arr[0].length - 1) {
            return true;
        }
        if (x + 1 < arr.length && arr[x + 1][y] == 0) {
            if (bfs(arr, x + 1, y, list)) {
                return true;
            }
        }
        if (y + 1 < arr[0].length && arr[x][y + 1] == 0) {
            if (bfs(arr, x, y + 1, list)) {
                return true;
            }
        }
        if (x - 1 >= 0 && arr[x - 1][y] == 0) {
            if (bfs(arr, x - 1, y, list)) {
                return true;
            }
        }
        if (y - 1 >= 0 && arr[x][y - 1] == 0) {
            if (bfs(arr, x, y - 1, list)) {
                return true;
            }
        }
        // 回溯
        list.remove(list.size() - 1);
        arr[x][y] = 0;
        return false;
    }

    public static class Point{
        private int x;
        private int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // https://www.nowcoder.com/practice/f9a4c19050fc477e9e27eb75f3bfd49c
    public static void test21(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            int[] w = new int[n];
            int[] num = new int[n];
            for (int i = 0; i < n; i++) {
                w[i] = sc.nextInt();
            }
            for (int i = 0; i < n; i++) {
                num[i] = sc.nextInt();
            }
            Set<Integer> set = new HashSet<>();
            set.add(0);
            for (int i = 0; i < n; i++) {
                ArrayList<Integer> list = new ArrayList<>(set);
                for (int j = 1; j <= num[i]; j++) {
                    for(int k=0;k<list.size();k++){
                        set.add(list.get(k) + w[i] * j);
                    }
                }
            }
            System.out.println(set.size());
        }
        sc.close();
    }

    // https://www.nowcoder.com/practice/2f6f9339d151410583459847ecc98446
    public static void test20(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int h = sc.nextInt();
            double len = (double) h;
            double temp = (double) h/2;
            for (int i = 1; i < 5; i++) {
                len += temp * 2;
                temp = temp/2;
            }
            System.out.println(len);
            System.out.println(temp);
        }
        sc.close();
    }

    // https://www.nowcoder.com/practice/6d9d69e3898f45169a441632b325c7b4
    public static void test19(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            int[] arrl = new int[n];
            int[] arrr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    if (arr[j] < arr[i]) {
                        arrl[i] = Math.max(arrl[i], arrl[j] + 1);
                    }
                }
            }

            for (int i = n - 1; i >= 0 ; i--) {
                for (int j = n - 1; j >= i ; j--) {
                    if (arr[j] < arr[i]) {
                        arrr[i] = Math.max(arrr[i], arrr[j] + 1);
                    }
                }
            }
            int max = 0;
            for (int i = 0; i < n; i++) {
                max = Math.max(max, arrl[i] + arrr[i] + 1);
            }
            System.out.println(n - max);

        }

        sc.close();
    }

    // https://www.nowcoder.com/practice/6d9d69e3898f45169a441632b325c7b4
    public static void test18(String[] args) {
        int i = 0;
        for (; i < 10; ++i) {
            System.out.println(i);
        }
        System.out.println("---");
        System.out.println(i);
    }

    // https://www.nowcoder.com/practice/119bcca3befb405fbe58abe9c532eb29
    public static void test17(String[] args) {
        Scanner sc = new Scanner(System.in);
        String next = sc.next();
        String[] split = next.split(";");
        int i = 0, j = 0;
        for (String s : split) {
            if (s.matches("[WASD][0-9]{1,2}")) {
                if (s.charAt(0) == 'A' || s.charAt(0) == 'a') {
                    i = i - Integer.valueOf(s.substring(1));
                } else if (s.charAt(0) == 'D' || s.charAt(0) == 'd') {
                    i = i + Integer.valueOf(s.substring(1));
                } else if (s.charAt(0) == 'W' || s.charAt(0) == 'w') {
                    j = j + Integer.valueOf(s.substring(1));
                } else if (s.charAt(0) == 'S' || s.charAt(0) == 's') {
                    j = j - Integer.valueOf(s.substring(1));
                }
            }
        }
        System.out.println(i + "," + j);
        sc.close();
    }

    // https://www.nowcoder.com/practice/22948c2cad484e0291350abad86136c3
    public static void test16(String[] args) {
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
