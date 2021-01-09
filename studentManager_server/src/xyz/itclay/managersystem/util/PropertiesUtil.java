package xyz.itclay.managersystem.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 * @author ZhangSenmiao
 * @date   2021/1/9 15:22
 **/
public class PropertiesUtil {
    public static Properties drive() throws Exception{
        File file=new File("E:\\IdeaProject\\studentmanager-version-3.0\\studentManager_server\\database.properties");
        FileInputStream fileInputStream=new FileInputStream(file);
        Properties properties=new Properties();
        properties.load(fileInputStream);
        return properties;
    }
}
