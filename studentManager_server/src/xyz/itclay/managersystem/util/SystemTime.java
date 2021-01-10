package xyz.itclay.managersystem.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ZhangSenmiao
 * @date   2021/1/9 0:44
 **/
public class SystemTime {
    public static void nowSystemTime(){
        Date d = new Date();
        SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.print(sbf.format(d));
    }
}

