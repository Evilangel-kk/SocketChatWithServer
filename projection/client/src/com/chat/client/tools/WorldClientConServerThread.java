package com.chat.client.tools;

import com.chat.client.view.ChatChat;
import com.chat.client.view.ChatFriendList;
import com.chat.client.view.WorldChat;
import com.chat.common.Message;
import com.chat.common.MessageType;

import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class WorldClientConServerThread extends Thread {
    private Socket s;

    public WorldClientConServerThread(Socket s) {
        this.s = s;
    }

    public Socket getSocket() {
        return this.s;
    }

    public void run() {
        while (true) {
            //不停的读取从服务器发来的消息
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message m = (Message) ois.readObject();
                if (m.getMessType().equals(MessageType.message_common)) {
                    //把从服务器获得的消息，显示到聊天界面
                    HashMap hm = ManageWorldChat.hm;
                    Iterator it = hm.keySet().iterator();
                    while (it.hasNext()) {
                        String getter = it.next().toString();
                        if (!getter.equals(m.getSender())) {
                            WorldChat worldChat = ManageWorldChat.getWorldChat(getter);
                            worldChat.Write(m.getSender(), m.getCon(), 0);
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException | BadLocationException e) {
                e.printStackTrace();
            }
        }
    }
}
