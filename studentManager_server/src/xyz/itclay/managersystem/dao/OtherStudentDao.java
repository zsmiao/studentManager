package xyz.itclay.managersystem.dao;

import xyz.itclay.managersystem.domain.Student;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author ZhangSenmiao
 * @date 2021/1/7 21:05
 **/
public class OtherStudentDao implements BaseStudentDao {

    private static ArrayList<Student> studentArray = new ArrayList<>();

    static {
        reload();
    }

    @Override
    public void deleteStudent(String deleteId) {
        int index = getIndex(deleteId);
        studentArray.remove(index);
        //删除学生信息的时候，根据学生的学号在数据库中删除学生的信息
        GrandDao.remove(deleteId);
    }

    @Override
    public int getIndex(String id) {
        int index = -1;
        for (int i = 0; i < studentArray.size(); i++) {
            Student student = studentArray.get(i);
            if (student != null && id != null && id.equals(student.getSid())) {
                index = i;
            }
        }
        return index;
    }

    /**
     * @param student:
     * @author ZhangSenmiao
     * @date 2021/1/8 13:36
     * @return: boolean
     */
    @Override
    public boolean addStudent(Student student) {
        studentArray.add(student);
        GrandDao.addStudentInfo(student.getSid(), student.getName());
        reSave01();
        return true;
    }

    @Override
    public Student[] findAllStudent() {
        return studentArray.toArray(new Student[studentArray.size()]);
    }


    @Override
    public void updateStudent(String updateStudentId, Student newStudent) {
        int index = getIndex(updateStudentId);
        studentArray.set(index, newStudent);
        GrandDao.update(newStudent.getSid(),newStudent.getName());
    }


    public void reSave01() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("studentManager_server\\stu.txt"));
            oos.writeObject(studentArray);
            oos.close();
        } catch (IOException e) {
            System.out.println();
        }
    }

    public static void reload() {

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("studentManager_server\\stu.txt"));
            studentArray = (ArrayList<Student>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println();
        }

    }

}
