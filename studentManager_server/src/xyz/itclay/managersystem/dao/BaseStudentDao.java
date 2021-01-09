package xyz.itclay.managersystem.dao;

import xyz.itclay.managersystem.domain.Student;

/**
 *
 * @author ZhangSenmiao
 * @date   2021/1/7 20:56
 **/
public interface BaseStudentDao {

    public abstract boolean addStudent(Student stu) ;


    public abstract Student[] findAllStudent();


    public abstract void deleteStudent(String delId);


    public abstract void updateStudent(String updateId, Student newStu);


    public abstract int getIndex(String id);
}
