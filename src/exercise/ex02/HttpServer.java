package exercise.ex02;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) {
        //接收http请求
        new HttpServer().await();
    }

    private volatile boolean flag = false;

    private void await() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        Request request = null;
        try {
            serverSocket = new ServerSocket(8080,1, InetAddress.getByName("127.0.0.1"));
            while (!flag){
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                //包装Request
                request = new Request(inputStream);
                request.parse();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
