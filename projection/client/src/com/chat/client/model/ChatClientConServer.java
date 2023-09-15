//这是客户端连接服务器的后台
package com.chat.client.model;

import com.chat.client.tools.ClientConServerThread;
import com.chat.client.tools.ManageClientConServerThread;
import com.chat.common.*;
import java.io.*;
import java.net.*;

public class ChatClientConServer {
    public Socket s;
    //发送第一次请求
    public boolean SendLoginInfoToSever(User u){
        boolean b=false;
        try {
            s = new Socket("127.0.0.1", 9999);
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            u.setName(u.getUserId());
            oos.writeObject(u);

            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            Message ms=(Message) ois.readObject();
            if(ms.getMessType().equals(MessageType.message_succeed)){
                //就创建一个该qq号和服务器端保持通讯连接得线程
                ClientConServerThread ccst=new ClientConServerThread(s);
                //启动该通讯线程
                ccst.start();
                //加入哈希表中
                ManageClientConServerThread.addClientConServerThread(u.getName(), ccst);
                b=true;
            } else{
//                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return b;
    }
}
