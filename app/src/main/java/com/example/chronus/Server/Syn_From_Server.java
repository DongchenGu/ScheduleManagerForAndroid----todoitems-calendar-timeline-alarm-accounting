package com.example.chronus.Server;

import android.os.Looper;
import android.widget.Toast;

import com.example.chronus.MainActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Syn_From_Server extends  Thread{
    //private String host ="220.136.218.181";
    private int port =31249;
    private Socket socket;
    private String filePath;

    public  Syn_From_Server (String FilePath) {
        filePath = FilePath;

    }
    public void run() {

        try {
            socket = new Socket(MainActivity.host,port);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
       } catch (IOException e) {
            // TODO Auto-generated catch block
           e.printStackTrace();
        }

        if (socket==null) {
            Looper.prepare();
            Toast.makeText(MainActivity.mainActivity, "数据库下载失败，请检查网络！", Toast.LENGTH_SHORT).show();
            Looper.loop();
        }

        try {
            DataInputStream dis =new DataInputStream(socket.getInputStream());
            DataOutputStream dos =new DataOutputStream(new FileOutputStream(filePath));

            byte[] buf = new byte[1024*9];
            int length =0;

            while((length =dis.read(buf))!=-1 ) {
                dos.write(buf, 0, length);
            }
            dos.flush();
            System.out.println("文件下载结束，，，，");
            Looper.prepare();
            Toast.makeText(MainActivity.mainActivity,"数据库已从服务器同步到本地",Toast.LENGTH_SHORT).show();
            Looper.loop();
            socket.close();
            dis.close();
            dos.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    //public static void mian(String args[]) {

      //  new Syn_From_Server("C:\\Users\\Administrator\\Desktop\\123321.txt").start();
    //}

}
