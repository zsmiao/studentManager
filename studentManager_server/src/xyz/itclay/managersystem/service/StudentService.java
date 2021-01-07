package xyz.itclay.managersystem.service;

import xyz.itclay.managersystem.dao.BaseStudentDao;
import xyz.itclay.managersystem.domain.Student;
import xyz.itclay.managersystem.factory.StudentDaoFactory;

/**
 * @author ZhangSenmiao
 * @date 2021/1/7 20:55
 **/
public class StudentService {
    private BaseStudentDao dao = StudentDaoFactory.getStudentDao();

    //业务员中的添加功能
    public boolean addStudent(Student stu) {
        //1. 创建一个库管对象,指挥库管对象将学生对象添加到数据仓库
        //2. 将库管添加之后返回的结果回传给业务员
        return dao.addStudent(stu);
    }

    // 判断学号是否存在
    public boolean isExists(String sid) {
        //1. 拿到系统中当前最新的所有学生信息: 指挥库管把数据仓库中现有的所有数据查出来
        Student[] stus = dao.findAllStudent();      // 1.{null,null,null,null,null},2. {stu,stu,null,null,null},3.{stu,stu,stu,stu,stu}
        // 标记位: 先假设当前学号不存在: flag = false
        boolean flag = false;

        // 2. 遍历当前系统中的所有学生信息,拿到每个学生对象的学号和当前传入的sid进行匹配
        for (int i = 0; i < stus.length; i++) {
            // 只有当前索引位置的元素不为null,并且当前索引位置的学生对象的学号和传进来的学号内容相等
            if (stus[i] != null && stus[i].getSid().equals(sid)) {
                flag = true;        // 修改标记位true
                break;
            }
        }
        // 3. 将匹配的结果返回
        return flag;
    }


    public Student[] findAllStudent() {
        //1. 指挥库管dao将仓库中所有数据都拿出来
        //1.1 stus= {null,null,null,null,null}      --> 期望: stus = null
        //1.2 stus = {stu,null,null,null,null}
        //1.3 stus = {stu,stu,stu,stu,stu,stu}
        Student[] stus = dao.findAllStudent();

        //标记位: 先假设stus中一个学生都没有
        boolean flag = false;

        for (int i = 0; i < stus.length; i++) {
            if (stus[i] != null) {
                flag = true;
            }
        }

        // 根据flag的值来判断stus中有没有学生
        if (flag) {
            // 有学生,返回整个dao传过来的数组: 有地址的数组
            return stus;
        } else {
            return null;    // 没学生,直接返回null
        }

    }

}

