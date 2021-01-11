package xyz.itclay.managersystem.service;

import xyz.itclay.managersystem.dao.BaseStudentDao;
import xyz.itclay.managersystem.domain.Student;
import xyz.itclay.managersystem.factory.StudentDaoFactory;

/**
 * @author ZhangSenmiao
 * @date 2021/1/7 20:55
 **/
public class StudentService {
    private BaseStudentDao dao = (BaseStudentDao) StudentDaoFactory.getBean("OtherStudentDao");

    /**
     * 添加学生
     */
    public boolean addStudent(Student stu) {
        return dao.addStudent(stu);
    }

    /**
     * 判断学生是否存在
     */
    public boolean isExists(String sid) {
        //1. 拿到系统中当前最新的所有学生信息: 指挥库管把数据仓库中现有的所有数据查出来
        Student[] stus = dao.findAllStudent();
        // 标记位: 先假设当前学号不存在: flag = false
        boolean flag = false;

        // 2. 遍历当前系统中的所有学生信息,拿到每个学生对象的学号和当前传入的sid进行匹配
        for (int i = 0; i < stus.length; i++) {
            // 只有当前索引位置的元素不为null,并且当前索引位置的学生对象的学号和传进来的学号内容相等
            if (stus[i] != null && stus[i].getSid().equals(sid)) {
                flag = true;
                break;
            }
        }
        // 3. 将匹配的结果返回
        return flag;
    }

    /**
     * 查看学生
     */
    public Student[] findAllStudent() {
        Student[] stus = dao.findAllStudent();
        boolean flag = false;
        for (int i = 0; i < stus.length; i++) {
            if (stus[i] != null) {
                flag = true;
            }
        }
        if (flag) {
            return stus;
        } else {
            return null;
        }

    }

    /**
     * 删除学生
     */
    public void deleteStudent(String deleteId) {
        dao.deleteStudent(deleteId);
    }

    /**
     * 修改学生
     */
    public void updateStudent(String updateStudentId, Student newStudent) {
        dao.updateStudent(updateStudentId, newStudent);
    }
}

