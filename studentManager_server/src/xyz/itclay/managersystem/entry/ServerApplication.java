package xyz.itclay.managersystem.entry;

import xyz.itclay.managersystem.thread.StudentRunnable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务端启动类
 *
 * @author ZhangSenmiao
 * @date 2021/1/7 20:43
 **/
public class ServerApplication {
    public static void main(String[] args) throws IOException {

        ExecutorService service = Executors.newFixedThreadPool(15);
        ServerSocket serverSocket = new ServerSocket(9998);
        while (true) {
            Socket socket = serverSocket.accept();
            service.submit(new StudentRunnable(socket));
        }
    }
}
