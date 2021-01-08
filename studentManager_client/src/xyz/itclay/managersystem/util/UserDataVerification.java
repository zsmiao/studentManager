package xyz.itclay.managersystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 用户数据校验类：
 * 对于用户输入的非法数据做安全校验
 * 姓名最小长度
 *
 * @author ZhangSenmiao
 * @date 2021/1/8 20:50
 **/
public class UserDataVerification {
    boolean flag = false;

    /**
     * 姓名的长度校验
     */
    public boolean nameIsLicit(String input) {
        boolean matches = input.matches("^[\u4E00-\u9FA5]{2,4}$");
        if (matches) {
            flag = true;
        }
        return flag;
    }

    /**
     * 学号长度校验
     */
    public boolean idIsLicit(String input) {
        boolean matches = input.matches("^[a-z\\d]{6,8}");
        if (matches) {
            flag = true;
        }
        return flag;
    }

    /**
     * 校验用户输入的生日时间是否大于当前系统时间
     */

    public boolean timeIsLicit(String input) {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long inputTime = 0;
        try {
            inputTime = simpleDateFormat.parse(input).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(inputTime);
        if (inputTime < currentTime) {
            flag = true;
        }
        return flag;
    }
}
