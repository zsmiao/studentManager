package xyz.itclay.managersystem.controller;

import xyz.itclay.managersystem.domain.Grande;
import xyz.itclay.managersystem.domain.Student;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * 学生成绩管理客服类
 *
 * @author ZhangSenmiao
 * @date 2021/1/9 16:05
 **/
public class GrandeController {
    static Scanner scanner = new Scanner(System.in);

    public static void start() {
        while (true) {
            System.out.println("--------欢迎来到 <成绩> 管理系统--------");
            System.out.println("请输入您的选择: 1.添加成绩  2.删除成绩  3.修改成绩  4.查看成绩  5.退出");

            String choice = scanner.next();
            switch (choice) {
                case "1":
                    addGrande();
                    break;
                case "2":
                    deleteGrande();
                    break;
                case "3":
                    updateGrande();
                    break;
                case "4":
                    findAllGrande();
                    break;
                case "5":
                    System.out.println("感谢您使用学生管理系统, 再见!");
                    return;
                default:
                    System.out.println("您的输入有误, 请重新输入");
                    break;
            }
        }
    }

    /**
     * 查看学生成绩
     */
    private static void findAllGrande() {
    }

    /**
     * 修改学生成绩
     */
    private static void updateGrande() {
    }

    /**
     * 删除学生成绩
     */
    private static void deleteGrande() {
    }

    /**
     * 添加学生成绩
     */
    private static void addGrande() {
        System.out.println("**********添加学生信息***********");
        System.out.print("请输入要添加成绩的学生学号>>>>>");
        String sid = scanner.next();
        Socket socket = StudentController.getSocket();
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write("[6]," + sid);
            bw.flush();
            socket.shutdownOutput();
            //等着接收服务器响应
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s = br.readLine();
            lo:
            while (true) {
                System.out.println("请核对学生姓名是否正确：" + s );
                System.out.println("y.正确      n.不正确");
                String choice = scanner.next();
                switch (choice) {
                    case "y":
                        inputStudentInfo(sid);
                        break lo;
                    case "n":
                        addGrande();
                        break lo;
                    default:
                        System.out.println("您的输入有误，请您重新核对！");
                }
            }
            //释放资源
            br.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 录入学生成绩
     */

    public static Grande inputStudentInfo(String sid) {
        Grande grande = new Grande();
        System.out.print("请输入c语言成绩的>>>>>");
        int stuC = scanner.nextInt();
        System.out.print("请输入java成绩>>>>>");
        int stuJava = scanner.nextInt();
        System.out.print("请输入网络成绩>>>>>");
        int stuNetwork = scanner.nextInt();
        System.out.print("请输入C#成绩>>>>>");
        int stuCSharp = scanner.nextInt();
        grande.setSid(sid);
        grande.setC(stuC);
        grande.setC(stuJava);
        grande.setC(stuNetwork);
        grande.setC(stuCSharp);
        return grande;
    }
}