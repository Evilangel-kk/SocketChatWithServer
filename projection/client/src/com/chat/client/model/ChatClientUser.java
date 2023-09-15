package com.chat.client.model;

import com.chat.common.*;

public class ChatClientUser {

    public boolean checkUser(User u){
        return new ChatClientConServer().SendLoginInfoToSever(u);
    }
}
