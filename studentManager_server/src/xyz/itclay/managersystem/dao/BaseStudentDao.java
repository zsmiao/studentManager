package xyz.itclay.managersystem.dao;

import xyz.itclay.managersystem.domain.Student;

/**
 *
 * @author ZhangSenmiao
 * @date   2021/1/7 20:56
 **/
public interface BaseStudentDao {
    //添加学生
    public abstract boolean addStudent(Student stu) ;

    //查询所有学生
    public abstract Student[] findAllStudent();

    // 删除学生
    public abstract void deleteStudent(String delId);

    //修改学生
    public abstract void updateStudent(String updateId, Student newStu);

    // 根据学号查找学生对象在数组中的索引位置
    public abstract int getIndex(String id);
}
