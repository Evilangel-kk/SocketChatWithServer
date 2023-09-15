//服务器与某个客户的通讯线程
package com.chat.sever.model;

import com.chat.common.Message;
import com.chat.common.MessageType;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class SerConClientThread extends Thread {
    Socket s;

    public SerConClientThread(Socket s) {
        //把服务器与该客户端的连接赋给socket
        this.s = s;
    }

    //通知其他用户
    public void NotifyOther(String iam) {
        //得到所有在线人的线程
        HashMap hm = ManageClientThread.hm;
        Iterator it = hm.keySet().iterator();
        while (it.hasNext()) {
            Message m = new Message();
            m.setCon(iam);
            m.setMessType(MessageType.message_ret_onLineFriend);
            String onLineUserId = it.next().toString();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getClientThread(onLineUserId).getOutputStream());
                m.setGetter(onLineUserId);
                oos.writeObject(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        while (true) {
            if (s.isInputShutdown()) {
                break;
            }
            //该线程可以接受客户端的信息
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message m = (Message) ois.readObject();
                //对客户端取得的消息进行类型判断并做相应处理
                if (m.getMessType().equals(MessageType.message_common)) {
                    System.out.println(m.getSender() + "对" + m.getGetter() + "说:" + m.getCon());
                    //完成转发
                    //取得接收人的通讯线程
                    Socket sc = ManageClientThread.getClientThread(m.getGetter());
                    ObjectOutputStream oos = new ObjectOutputStream(sc.getOutputStream());
                    oos.writeObject(m);
                } else if (m.getMessType().equals(MessageType.message_get_onLineFriend)) {
                    //把在服务器的好友返回给该客户端
                    String res = ManageClientThread.getAllonLineUserId();
                    Message mess = new Message();
                    mess.setMessType(MessageType.message_ret_onLineFriend);
                    mess.setCon(res);
                    mess.setGetter(m.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                    oos.writeObject(mess);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
