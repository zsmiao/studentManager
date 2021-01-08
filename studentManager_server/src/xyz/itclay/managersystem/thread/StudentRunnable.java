package xyz.itclay.managersystem.thread;

import xyz.itclay.managersystem.domain.Student;
import xyz.itclay.managersystem.service.StudentService;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

/**
 * 处理学生业务的线程任务类
 *
 * @author ZhangSenmiao
 * @date 2021/1/7 20:47
 **/
public class StudentRunnable implements Runnable {
    private final StudentService service = new StudentService();
    private final Socket socket;

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
            System.out.println("clientMsg = " + clientMsg);

//          解析客户端传输过来的报文数据
            String[] split = clientMsg.split(",");
            String choice = split[0];
            switch (choice) {
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
                    System.out.println("您的输入有误, 请重新输入");
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
            bw.write("修改成功！");
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
            bw.write("删除成功！");
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
                    bw.write(String.valueOf(student)+"、");
                }
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
            System.out.println("-----");
            bw.write(exists + "");
            System.out.println(exists + "");
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
            System.out.println(resMsg);
            bw.flush();
            bw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

