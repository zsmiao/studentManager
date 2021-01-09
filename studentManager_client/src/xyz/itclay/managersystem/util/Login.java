package xyz.itclay.managersystem.util;

import xyz.itclay.managersystem.controller.GrandeController;
import xyz.itclay.managersystem.controller.StudentController;

import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

/**
 * 用户登录
 *
 * @author ZhangSenmiao
 * @date 2021/1/8 12:59
 **/
public class Login {

    public static void userLogin(){
        Scanner scanner = new Scanner(System.in);
        int count = 3;
        while (true) {
            System.out.println("请输入用户名:");
            String user = scanner.next();
            System.out.println("请输入密码:");
            String password = scanner.next();
            verificationCode();
            Socket socket = StudentController.getSocket();
            try {
                OutputStream os = socket.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                bw.write("[0],"+user+","+password);
                bw.flush();
                socket.shutdownOutput();

                //等着接收服务器响应
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String s = br.readLine();
                if ("登录成功".equals(s)){
                    System.out.println(s+",欢迎您！"+user);
                    System.out.print("当前登录时间为：");
                    SystemTime.nowSystemTime();
                    menu();
                }else {
                    System.out.println("用户名或密码错误！,您还有" + (count - 1) + "次机会！");
                    count--;
                    if (count == 0) {
                        System.out.println("登录次数超限，请稍后再试！");
                        break;
                    }
                }
                //释放资源
                br.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 主菜单
     */
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
                    System.out.println("学生成绩管理系统");
                   GrandeController.start();
                    break;
                case "3":
                    System.exit(0);
                default:
                    System.out.println("您的输入有误，请重新输入！");
                    break;
            }
        }
    }

    /**
     * 生成验证码
     */
    public static void verificationCode() {
        StringBuilder str = new StringBuilder("0123456789");
        for (char i = 'a', j = 'A'; i <= 'z' && j <= 'Z'; i++, j++) {
            str.append(i).append(j);
        }
        Random random = new Random();
        boolean flag = false;
        while (true) {
            String verifyCode = new String();
            for (int i = 1; i <= 4; i++) {
                int index = random.nextInt(str.length());
                char ch = str.toString().charAt(index);
                verifyCode += ch;
            }
            System.out.println("验证码: " + verifyCode);
            Scanner sc = new Scanner(System.in);
            System.out.print("请输入验证码:");
            String code = sc.next();
            if (verifyCode.equalsIgnoreCase(code)) {
                break;
            } else {
                System.out.println("输入错误，请重新输入...");
            }
        }
    }
}
