package com.chat.client.model;

import com.chat.client.tools.ClientConServerThread;
import com.chat.client.tools.ManageClientConServerThread;
import com.chat.client.tools.ManageWorldClientConServerThread;
import com.chat.client.tools.WorldClientConServerThread;
import com.chat.common.Message;
import com.chat.common.MessageType;
import com.chat.common.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class WorldChatClientConServer {
    public Socket s;

    public WorldChatClientConServer(User u) {
        try {
            s = new Socket("127.0.0.1", 9999);
            //就创建一个该qq号和服务器端保持通讯连接得线程
            WorldClientConServerThread wccst = new WorldClientConServerThread(s);
            //启动该通讯线程
            wccst.start();
            //加入哈希表中
            ManageWorldClientConServerThread.addWorldClientConServerThread(u.getName(), wccst);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
