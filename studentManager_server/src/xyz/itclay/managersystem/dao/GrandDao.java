package xyz.itclay.managersystem.dao;

import xyz.itclay.managersystem.domain.Grande;
import xyz.itclay.managersystem.util.PropertiesUtil;
import xyz.itclay.managersystem.util.SystemTime;

import java.sql.*;

/**
 * @author ZhangSenmiao
 * @date 2021/1/9 16:56
 **/
public class GrandDao {
    private static final String ADD_STUDENT = "insert into tb_Grandes(Student_Id,Student_Name) values (?,?)";
    private static final String DELETE_STUDENT = "delete from tb_Grandes where Student_Id=?";
    private static final String UPDATE_STUDENT = "update tb_Grandes set Student_Name=? where Student_Id=?";
    private static final String FIND_NAME = "select Student_Name from tb_Grandes where Student_Id=?";
    private static final String ADD_GRAND = "update tb_Grandes set Student_C=?,Student_Java=?,Student_Network=? where Student_Id=?";
    public static Connection conn;

    /**
     * 连接数据库
     */
    static {
        try {
            String driver = PropertiesUtil.drive().getProperty("DRIVER");
            String url = PropertiesUtil.drive().getProperty("URL");
            String user = PropertiesUtil.drive().getProperty("USER");
            String pwd = PropertiesUtil.drive().getProperty("PWD");
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pwd);
            SystemTime.nowSystemTime();
            System.out.println("：数据库连接成功!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("：数据库连接失败！");
        }

    }

    /**
     * 添加学生信息的时候取出学号和姓名，写入数据库中
     **/
    public static void addStudentInfo(String sid, String name) {
        try {
            PreparedStatement ps = conn.prepareStatement(ADD_STUDENT);
            ps.setString(1, sid);
            ps.setString(2, name);
            if (ps.executeUpdate() > 0) {
                SystemTime.nowSystemTime();
                System.out.println("：添加学生信息成功！学生信息为:" + sid + name);
            } else {
                SystemTime.nowSystemTime();
                System.out.println("：添加学生信息到数据库失败！");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 删除学生信息,根据学生学号
     */

    public static void remove(String sid) {
        try {
            PreparedStatement ps = conn.prepareStatement(DELETE_STUDENT);
            ps.setString(1, sid);
            if (ps.executeUpdate() > 0) {
                SystemTime.nowSystemTime();
                System.out.println("：删除学生信息成功！学生学号为:" + sid);
            } else {
                SystemTime.nowSystemTime();
                System.out.println("：删除学生信息失败！学生的学号为：" + sid);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 修改学生信息,根据用户修改的内容，修改学生的姓名
     */

    public static void update(String sid, String name) {
        try {
            PreparedStatement ps = conn.prepareStatement(UPDATE_STUDENT);
            ps.setString(2, sid);
            ps.setString(1, name);
            if (ps.executeUpdate() > 0) {
                SystemTime.nowSystemTime();
                System.out.println("：修改数据库学生信息成功！修改的学生信息为:" + sid);
            } else {
                SystemTime.nowSystemTime();
                System.out.println("：修改学生信息失败！");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * 根据学生学号，查询学生姓名，并返回
     *
     * @param sid
     * @return
     */
    public String findName(String sid) {
        String name = "";
        try {
            PreparedStatement ps = conn.prepareStatement(FIND_NAME);
            ps.setString(1, sid);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                name = resultSet.getString(1);
            }
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return name;
    }

    /**
     * 添加学生成绩
     *
     * @param grande
     * @return
     */
    public boolean addGrand(Grande grande) {
        Boolean flag = false;
        try {
            PreparedStatement ps = conn.prepareStatement(ADD_GRAND);
            ps.setString(4, grande.getSid());
            ps.setString(1, String.valueOf(grande.getC()));
            ps.setString(2, String.valueOf(grande.getJava()));
            ps.setString(3, String.valueOf(grande.getNetwork()));
            if (ps.executeUpdate() > 0) {
                SystemTime.nowSystemTime();
                System.out.println("：添加学生成绩成功！学生成绩为:" + grande.toString());
                flag = true;
            } else {
                SystemTime.nowSystemTime();
                System.out.println("：添加学生信息到数据库失败！");

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }
}
