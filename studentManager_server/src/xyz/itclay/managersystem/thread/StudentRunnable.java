package xyz.itclay.managersystem.thread;

import xyz.itclay.managersystem.domain.Student;
import xyz.itclay.managersystem.service.StudentService;
import xyz.itclay.managersystem.util.SystemTime;
import xyz.itclay.managersystem.util.UserLogin;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Properties;

/**
 * 处理学生业务的线程任务类
 *
 * @author ZhangSenmiao
 * @date 2021/1/7 20:47
 **/
public class StudentRunnable implements Runnable {
    private final StudentService service = new StudentService();
    private final Socket socket;
    String loginUserName="";

    public StudentRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
//          取出本次socket会话中客户端传来的数据
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String clientMsg = br.readLine();


//          解析客户端传输过来的报文数据
            String[] split = clientMsg.split(",");
            String choice = split[0];
            switch (choice) {
                case "[0]":
                    isUser(split[1], split[2]);
                    break;
                case "[1]":
                    addStudent(split[1], split[2], split[3], split[4]);
                    break;
                case "[2]":
                    deleteStudent(split[1]);
                    break;
                case "[3]":
                    Student student = new Student(split[2], split[3], split[4], split[5]);
                    updateStudent(split[1], student);
                case "[4]":
                    findAllStudent();
                    break;
                case "[5]":
                    idIsExists(split[1]);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改学生
     */
    private void updateStudent(String updateStudentId, Student student) {
        service.updateStudent(updateStudentId, student);
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            String str="修改成功！";
            bw.write(str);
            SystemTime.nowSystemTime();
            System.out.println("：" + loginUserName + "用户修改学生：" + str+"学生信息为："+student.toString());
            bw.flush();
            bw.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("修改失败！");
        }
    }

    /**
     * 删除学生
     */

    private void deleteStudent(String deleteId) {
        service.deleteStudent(deleteId);
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            String str="删除成功！";
            bw.write(str);
            SystemTime.nowSystemTime();
            System.out.println("：" + loginUserName + "用户删除学生：" + str+",学生学号为："+deleteId);
            bw.flush();
            bw.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("删除失败！");
        }
    }

    /**
     * 查看学生
     */
    private void findAllStudent() {
        Student[] stus = service.findAllStudent();
        if (stus == null) {
            try {
                OutputStream os = socket.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                String str = "false";
                bw.write(str);
                bw.flush();
                bw.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                OutputStream os = socket.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                for (Student student : stus) {
                    bw.write(String.valueOf(student) + "、");
                }
                SystemTime.nowSystemTime();
                System.out.println("：" + loginUserName + "用户查看所有学生...");
                bw.flush();
                bw.close();
                socket.shutdownOutput();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 判断id是否存在
     *
     * @param sid
     */
    private void idIsExists(String sid) {
        boolean exists = service.isExists(sid);
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(exists + "");
            bw.flush();
            bw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 客服类中添加学生的方法
     *
     * @param sid
     * @param name
     * @param age
     * @param birthday
     */
    public void addStudent(String sid, String name, String age, String birthday) {
        //1. 封装学生对象
        Student stu = new Student(sid, name, age, birthday);

        boolean res = service.addStudent(stu);

        //3. 根据业务员返回的结果,给出对应的提示信息展示给用户
        String resMsg = "添加成功";
        if (!res) {
            resMsg = "添加失败";
        }
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(resMsg);
            SystemTime.nowSystemTime();
            System.out.println("：" + loginUserName+ "用户添加学生：" + resMsg + ",学生信息为："+stu.toString());
            bw.flush();
            bw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void isUser(String user, String password) throws IOException {
        String hostAddress = socket.getInetAddress().getHostAddress();
        Properties properties = new Properties();
        FileReader fileReader = new FileReader("studentManager_server\\user.txt");
        properties.load(fileReader);
        String username = properties.getProperty("username");
        String password1 = properties.getProperty("password");
        String resMsg = "";
        if (user.equals(username) && password.equals(password1)) {
            resMsg = "登录成功";
        } else {
            resMsg = "登录失败";
        }
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(resMsg);
            SystemTime.nowSystemTime();
            System.out.println("：" + username + "用户" + resMsg + ",IP为:" + hostAddress);
            loginUserName=user;
            bw.flush();
            bw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

