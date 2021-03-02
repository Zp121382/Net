package com.xzy;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Invisible
 * @version 0.1
 * @date 2021/2/24 17:18
 */


public class Server {
    private static InputStream inputStream;
    private static Socket socket;
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            /**
             * 创建ServerSocket
             *
             */

            serverSocket = new ServerSocket(9999);
//            System.out.println("正在等待客户端连接...");


            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            while(true){
                // accept: 等待客户端连接   这是一个阻塞方法,返回一个代表客户端的"抽象对象"
                socket = serverSocket.accept();
                System.out.println("正在等待客户端连接...");

                /**
                 * 利用线程池技术来解决多个线程的问题
                 */

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("有客户端连接进来了"+socket.getInetAddress().getHostAddress());
                            // 获取到输入流
                            inputStream = socket.getInputStream();

                            byte[] buffer = new byte[1024];
                            int len = 0;
                            while ((len = inputStream.read(buffer)) != -1){
                                System.out.println(new String(buffer,0,len));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            // 服务器没必要主动释放资源
                            try {
                                System.out.println("资源正在释放中......");
                                inputStream.close();
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("释放资源异常");
                            }

                        }
                    }
                }
                );


                /*new Thread(){
                    @Override
                    public void run() {

                        try {
                            System.out.println("与客户端连接进来了"+socket.getInetAddress().getHostAddress());
                            // 获取到输入流
                            inputStream = socket.getInputStream();

                            byte[] buffer = new byte[1024];
                            int len = 0;
                            while ((len = inputStream.read(buffer)) != -1){
                                System.out.println(new String(buffer,0,len));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            // 服务器没必要主动释放资源
                            try {
                                System.out.println("资源马上释放");
                                inputStream.close();
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("释放资源异常");
                            }
                        }
                    }
                }.start();*/

           }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
