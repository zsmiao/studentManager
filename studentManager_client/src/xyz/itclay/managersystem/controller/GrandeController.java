package xyz.itclay.managersystem.controller;

import xyz.itclay.managersystem.domain.Grande;
import xyz.itclay.managersystem.domain.ListTest;
import xyz.itclay.managersystem.domain.StudentResult;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * 学生成绩管理客服类
 *
 * @author ZhangSenmiao
 * @date 2021/1/9 16:05
 **/
public class GrandeController {
    static Grande grande = new Grande();
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
        System.out.println("请选择：1.筛选条件   2.查询所有   0.退出");
        String choice = scanner.next();
        switch (choice) {
            case "1":
                filterCondition();
                break;
            case "2":
                viewAll();
                break;
            case "0":
                return;
            default:
                System.out.println("您的选择有误，请您重新选择！");
        }
    }

    /**
     * 查询所有学生信息
     */
    private static void viewAll() {
        try {
            Socket socket = StudentController.getSocket();
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write("[9],");
            bw.flush();
            socket.shutdownOutput();

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s = br.readLine();
            //释放资源
            if ("false".equals(s)) {
                System.out.println("暂无学生成绩信息，请添加后查看！");
            } else {
                System.out.println("学号\t\t\t姓名\t\tC语言\t\tJava\t\t网络");
                String[] split = s.split("、");
                for (String s1 : split) {
                    String[] split1 = s1.split(",");
                    System.out.println(split1[0] + "\t" + split1[1] + "\t\t" + split1[2] + "\t\t\t" + split1[3] + "\t\t\t" + split1[4]);
                }
            }
            br.close();
            socket.close();
        } catch (Exception e) {

        }
    }

    /**
     * 条件查询学生成绩信息
     */
    public static void conditionQuery(String sid) {
        Socket socket = StudentController.getSocket();
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write("[11]," + sid);
            bw.flush();
            socket.shutdownOutput();

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s = br.readLine();
            System.out.println("学号\t\t\t姓名\t\tC语言\t\tJava\t\t网络");
            String[] split = s.split(",");
            System.out.println(split[0] + "\t" + split[1] + "\t\t" + split[2] + "\t\t\t" + split[3] + "\t\t\t" + split[4]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void filterCondition() {
        while (true) {
            System.out.println("请选择您要筛选的条件:");
            System.out.println("1.学号  2.姓名   3.C语言   4.JAVA   5.网络  0.退出");
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    System.out.println("请输入学号:");
                    String sid = scanner.next();
                    conditionQuery(sid);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("您的输入有误!");
            }
        }
    }

    /**
     * 修改学生成绩
     */
    private static void updateGrande() {
        System.out.println("请输入要修改的学生学号:");
        String updateSid = scanner.next();
        boolean returnStudentName = returnStudentName(updateSid);
        if (returnStudentName) {
            addGrande(updateSid);
        } else {
            addGrande();
        }
    }

    /**
     * 删除学生成绩
     */
    private static void deleteGrande() {
        Socket socket = StudentController.getSocket();
        System.out.println("请输入要删除的学生学号:");
        String sid = scanner.next();
        boolean returnStudentName = returnStudentName(sid);
        if (returnStudentName) {
            try {
                OutputStream os = socket.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                bw.write("[8]," + sid);
                bw.flush();
                socket.shutdownOutput();

                //等着接收服务器响应
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String s = br.readLine();
                System.out.println(s);
                //释放资源
                br.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("您的输入有误，请您重新输入要删除的学号!");
            deleteGrande();
        }
    }

    /**
     * 添加学生成绩
     */
    private static void addGrande() {
        System.out.print("请输入学生学号>>>>>");
        String sid = scanner.next();
        boolean returnStudentName = returnStudentName(sid);
        if (returnStudentName) {
            addGrande(sid);
        } else {
            addGrande();
        }

    }

    private static void addGrande(String sid) {
        inputStudentInfo(sid);
        Socket socket = StudentController.getSocket();
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write("[7]," + grande.toString());
            bw.flush();
            socket.shutdownOutput();

            //等着接收服务器响应
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s = br.readLine();
            System.out.println(s);
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

    public static void inputStudentInfo(String sid) {
        System.out.print("请输入c语言成绩的>>>>>");
        int stuC = scanner.nextInt();
        System.out.print("请输入java成绩>>>>>");
        int stuJava = scanner.nextInt();
        System.out.print("请输入网络成绩>>>>>");
        int stuNetwork = scanner.nextInt();
        grande.setSid(sid);
        grande.setC(stuC);
        grande.setJava(stuJava);
        grande.setNetwork(stuNetwork);
        // return grande;
    }

    /**
     * 根据学号，返回学生姓名
     */
    private static boolean returnStudentName(String sid) {
        boolean flag = false;
        String s = studentName(sid);
        lo:
        while (true) {
            System.out.println("请核对学生姓名是否正确：" + s);
            System.out.println("y.正确      n.不正确");
            String choice = scanner.next();
            switch (choice) {
                case "y":
                    flag = true;
                    break lo;
                case "n":
                    break lo;
                default:
                    System.out.println("您的输入有误，请您重新核对！");
            }
        }

        return flag;
    }

    public static String studentName(String sid) {
        Socket socket = StudentController.getSocket();
        String name = "";
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write("[6]," + sid);
            bw.flush();
            socket.shutdownOutput();
            //等着接收服务器响应
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            name = br.readLine();
            //释放资源
            br.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 条件查询学生信息
     */
    public static void conditionStudent(String sid) {
        Socket socket = StudentController.getSocket();
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write("[12],"+sid);
            bw.flush();
            socket.shutdownOutput();

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s = br.readLine();

            System.out.println("学号\t\t\t姓名\t\t年龄\t\t生日");
            String[] split = s.split(",");
            System.out.println(split[0] + "\t" + split[1] + "\t\t" + split[2] + "\t\t" + split[3]);
            //释放资源
            br.close();
            socket.close();
        } catch (Exception e) {
        }
    }

    /**
     * 学生修改密码
     */
    public static void changePassword(String sid, String confirmPassword) {
        Socket socket = StudentController.getSocket();
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write("[13]," + sid+","+confirmPassword);
            bw.flush();
            socket.shutdownOutput();

            //等着接收服务器响应
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s = br.readLine();
            System.out.println(s);
            //释放资源
            br.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}