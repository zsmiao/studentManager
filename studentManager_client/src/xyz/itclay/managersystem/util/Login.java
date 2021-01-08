package xyz.itclay.managersystem.util;

import xyz.itclay.managersystem.controller.StudentController;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author ZhangSenmiao
 * @date 2021/1/8 12:59
 **/
public class Login {

    public static void userLogin() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader("studentManager_client\\user.txt");
        Scanner scanner = new Scanner(System.in);
        int count = 3;
        while (true) {
            System.out.println("请输入用户名:");
            String user = scanner.next();
            System.out.println("请输入密码:");
            String password = scanner.next();
            properties.load(fileReader);
            boolean b = verificationCode();
            if (b) {
                String username = properties.getProperty("username");
                String password1 = properties.getProperty("password");
                if (user.equals(username) && password.equals(password1)) {
                    System.out.println("登录成功！欢迎您" + user);
                    menu();
                    break;
                } else {
                    System.out.println("用户名或密码错误！,您还有" + (count - 1) + "次机会！");
                    count--;
                    if (count == 0) {
                        System.out.println("登录次数超限，请稍后再试！");
                        break;
                    }
                }
            } else {
                System.out.println("验证码错误！");
            }
        }
    }


    private static void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("——————————欢迎来到黑马学生信息管理系统——————————");
            System.out.println("请输入您的选择：1.学生管理 2.成绩管理 3.退出");
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    StudentController studentController = new StudentController();
                    studentController.start();
                    break;
                case "2":
                    break;
                case "3":
                    System.exit(0);
                default:
                    System.out.println("您的输入有误，请重新输入！");
                    break;
            }
        }
    }

    public static boolean verificationCode() {
        Scanner scanner = new Scanner(System.in);
        String[] code = {
                "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q",
                "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q",
                "R", "S", "T", "U", "V", "W", "X", "Y", "Z",};
        int length = 4;
        String codeStr = "";
        for (int i = 0; i < length; i++) {
            int a = (int) (Math.random() * 52);
            codeStr += code[a];
        }
        System.out.println("验证码：" + codeStr);
        String userInput = scanner.next();
        return codeStr.equalsIgnoreCase(userInput);
    }
}

/*
package com.itheima.homework.day08;

import java.util.Random;
import java.util.Scanner;

/*
    生成的验证码为：BkhD
    请输入验证码
    abcd
    输入错误，请重新输入...
    生成的验证码为：n1wX
    请输入验证码
    aaaa
    输入错误，请重新输入...
    生成的验证码为：Fm1a
    请输入验证码
    fm1a
    输入正确

public class Homework09 {
    public static void main(String[] args) {
        // 1. 拼接一个超大字符串,包含:[A-Z,a-z,0-9]
        StringBuilder str = new StringBuilder("0123456789");
        for (char i = 'a', j = 'A'; i <= 'z' && j <= 'Z'; i++, j++) {
            str.append(i).append(j) ;
        }
        //2. 通过Random生成四个大字符串的随机索引    -- [0,str.length() - 1]
        Random r = new Random();
        //4. 定义一个字符串,用来充当最后的验证码
        while (true) {
            String verifyCode = new String();
            for (int i = 1; i <= 4; i++) {
                int index = r.nextInt(str.length());
                char ch = str.toString().charAt(index);
                //3. 通过charAt()获取指定随机索引位置对应的字符,拼接成一个新的字符串,这个新的字符串就是我们要的验证码字符串
                verifyCode += ch;
            }
            System.out.println("系统生成验证码: " + verifyCode);

            // 5. 键盘录入验证码   -- 把用户输入的验证码拿到
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入验证码");
            String code = sc.next();

            //6. 比较用户录入的验证码和系统生成的验证码是否匹配   -- 忽略大小写比较
            if (verifyCode.equalsIgnoreCase(code)) {
                System.out.println("输入正确");
                //结束循环
                break;
            } else {
                System.out.println("输入错误，请重新输入...");
            }
        }

    }
}

 */