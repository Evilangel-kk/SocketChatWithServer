//客户端和服务器保持通讯的线程
package com.chat.client.tools;

import com.chat.client.view.ChatChat;
import com.chat.client.view.ChatFriendList;
import com.chat.common.Message;
import com.chat.common.MessageType;

import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConServerThread extends Thread {
    private Socket s;
    private boolean isConnet;

    public ClientConServerThread(Socket s) {
        this.s = s;
        isConnet=true;
    }

    public void setConnet(boolean connet){
        this.isConnet=connet;
    }

    public Socket getSocket() {
        return this.s;
    }

    public void run() {
        while (isConnet) {
            if(s.isInputShutdown()){
                break;
            }
            //不停的读取从服务器发来的消息
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message m = (Message) ois.readObject();
                if(m.getMessType().equals(MessageType.message_common)){
                    //把从服务器获得的消息，显示到聊天界面
                    ChatChat chatChat = ManageChatChat.getChatChat(m.getGetter() + "_" + m.getSender());
                    chatChat.ShowMessage(m,0);
                } else if(m.getMessType().equals(MessageType.message_ret_onLineFriend)){
                    String con=m.getCon();
                    String friends[]=con.split("");
                    //修改好友列表
                    ChatFriendList chatFriendList=ManageChatFriendList.getChatFriendList(m.getGetter());
                    //更新在线好友
                    if(chatFriendList!=null){
                        chatFriendList.UpdateFriend(m);
                    }
                }
            } catch (IOException | ClassNotFoundException | BadLocationException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
