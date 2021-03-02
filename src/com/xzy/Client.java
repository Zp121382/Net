package com.xzy;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Invisible
 * @version 0.1
 * @date 2021/2/24 10:47
 */
public class Client {
    private static Socket socket;
    private static OutputStream outputStream;

    public static void main(String[] args) throws UnknownHostException {
        try {
            /**
             *  创建Socket对象, 内部帮我们做了好多事
             *  1)创建Socket对象
             *  2)帮我们的客户端绑定了一个没有被占用的随机的端口(每一都是随机的)
             *  3)连接我们传递的IP地址对应的机器上的端口
             *
             */

            socket = new Socket("127.0.0.1",9999);
            // 具体实现原理
            /*socket = new Socket();
            socket.bind(new InetSocketAddress(8888));
            socket.connect(new InetSocketAddress("127.0.0.1",8080));*/

            System.out.println("连接服务器成功了");
            outputStream = socket.getOutputStream();
            outputStream.write("hello中".getBytes());


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("连接服务器出现异常");
        } finally {
            try {
                outputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("客户端关闭连接异常");

            }
        }
    }
}
