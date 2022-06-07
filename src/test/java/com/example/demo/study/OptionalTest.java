package com.example.demo.study;

import org.junit.jupiter.api.Test;

import java.util.Optional;

 public class OptionalTest {

     @Test
     public void test3() {

     }

//     public static String getDepartmentNameOfUser(String username) {
//         return Optional.ofNullable(getUserByName(username))
//                 .map(ResultTO::getData)
//                 .map(User::getDepartment)
//                 .map(Department::getName)
//                 .orElse("未知部门");
//     }

//     public boolean sendMessage(Long fromId, Long toId, String message) {
//         // 用户校验：如果用户不存在，直接抛异常
//         User user = Optional.ofNullable(userService.getUserById(fromId))
//                 .orElseThrow(() -> new BizException(ErrorEnumCode.USER_NOT_EXIST));
//
//         // 组装数据并发送...
//     }

//     public static String getDepartmentNameOfUser(String username) {
//         return Optional.ofNullable(getUserByName(username))
//                 .map(ResultTO::getData)
//                 .map(User::getDepartment)
//                 .map(Department::getName)
//                 .orElse("未知部门");
//     }

//     public class OptionalFilterTest {
//
//         public static void main(String[] args) {
//
//             // 需求：调用getUser()得到person，并且person的age大于18才返回username，否则返回不存在
//
//             // 普通的写法（如果层级深一点会很难看）
//             Person user = getUser();
//             if (user != null && user.getAge() > 18) {
//                 System.out.println(user.getName());
//             } else {
//                 System.out.println("不存在");
//             }
//
//             // 你尝试用map()，但你发现直接返回username了，你甚至无法再次判断是否age>18
//             String username1 = Optional.ofNullable(getUser())
//                     .map(Person::getName)
//                     .orElse("不存在");
//             System.out.println("username1 = " + username1);
//
//             // 引入filter()
//             String username2 = Optional.ofNullable(getUser())
//                     .filter(person -> person.getAge() > 18)
//                     .map(Person::getName)
//                     .orElse("不存在");
//             System.out.println("username2 = " + username2);
//         }
//
//         public static Person getUser() {
//             if (RandomUtils.nextBoolean()) {
//                 return null;
//             } else {
//                 Person person = new Person();
//                 person.setName("鲍勃");
//                 // commons.lang3
//                 person.setAge(RandomUtils.nextInt(0, 50));
//                 return person;
//             }
//         }
//
//         @Data
//         static class Person {
//             private String name;
//             private Integer age;
//         }
//     }

     @Test
     public void test2(String str){
         // 支持过滤操作和映射操作
//         String str = "A";
         Optional<String> optional = Optional.ofNullable(str);   //转换为Optional（可空）
         System.out.println(optional.filter(s -> s.equals("B")).get());   //被过滤了，此时元素为null，获取时报错
     }

    @Test
    public void test1(){
        String str = null;
        Optional<String> optional = Optional.ofNullable(str);   //转换为Optional
        optional.ifPresent(System.out::println);  //当存在时再执行方法
        System.out.println(optional.orElse("abc"));
    }

}
