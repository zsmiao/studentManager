package xyz.itclay.managersystem.factory;

import xyz.itclay.managersystem.dao.BaseStudentDao;
import xyz.itclay.managersystem.dao.OtherStudentDao;

/**
 *
 * @author ZhangSenmiao
 * @date   2021/1/7 21:06
 **/
public class StudentDaoFactory {
    // 当方法的返回值类型是一个类名的时候,方法体中需要返回的就是该类的对象!
    // 当方法的返回值类型是一个接口的时候,方法体中需要返回的就是该接口的实现类对象!
    // 当方法的返回值类型是一个父类型的时候,方法体中需要返回的就是该父类型的子类对象!
    public static BaseStudentDao getStudentDao(){
//        return new StudentDao();
        return new OtherStudentDao();
    }
}
