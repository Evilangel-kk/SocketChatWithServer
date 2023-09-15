//管理用户聊天界面
package com.chat.client.tools;

import com.chat.client.view.ChatChat;
import java.util.HashMap;

public class ManageChatChat {
    private static HashMap hm = new HashMap<String, ChatChat>();

    public static void addChatChat(String login_and_friend, ChatChat chatChat) {
        hm.put(login_and_friend, chatChat);
    }

    public static ChatChat getChatChat(String login_and_friend) {
        return (ChatChat) hm.get(login_and_friend);
    }
}
