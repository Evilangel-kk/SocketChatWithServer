//管理好友列表
package com.chat.client.tools;

import com.chat.client.view.ChatFriendList;

import java.util.HashMap;

public class ManageChatFriendList {
    private static HashMap hm=new HashMap<String, ChatFriendList>();
    public static void addChatFriendList(String Id,ChatFriendList chatFriendList){
        hm.put(Id,chatFriendList);
    }
    public static ChatFriendList getChatFriendList(String Id){
        return (ChatFriendList) hm.get(Id);
    }
}
