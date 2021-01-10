package xyz.itclay.managersystem.service;

import xyz.itclay.managersystem.dao.GrandDao;
import xyz.itclay.managersystem.domain.Grande;

/**
 *
 * @author ZhangSenmiao
 * @date   2021/1/9 18:04
 **/
public class GrandService {
GrandDao grandDao=new GrandDao();
    public String returnName(String sid) {
        return grandDao.findName(sid);
    }

    public boolean addGrand(Grande grande) {
      return  grandDao.addGrand(grande);
    }
}
