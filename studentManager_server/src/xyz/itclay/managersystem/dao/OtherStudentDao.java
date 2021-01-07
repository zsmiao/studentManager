package xyz.itclay.managersystem.dao;

import xyz.itclay.managersystem.domain.Student;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author ZhangSenmiao
 * @date   2021/1/7 21:05
 **/
public class OtherStudentDao implements BaseStudentDao{
    //定义一个学生类型的数组,长度为5,未来这个数组容器可以容纳5个学生对象
    private static ArrayList<Student> stus = new ArrayList();        // Student[] stus = {stu,stu,null,null,null}

    static {
        //将本地文件中保存的数据加载进内存
        reload02();
    }

    //添加学生
    @Override
    public boolean addStudent(Student stu) {
        stus.add(stu);
        //持久化存档
        reSave02();
        return true;
    }

    //查询所有学生
    @Override
    public Student[] findAllStudent() {
        return stus.toArray(new Student[stus.size()]);
    }

    // 删除学生
    @Override
    public void deleteStudent(String delId) {
        //1. 找到这个学号在数组中对应的学生对象的索引位置
        int index = getIndex(delId);
        //2. 用null去覆盖该索引位置的元素即为删除
        stus.remove(index);
        //持久化存档
        reSave02();
    }

    //修改学生
    @Override
    public void updateStudent(String updateId, Student newStu) {
        //1. 根据要修改的学号,找到对应的索引位置
        int index = getIndex(updateId);

        //2. 将newStu去覆盖index索引位置的老学生信息即可
        stus.set(index,newStu);
        //持久化存档
        reSave02();
    }

    // 根据学号查找学生对象在数组中的索引位置
    @Override
    public int getIndex(String id) {
        //先假设要查找的学号不存在
        int index = -1;
        for (int i = 0; i < stus.size(); i++) {
            Student stu = stus.get(i);      //stu ={null, "张三","23","1999-1-11"}
            if (stu != null && id != null && id.equals(stu.getSid())) {
                index = i;
            }
        }
        return index;
    }


    //使用对象序列化流实现数据的存档
    public void reSave02()  {
        //定义一个输出流对象
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("studentManager_server\\stu.txt"));
            //1. 将list写到文件
            oos.writeObject(stus);
            //释放资源
            oos.close();
        } catch (IOException e) {
        }
    }

    //通过反序列化流加载数据
    private static void reload02() {
        // 通过IO读取stu.txt中的数据进内存,生成学生对象并装载进集合
        try {
            // 注意: 静态代码块中不容许抛出异常,有异常必须try..catch处理
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("studentManager_server\\stu.txt"));
            //2. 读取list到内存
            stus = (ArrayList<Student>) ois.readObject();

            ois.close();
        } catch (Exception e) {
        }
    }

}
