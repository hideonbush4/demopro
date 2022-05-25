package com.example.demo.study;

import com.example.demo.BaseTest;
import com.example.demo.domain.entity.User;
import com.example.demo.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author dengzhewen
 * @create 2022-03-04 9:37
 * @Version v1.0.0
 */
class StreamTest extends BaseTest {

    @Autowired
    UserService userService;

    @Test
    public void test11() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));

        List<String> limit3 = list.stream().limit(3).collect(Collectors.toList());
        // 超出实际长度也不会报错
        List<String> limit5 = list.stream().limit(5).collect(Collectors.toList());
        List<String> range3_4 = list.stream().skip(2).limit(2).collect(Collectors.toList());
        // 超出实际长度也不会报错
        List<String> range3_5 = list.stream().skip(2).limit(3).collect(Collectors.toList());

        System.out.println(limit3 + " " + limit5 + " " + range3_4 + " " + range3_5);
    }

    @Test
    public void test10(){
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "aaa"));
        userList.add(new User(2L, "bbb"));
        userList.add(new User(3L, "ccc"));
        userList.add(new User(2L, "ddd"));
        userList.add(new User(3L, "eee"));

        // 报错因为user的id重复
//        Map<Integer, String> map = userList.stream().collect(Collectors.toMap(User::getId, User::getName));

        // 解决方法1：取前面value的值，或者取后面放入的value值，则覆盖先前的value值
        Map<Integer, String> map2 = userList.stream().collect(Collectors.toMap(User::getId, User::getName, (v1, v2) -> v1)); // 保留第一个
        Map<Integer, String> map3 = userList.stream().collect(Collectors.toMap(User::getId, User::getName, (v1, v2) -> v2)); // 后面覆盖前面
        for (Integer integer : map2.keySet()) {
            System.out.println(integer + "===" + map2.get(integer));
        }
        for (Integer integer : map3.keySet()) {
            System.out.println(integer + "===" + map3.get(integer));
        }

        // 方法2：使用Function.identity()
        Map<Integer, User> map4 = userList.stream().collect(Collectors.toMap(User::getId, Function.identity(), ((user, user2) -> user2)));
        for (Integer integer : map4.keySet()) {
            System.out.println(integer + "===" + map4.get(integer));
        }
    }


    @Test
    public void test9(){
        // 值传递和应用传递
        Map<User, String> map = new HashMap<>();
        User user = new User().setName("11");
        map.put(user, "1");
        user.setName("22");
        System.out.println(map.get(user));
        int a = 1;
        System.out.println(tt(a));
        System.out.println(a);
        System.out.println("----------");
        String s = "123";
        System.out.println(ss(s));
        System.out.println(s);
        System.out.println("----------");
        Integer i = 1;
        System.out.println(tt2(i));
        System.out.println(i);
        System.out.println("-----------");
        StringBuffer sb = new StringBuffer("sb");
        System.out.println(ss2(sb));
        System.out.println(sb);
    }

    public int tt(int s){
        s = 2;
        return s + 1;
    }

    public String ss(String s){
        s = "abc";
        return s;
    }

    public Integer tt2(Integer i){
//        i = 3;
        i = new Integer(3);
        return i;
    }

    public StringBuffer ss2(StringBuffer stringBuffer){
        stringBuffer.append("abb");
        return stringBuffer;
    }

    @Test
    public void test8(){
        Integer[] array = {1, 5, 2, 4, 7, 3, 6};
        // 该list本质上还是一个数组
        List<Integer> list = Arrays.asList(array);   //不支持基本类型数组，必须是对象类型数组
        Arrays.asList("A", "B", "C");  //也可以逐个添加，因为是可变参数
//        ArrayList<Integer> arrayList = new ArrayList<>(list);
//        arrayList.add(7);
//        System.out.println(arrayList);

        list.add(1);    //此List实现是长度固定的，是Arrays内部单独实现的一个类型，因此不支持添加操作
        list.remove(0);   //同理，也不支持移除

        list.set(0, 8);   //直接设置指定下标的值就可以
        list.sort(Comparator.reverseOrder());   //也可以执行排序操作
        System.out.println(list);   //也可以像List那样直接打印
    }

    @Test
    public void test7(){
        User[] users = {new User(), new User()};
        int[] array = {1, 5, 2, 4, 7, 3, 6};
        Arrays
                .stream(array)    //将数组转换为流进行操作
                .sorted()
                .forEach(System.out::println);

    }

    @Test
    public void test3() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        // 使用iterator.remove()移除，迭代器list移除元素可以, list增加元素不行（增加元素必须深拷贝后） map同理
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals("B")) iterator.remove();
//            if(iterator.next().equals("B")) list.remove(iterator.next());
//            if (iterator.next().equals("C")) {
//                list.add("D");
//            }
        }
        System.out.println(list);

        Map<String, String> map = new HashMap<>();
        map.put("1", "A");
        map.put("2", "B");
        map.put("3", "C");
        Iterator<String> iterator1 = map.keySet().iterator();
        while (iterator1.hasNext()) {
            String key = iterator1.next();
            String value = map.get(key);
            if (key.equals("2")) {
                map.remove("1");
            }
//            if (key.equals("2")) {
//                map.put("4", "D");
//            }
        }
        System.out.println(map.toString());
    }

    @Test
    public void test6(){
        // reduce
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int sum = list
                .stream()
                .reduce((a, b) -> a + b)   //计算规则为：a是上一次计算的值，b是当前要计算的参数，这里是求和
                .get();    //我们发现得到的是一个Optional类实例，不是我们返回的类型，通过get方法返回得到的值
        System.out.println(sum);
    }

    @Test
    public void test5(){
        // flatMap
        List<String> list = new ArrayList<>();
        list.add("A,B");
        list.add("C,D");
        list.add("E,F");   //我们想让每一个元素通过,进行分割，变成独立的6个元素
        list = list
                .stream()    //生成流
                .flatMap(e -> Arrays.stream(e.split(",")))    //分割字符串并生成新的流
                .collect(Collectors.toList());   //汇成新的List
        System.out.println(list);   //得到结果
    }

    @Test
    public void test4() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);

        list = list
                .stream()
                .distinct()   //去重（使用equals判断）
                .sorted((a, b) -> b - a)    //进行倒序排列
                .map(e -> e + 1)    //每个元素都要执行+1操作
                .limit(2)    //只放行前两个元素
                .collect(Collectors.toList());

        System.out.println(list);
    }

    @Test
    public void test2() {
        List<User> list = userService.list();

        // 从小到大排序
        list.sort((o1, o2) -> o1.getAge().compareTo(o2.getAge()));

        // 同上
        list.sort(Comparator.comparingInt(user -> user.getAge()));
        list.sort(Comparator.comparingInt(User::getAge));

        // 进行相反的排序
        list.sort(Comparator.comparingInt(User::getAge).reversed());

        // 从大到小排序
        list.sort(Comparator.comparing(
                User::getAge, (s1, s2) -> {
                    return s2.compareTo(s1);
                }
        ));
    }

    @Test
    public void test1() {

        List<User> list = userService.list();
        // 使用Collectors.groupingBy返回的map的key是name，value是list<User>
        Map<String, List<User>> collect = list.stream().collect(Collectors.groupingBy(User::getName));
        System.out.println("---");

    }

}
