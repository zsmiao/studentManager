package xyz.itclay.managersystem.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.itclay.managersystem.domain.Grande;
import xyz.itclay.managersystem.domain.StudentResult;
import xyz.itclay.managersystem.entry.ServerApplication;
import xyz.itclay.managersystem.util.PropertiesUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangSenmiao
 * @date 2021/1/9 16:56
 **/
public class GrandDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerApplication.class);
    private static final String ADD_STUDENT = "insert into tb_Grandes(Student_Id,Student_Name) values (?,?)";
    private static final String DELETE_STUDENT = "delete from tb_Grandes where Student_Id=?";
    private static final String UPDATE_STUDENT = "update tb_Grandes set Student_Name=? where Student_Id=?";
    private static final String FIND_NAME = "select Student_Name from tb_Grandes where Student_Id=?";
    private static final String ADD_GRAND = "update tb_Grandes set Student_C=?,Student_Java=?,Student_Network=? where Student_Id=?";
    private static final String FIND_STUDENT_RESULT = "select * from tb_Grandes";
    private static final String ADD_USER = "insert into tb_User(User_Name,Password) values (?,?)";
    private static final String USER_LOGIN = " select  * from  tb_User WHERE User_Name=? and Password=?";
    public static Connection conn;

    static {
        try {
            String driver = PropertiesUtil.drive().getProperty("DRIVER");
            String url = PropertiesUtil.drive().getProperty("URL");
            String user = PropertiesUtil.drive().getProperty("USER");
            String pwd = PropertiesUtil.drive().getProperty("PWD");
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pwd);
            LOGGER.info("数据库连接成功!");
        } catch (Exception e) {
            LOGGER.error("数据库连接失败!");
        }

    }

    /**
     * 查询学生信息，装进集合中，返回集合
     */
    public List<StudentResult> showAll() {
        List<StudentResult> listTest = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(FIND_STUDENT_RESULT);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                StudentResult sr = new StudentResult();
                sr.setSid(resultSet.getString(1));
                sr.setName(resultSet.getString(2));
                sr.setC(Integer.parseInt(resultSet.getString(3)));
                sr.setJava(Integer.parseInt(resultSet.getString(4)));
                sr.setNetwork(Integer.parseInt(resultSet.getString(5)));
                listTest.add(sr);
            }
            resultSet.close();
        } catch (SQLException throwables) {
            LOGGER.error("数据库异常！" + throwables);
        }
        return listTest;
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
                addUser(sid);
                LOGGER.info("添加学生信息成功！学生信息为:" + sid + name);
            } else {
                LOGGER.info("添加学生信息失败！学生信息为:" + sid + name);
            }
        } catch (SQLException throwables) {
            LOGGER.error("添加学生数据库异常" + throwables);
        }
    }

    /**
     *以学生的学号作为账号和密码，同步到数据库的tb_User表中
     */

    public static void addUser(String sid) {
        try {
            PreparedStatement ps = conn.prepareStatement(ADD_USER);
            ps.setString(1, sid);
            ps.setString(2, sid);
            if (ps.executeUpdate() > 0) {
                LOGGER.info("同步学生账号成功!" + sid);
            } else {
                LOGGER.info("同步学生账号失败!" + sid);
            }
        } catch (SQLException throwables) {
            LOGGER.error("同步学生账号失败!数据库异常！" + throwables);
        }
    }

    /**
     * 删除学生信息,根据学生学号
     */

    public Boolean remove(String sid) {
        boolean flag = false;
        try {
            PreparedStatement ps = conn.prepareStatement(DELETE_STUDENT);
            ps.setString(1, sid);
            if (ps.executeUpdate() > 0) {
                flag = true;
                LOGGER.info("删除学生信息成功！学生学号为:" + sid);
            } else {
                LOGGER.info("删除学生信息失败！学生学号为:" + sid);
            }
        } catch (SQLException throwables) {
            LOGGER.error("数据库删除学生异常:" + throwables);
        }
        return flag;
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
                LOGGER.info("修改数据库学生信息成功！修改的学生信息为:" + sid);
            } else {
                LOGGER.info("修改学生信息失败！" + sid);
            }
        } catch (SQLException throwables) {
            LOGGER.error("数据库修改学生异常:" + throwables);
        }

    }

    /**
     * 根据学生学号，查询学生姓名，并返回
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
            LOGGER.info("数据库异常" + throwables);
        }
        return name;
    }

    /**
     * 添加学生成绩
     */
    public Boolean addGrand(Grande grande) {
        boolean flag = false;
        try {
            PreparedStatement ps = conn.prepareStatement(ADD_GRAND);
            ps.setString(4, grande.getSid());
            ps.setString(1, String.valueOf(grande.getC()));
            ps.setString(2, String.valueOf(grande.getJava()));
            ps.setString(3, String.valueOf(grande.getNetwork()));
            if (ps.executeUpdate() > 0) {
                LOGGER.info("添加学生成绩成功！学生成绩为:" + grande.toString());
                flag = true;
            } else {
                LOGGER.info("添加学生成绩失败！");
            }
        } catch (SQLException throwables) {
            LOGGER.error("数据库异常" + throwables);
        }
        return flag;
    }
}
