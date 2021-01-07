package xyz.itclay.managersystem.entry;

import xyz.itclay.managersystem.controller.StudentController;

import java.util.Scanner;

/**
 * 客户端启动类
 *
 * @author ZhangSenmiao
 * @date 2021/1/7 20:16
 * @version 3.0
 **/
public class ClientApplication {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        while (true) {

            System.out.println("**********黑马信息管理系统**********");
            System.out.println("请输入您的选择: 1.学生管理  2.老师管理  3.退出");
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    StudentController controller = new StudentController();
                    controller.start();
                    break;
                case "2":
                    System.out.println("老师管理");
                    break;
                case "3":
                    System.out.println("感谢您的使用");
                    System.exit(0);
                default:
                    System.out.println("您的输入有误, 请重新输入");
                    break;
            }
        }
    }
}
