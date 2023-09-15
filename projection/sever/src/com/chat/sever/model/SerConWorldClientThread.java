package com.chat.sever.model;

import com.chat.common.Message;
import com.chat.common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class SerConWorldClientThread extends Thread {
    Socket s;

    public SerConWorldClientThread(Socket s) {
        this.s = s;
    }

    public void run() {
        while (true) {
            //该线程可以接受客户端的信息
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message m = (Message) ois.readObject();
                System.out.println("收到消息了————————");
                //对客户端取得的消息进行类型判断并做相应处理
                if (m.getMessType().equals(MessageType.message_common)) {
                    //完成转发
                    //取得接收人的通讯线程
                    HashMap hm=ManageWorldClientThread.hm;
                    Iterator it =hm.keySet().iterator();
                    while(it.hasNext()){
                        String user=it.next().toString();
                        if(!user.equals(m.getSender())){
                            Socket sc = ManageWorldClientThread.getWorldClientThread(user);
                            ObjectOutputStream oos = new ObjectOutputStream(sc.getOutputStream());
                            oos.writeObject(m);
                            System.out.println("消息又发走了！！！！！");
                        }

                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
