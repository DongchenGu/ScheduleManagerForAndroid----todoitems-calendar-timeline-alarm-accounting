package com.example.chronus.Server;

import android.os.Looper;
import android.widget.Toast;

import com.example.chronus.MainActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Syn_To_Server  extends Thread{

    private  Socket socket;
    private String filePath;

    public Syn_To_Server( String FilePath) {
        filePath = FilePath;

    }

        public  void run() {

            try {
                socket = new Socket(MainActivity.host, MainActivity.port);
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (socket ==null) {
                Looper.prepare();
                Toast.makeText(MainActivity.mainActivity, "无法连接到服务器，请检查网络！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
                try {
                    File file = new File(filePath);
                    System.out.println("文件大小：" + file.length() + "kb");
                    DataInputStream dis = new DataInputStream(new FileInputStream(filePath));
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    byte[] buf = new byte[1024 * 9];
                    int len = 0;
                    while ((len = dis.read(buf)) != -1) {
                        dos.write(buf, 0, len);

                    }
                    dos.flush();
                    //下面这一行非常重要，如果没有这一行，通过tcp上传数据会发生消息阻塞，也就是客户端
                    //将数据返送完毕后，服务器端通过DataInputStream的read方法无法判断是否结束，因此
                      // 服务器会一直等待，直到程序退出，线程被强制中止，服务器才能确认受到数据
                    // 调用Socket.shutdownOutput()后，禁用此套接字的输出流，对于 TCP 套接字，任何以前写入的数据都将被发送，
                    // 并且后跟 TCP 的正常连接终止序列（即-1），之后，从另一端TCP套接字的输入流中读取数据时，
                    // 如果到达输入流末尾而不再有数据可用，则返回 -1
                    socket.shutdownOutput();

                    System.out.println("文件上传结束，，，，");
                    Looper.prepare();
                    Toast.makeText(MainActivity.mainActivity,"数据库上传成功",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    socket.close();
                    dis.close();
                    dos.close();
                    
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


}