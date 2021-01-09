package xyz.itclay.managersystem.service;

import xyz.itclay.managersystem.dao.GrandDao;

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
}
