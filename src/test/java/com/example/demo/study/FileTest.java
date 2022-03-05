package com.example.demo.study;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileTest {

    @Test
    public void test6(){
        // File类
        File file = new File("test.txt");   //直接创建文件对象，可以是相对路径，也可以是绝对路径
        System.out.println(file.exists());   //此文件是否存在
        System.out.println(file.length());   //获取文件的大小 字节
        System.out.println(file.isDirectory());   //是否为一个文件夹
        System.out.println(file.canRead());   //是否可读
        System.out.println(file.canWrite());   //是否可写
        System.out.println(file.canExecute());   //是否可执行

        // 将一个文件夹下的所有文件拷贝到另一个文件夹下
        File file1 = new File(".idea/"); // 文件夹
        File target = new File("new/");
        target.mkdir();
        for (File listFile : file1.listFiles()) {
            try(FileInputStream fileInputStream = new FileInputStream(listFile);
                FileOutputStream fileOutputStream = new FileOutputStream(target.getPath() + "/" + listFile.getName())){
                byte b[] = new byte[100];
                int temp;
                while ((temp = fileInputStream.read(b)) != -1) {
                    fileOutputStream.write(b, 0, temp);
                    fileOutputStream.flush();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    @Test
    public void test5(){
        //reader,writer进行文件拷贝
        try (FileReader fileReader = new FileReader("test.txt");
             FileWriter fileWriter = new FileWriter("writerout.txt")) {
            char[] chars = new char[10];
            int temp;
            while ((temp = fileReader.read(chars)) != -1){
                fileWriter.write(chars, 0, temp);
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4(){
        try(FileOutputStream outputStream = new FileOutputStream("out.txt");
            FileInputStream inputStream = new FileInputStream("test.txt")) {   //可以写入多个
            byte[] bytes = new byte[10];    //使用长度为10的byte[]做传输媒介
            int tmp;   //存储本地读取字节数
            while ((tmp = inputStream.read(bytes)) != -1){   //直到读取完成为止
                outputStream.write(bytes, 0, tmp);    //写入对应长度的数据到输出流
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void test3(){
        byte[] bytes = "字节数".getBytes();
        for (byte aByte : bytes) {
            System.out.println((char) aByte);
        }

        System.out.println("------");

        try (FileInputStream inputStream = new FileInputStream("test.txt")) {
            byte[] bytes1 = new byte[10];
            System.out.println(inputStream.read(bytes1, 1, 2)); // 读取bytes数组，从索引为1开始读取长度为2 返回值是读取的字节数
            System.out.println(new String(bytes1));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("------");

        try (FileOutputStream fileOutputStream = new FileOutputStream("out.txt")) {
            fileOutputStream.write("abddd".getBytes(), 3, 2);
        } catch (IOException e){
            e.printStackTrace();
        }



    }

    @Test
    public void test2(){
        //test.txt：abcd
        try(FileInputStream inputStream = new FileInputStream("test.txt")) {
            int tmp;
            while ((tmp = inputStream.read()) != -1){   //通过while循环来一次性读完内容
                System.out.print((char)tmp);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void test1() {
        // 自动管理文件流
        try(FileInputStream inputStream = new FileInputStream("test.txt")){
            System.out.println((char) inputStream.read());
            System.out.println((char) inputStream.read());
        }catch (IOException e){
            e.printStackTrace();
        }

        // 这样写太繁琐
//        FileInputStream fileInputStream = null;
//        try {
//            fileInputStream = new FileInputStream("test.txt");
//            FileDescriptor fd = fileInputStream.getFD();
//            System.out.println(fd);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fileInputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

}
