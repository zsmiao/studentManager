package xyz.itclay.managersystem.controller;

import xyz.itclay.managersystem.domain.Student;
import xyz.itclay.managersystem.util.DateUtil;
import xyz.itclay.managersystem.util.UserDataVerification;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author ZhangSenmiao
 * @date 2021/1/7 20:22
 **/
public class StudentController {
    static Scanner scanner = new Scanner(System.in);
    static UserDataVerification udv = new UserDataVerification();

    /**
     * 学生信息管理菜单
     */
    public void start() {
        while (true) {
            System.out.println("--------欢迎来到 <学生> 管理系统--------");
            System.out.println("请输入您的选择: 1.添加学生  2.删除学生  3.修改学生  4.查看学生  5.退出");

            String choice = scanner.next();
            switch (choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    deleteStudent();
                    break;
                case "3":
                    updateStudent();
                    break;
                case "4":
                    findAllStudent();
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
     * 修改学生
     */
    private void updateStudent() {
        System.out.println("请输入要修改学生的学号：");
        String updateStudentId = inputId(true);
        Student student = inputStudentInfo(updateStudentId);
        Socket socket = getSocket();
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write("[3]," + updateStudentId + "," + student);
            bw.flush();
            socket.shutdownOutput();

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            System.out.println(br.readLine());

            //释放资源
            br.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("服务器连接失败！");
        }
    }

    /**
     * 删除学生
     **/
    private void deleteStudent() {
        System.out.println("请输入要删除学生的学号:");
        String deleteId = inputId(true);
        Socket socket = getSocket();
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write("[2]," + deleteId);
            bw.flush();
            socket.shutdownOutput();

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            System.out.println(br.readLine());

            //释放资源
            br.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("服务器连接失败！");
        }

    }

    /**
     * 查看学生
     **/
    private void findAllStudent() {
        Socket socket = getSocket();
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write("[4],");
            bw.flush();
            socket.shutdownOutput();

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s = br.readLine();

            //释放资源
            br.close();
            socket.close();
            if ("false".equals(s)) {
                System.out.println("暂无学生信息，请添加后查看！");
            } else {
                System.out.println("学号\t\t\t姓名\t\t年龄\t\t生日");
                String[] splitStr = s.split("、");
                for (String s1 : splitStr) {
                    String[] split = s1.split(",");
                    System.out.println(split[0] + "\t" + split[1] + "\t\t" + split[2] + "\t\t" + split[3]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加学生
     */
    private void addStudent() {
        System.out.println("请输入学号:");
        String sid = inputId(false);
        Student stu = inputStudentInfo(sid);
        //将封装好的学生数据发socket请求到服务器
        Socket socket = getSocket();
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write("[1]," + stu.toString());
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
     * 录入学生信息
     */
    public Student inputStudentInfo(String sid) {
        Student stu = new Student();
        while (true) {
            System.out.println("请输入姓名:");
            String name = scanner.next();
            if (!udv.nameIsLicit(name)) {
                System.out.println("姓名输入有误，请重新输入！");
            } else {
                System.out.println("请输入生日,格式示例[1999-11-11]:");
                String birthday = scanner.next();
                if (udv.timeIsLicit(birthday)) {
                    //根据生日,计算年龄
                    String age = DateUtil.getAge(birthday);

                    stu.setSid(sid);
                    stu.setName(name);
                    stu.setAge(age);
                    stu.setBirthday(birthday);
                    break;
                } else {
                    System.out.println("日期有误！请重新输入！");
                }
            }
        }
        return stu;
    }

    /**
     * 判断学号是否可用
     */
    public static String inputId(boolean flag) {
        String id;
        boolean exists = false;
        while (true) {
            id = scanner.next();
            if (!udv.idIsLicit(id)) {
                System.out.println("学号输入有误，示例：heima001");
            } else {
                //1. 拿socket
                try {
                    Socket socket = getSocket();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bw.write("[5]," + id);
                    bw.flush();
                    socket.shutdownOutput();

                    //获取服务器响应的数据
                    InputStream is = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String s = br.readLine();
                    br.close();
                    socket.close();
                    if (!"false".equals(s)) {
                        exists = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (exists == flag) {
                    break;
                } else {
                    exists = false;
                    System.out.println("当前id不可用,请重新输入!");
                }
            }
        }
        return id;
    }

    public static Socket getSocket() {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 9998);
        } catch (IOException e) {
            System.out.println("服务器连接失败,请稍后在试！");
        }
        return socket;
    }
}
