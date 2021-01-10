package xyz.itclay.managersystem.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.itclay.managersystem.domain.Grande;
import xyz.itclay.managersystem.domain.Student;
import xyz.itclay.managersystem.domain.StudentResult;
import xyz.itclay.managersystem.entry.ServerApplication;
import xyz.itclay.managersystem.service.GrandService;
import xyz.itclay.managersystem.service.StudentService;
import xyz.itclay.managersystem.util.SystemTime;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Properties;


/**
 * 处理学生业务的线程任务类
 *
 * @author ZhangSenmiao
 * @date 2021/1/7 20:47
 **/
public class StudentRunnable implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerApplication.class);
    private final StudentService service = new StudentService();
    private final GrandService grandService = new GrandService();
    private final Socket socket;
    String loginUserName = "";

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
            LOGGER.info("客户端信息:" + clientMsg);

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
                    break;
                case "[4]":
                    findAllStudent();
                    break;
                case "[5]":
                    idIsExists(split[1]);
                    break;
                case "[6]":
                    addGrand(split[1]);
                    break;
                case "[7]":
                    Grande grande = new Grande(split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]));
                    addGrand(grande);
                    break;
                case "[8]":
                    deleteGrand(split[1]);
                    break;
                case "[9]":
                    showAll();
                    break;
                case "[10]":
                    isStudent(split[1], split[2]);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断学生登录
     */
    private void isStudent(String s, String s1) {

    }

    /**
     * 查询学生成绩
     */
    private void showAll() {
        List<StudentResult> list = grandService.showAll();
        if (list.size() == 0) {
            try {
                OutputStream os = socket.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                String str = "false";
                bw.write(str);
                bw.flush();
                bw.close();
                socket.close();
            } catch (Exception e) {
                LOGGER.error("连接异常" + e);
            }
        } else {
            try {
                OutputStream os = socket.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                for (StudentResult studentResult : list) {
                    bw.write(String.valueOf(studentResult) + "、");
                    System.out.println(studentResult.toString());
                }
                LOGGER.info(loginUserName + "用户查看所有学生的成绩...");
                bw.flush();
                socket.shutdownOutput();
            } catch (Exception e) {
                LOGGER.error("查看学生失败" + e);
            }
        }
    }

    /**
     * 删除学生成绩
     */
    private void deleteGrand(String sid) {
        boolean result = grandService.deleteGrand(sid);
        String str = "";
        if (result) {
            str = "删除成功";
        } else {
            str = "删除失败";
        }
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(str);
            SystemTime.nowSystemTime();
            System.out.println("：" + loginUserName + "用户删除学生成绩：" + str + ",学生学号为：" + sid);
            bw.flush();
            bw.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("删除失败！");
        }
    }

    /**
     * 添加学生成绩
     */

    private void addGrand(String sid) {
        returnName(sid);
    }

    private void addGrand(Grande grande) {
        boolean res = grandService.addGrand(grande);
        String resMsg = "添加成功";
        if (!res) {
            resMsg = "添加失败";
        }
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(resMsg);
            SystemTime.nowSystemTime();
            System.out.println("：" + loginUserName + "用户添加学生成绩：" + resMsg + ",学生成绩为：" + grande.toString());
            LOGGER.info(loginUserName + "用户添加学生成绩：" + resMsg + ",学生成绩为：" + grande.toString());
            bw.flush();
            bw.close();
            socket.close();
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
            String str = "修改成功！";
            bw.write(str);
            LOGGER.info(loginUserName + "用户修改学生：" + str + "学生信息为：" + student.toString());
            bw.flush();
            bw.close();
            socket.close();
        } catch (Exception e) {
            LOGGER.error("用户修改学生信息失败" + e);
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
            String str = "删除成功！";
            bw.write(str);
            SystemTime.nowSystemTime();
            LOGGER.info(loginUserName + "用户删除学生：" + str + ",学生学号为：" + deleteId);
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
                LOGGER.info(loginUserName + "用户查看所有学生...");
                bw.flush();
                bw.close();
                socket.shutdownOutput();
            } catch (Exception e) {
                LOGGER.error("查看学生失败" + e);
            }
        }
    }

    /**
     * 判断id是否存在
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
            LOGGER.info(loginUserName + "用户添加学生：" + resMsg + ",学生信息为：" + stu.toString());
            bw.flush();
            bw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断用户登录
     */
    public void isUser(String user, String password) throws IOException {
        InetAddress address = socket.getInetAddress();
        String hostAddress = address.getHostAddress();
        int port = socket.getLocalPort();
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
            LOGGER.info(username + "用户" + resMsg + ",IP为:" + hostAddress + ":" + port);
            loginUserName = user;
            bw.flush();
            bw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据学号，在数据库中查询，返回学生姓名
     */
    public void returnName(String sid) {
        String name = grandService.returnName(sid);
        try {
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(name);
            bw.flush();
            bw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

