package xyz.itclay.managersystem.factory;

import xyz.itclay.managersystem.dao.BaseStudentDao;
import xyz.itclay.managersystem.dao.OtherStudentDao;

import java.util.ResourceBundle;

/**
 *
 * @author ZhangSenmiao
 * @date   2021/1/7 21:06
 **/
public class StudentDaoFactory {
    public static Object getBean(String beanName){

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("bean");
            String value = bundle.getString(beanName);
            Class<?> clazz = Class.forName(value);
            Object o = clazz.newInstance();
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
