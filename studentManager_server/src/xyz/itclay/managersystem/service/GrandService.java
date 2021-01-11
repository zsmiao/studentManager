package xyz.itclay.managersystem.service;

import xyz.itclay.managersystem.dao.BaseStudentDao;
import xyz.itclay.managersystem.dao.GrandDao;
import xyz.itclay.managersystem.domain.Grande;
import xyz.itclay.managersystem.domain.StudentResult;
import xyz.itclay.managersystem.factory.StudentDaoFactory;

import java.util.List;

/**
 * @author ZhangSenmiao
 * @date 2021/1/9 18:04
 **/
public class GrandService {
    GrandDao grandDao = new GrandDao();
    private BaseStudentDao dao = (BaseStudentDao) StudentDaoFactory.getBean("OtherStudentDao");

    public void changePassword(String sid, String password) {
        grandDao.changePassword(sid,password);

    }

    public String returnName(String sid) {
        return grandDao.findName(sid);
    }

    public boolean addGrand(Grande grande) {
        return grandDao.addGrand(grande);
    }

    public boolean deleteGrand(String sid) {
       dao.deleteStudent(sid);
       return true;
    }

    public List<StudentResult> showAll() {
        return grandDao.showAll();
    }

    public boolean isStudent(String sid, String password) {
       return grandDao.isStudent(sid,password);
    }

    public StudentResult conditionQuery(String sid) {
        return grandDao.conditionQuery(sid);
    }
}
