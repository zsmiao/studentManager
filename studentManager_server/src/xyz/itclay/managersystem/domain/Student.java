package xyz.itclay.managersystem.domain;

import java.io.Serializable;

/**
 * 学生类（学号、姓名、年龄、生日）
 *
 * @author ZhangSenmiao
 * @date 2021/1/7 20:08
 **/
public class Student implements Serializable {
    private static final long serialVersionUID = 1;
    private String sid;
    private String name;
    private String age;
    private String birthday;

    public Student() {
    }

    public Student(String sid, String name, String age, String birthday) {
        this.sid = sid;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return sid + "," + name + "," + age + "," + birthday;
    }
}
