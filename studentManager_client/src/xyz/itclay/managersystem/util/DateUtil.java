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
        //1. 将birthStr转为日期对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDay = null;
        try {
            birthDay = sdf.parse(birthStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //2. 拿到当前日期
        Date now = new Date();

        //3. 计算当前日期和出生日期之间的毫秒值的差值
        long time = now.getTime() - birthDay.getTime();

        //4. 根据毫秒值,计算过了多少年
        long age = time / 1000L / 60 / 60 / 24 / 365;

        //5. 将计算好的年龄返回
        return age + "";
    }


}
