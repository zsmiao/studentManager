package xyz.itclay.managersystem.entry;

import xyz.itclay.managersystem.controller.StudentController;
import xyz.itclay.managersystem.util.Login;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

/**
 * 客户端启动类
 *
 * @author ZhangSenmiao
 * @version 3.0
 * @date 2021/1/7 20:16
 **/
public class ClientApplication {

    public static void main(String[] args) throws IOException {
        Login.userLogin();
    }
}
