//服务器功能监听等待某个客户端连接服务器
package com.chat.sever.model;

import com.chat.common.*;
import com.chat.sever.db.SqlHelper;
import com.chat.sever.view.MySeverFrame;

import java.io.*;
import java.net.*;

public class MyChatSever extends Thread{
    private boolean isRun;
    private ServerSocket ss;

    public void setRun(boolean run) {
        isRun = run;
    }

    @Override
    public void run(){
        try {
            //在9999监听
            System.out.println("监听服务器中......");
            ss = new ServerSocket(9999);
            isRun=true;
            //阻塞，等待连接
            while (isRun) {
                Socket s = ss.accept();
                //接收客户端发来的信息
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                User u = (User) ois.readObject();

                Message m = new Message();
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

                //验证账号密码是否正确
                if (SqlHelper.UserCheck(u)) {
                    //返回一个成功登录的信息包
                    m.setMessType(MessageType.message_succeed);
                    oos.writeObject(m);
                    //这里单开一个线程，让线程与该客户端保持连接
                    SerConClientThread scct = new SerConClientThread(s);
                    //加入到哈希表中
                    ManageClientThread.addClientThread(u.getName(), scct.s);
                    //启动与该客户端通讯的线程
                    scct.start();
                    //并通知其他在线用户更新好友列表
                    scct.NotifyOther(u.getName());

                    Socket sworld=ss.accept();
                    SerConWorldClientThread scwct=new SerConWorldClientThread(sworld);
                    ManageWorldClientThread.addWorldClientThread(u.getName(),scwct.s);
                    scwct.start();

                    //服务器界面更新用户列表
                    MySeverFrame mySeverFrame=ServerFrameHashMap.getaddServerFrame("myserverframe");
                    mySeverFrame.UpdateUsers(u.getName());

                } else {
                    m.setMessType(MessageType.message_failed);
                    oos.writeObject(m);
                }
            }
            ss.close();
            ss=null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
