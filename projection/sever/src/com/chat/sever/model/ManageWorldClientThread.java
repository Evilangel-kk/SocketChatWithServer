package com.chat.sever.model;

import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class ManageWorldClientThread {
    public static HashMap hm = new HashMap<String, Socket>();

    public static void addWorldClientThread(String uid, Socket s) { hm.put(uid, s); }

    public static Socket getWorldClientThread(String uid) {
        return (Socket) hm.get(uid);
    }

}
