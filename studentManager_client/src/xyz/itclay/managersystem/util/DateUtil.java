package xyz.itclay.managersystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author ZhangSenmiao
 * @date 2021/1/7 20:35
 **/
public class DateUtil {
    private DateUtil() {
    }

    public static String getAge(String birthStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDay = null;
        try {
            birthDay = sdf.parse(birthStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date now = new Date();

        long time = now.getTime() - birthDay.getTime();

        long age = time / 1000L / 60 / 60 / 24 / 365;

        return age + "";
    }


}
