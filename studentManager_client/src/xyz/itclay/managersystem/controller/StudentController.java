package xyz.itclay.managersystem.controller;

import xyz.itclay.managersystem.domain.Student;
import xyz.itclay.managersystem.util.DateUtil;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author ZhangSenmiao
 * @date 2021/1/7 20:22
 **/
public class StudentController {
    Scanner scanner = new Scanner(System.in);

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
                    System.out.println("删除学生");
                    break;
                case "3":
                    System.out.println("修改学生");
                    break;
                case "4":
                    findAllStudent();
                    System.out.println("查看学生");
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
            if ("false".equals(s)){
                System.out.println("暂无学生信息，请添加后查看！");
            }else {
                String[] split = s.split(",");
                System.out.println("学号\t\t\t姓名\t\t年龄\t\t生日");
                System.out.println(split[0] + "\t" +split[1] + "\t\t" + split[2] + "\t\t" + split[3]);
            }
            //将服务器响应回来的数据输出到控制台
            System.out.println(s);
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
            //[1],heima001,张三,23,1999-11-11
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write("[1]," + stu.toString());
            bw.flush();
            socket.shutdownOutput();

            //等着接收服务器响应
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s = br.readLine();

            //释放资源
            br.close();
            socket.close();

            //将服务器响应回来的数据输出到控制台
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Student inputStudentInfo(String sid) {
        System.out.println("请输入姓名:");
        String name = scanner.next();
        System.out.println("请输入生日,格式示例[1999-11-11]:");
        String birthday = scanner.next();
        //根据生日,计算年龄
        String age = DateUtil.getAge(birthday);

        //2. 封装学生对象,然后把封装好的对象交给业务员Service,指挥业务员完成添加业务
        // 增量开发原则: 开闭原则: 对扩展开放,对修改关闭
        Student stu = new Student();
        stu.setSid(sid);
        stu.setName(name);
        stu.setAge(age);
        stu.setBirthday(birthday);

        return stu;
    }

    public String inputId(boolean flag) {
        String id;
        boolean exists = false;
        while (true) {
            id = scanner.next();
            //1. 拿socket
            try {
                Socket socket = getSocket();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bw.write("[5]," + id);
                bw.flush();
                //bw.close();
                socket.shutdownOutput();

                //获取服务器响应的数据
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String s = br.readLine();
                br.close();
                System.out.println(s);
                socket.close();
                if (!"false".equals(s)) {
                    exists = true;
                }
                System.out.println(exists);

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
        return id;
    }

    private Socket getSocket() {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }
}
