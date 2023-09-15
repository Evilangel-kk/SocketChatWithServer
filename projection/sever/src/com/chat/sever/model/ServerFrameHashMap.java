package com.chat.sever.model;

import com.chat.sever.view.MySeverFrame;

import java.util.HashMap;

public class ServerFrameHashMap {
    public static HashMap hm = new HashMap<String,MySeverFrame>();
    public static void addServerFrame(String uid, MySeverFrame mySeverFrame) {
        hm.put(uid, mySeverFrame);
    }

    public static MySeverFrame getaddServerFrame(String uid) {
        return (MySeverFrame) hm.get(uid);
    }
}
