package com.chat.sever.model;

import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class ManageClientThread {
    public static HashMap hm = new HashMap<String, Socket>();

    public static void addClientThread(String uid, Socket s) { hm.put(uid, s); }

    public static void removeClientThread(String uid) { hm.remove(uid); }

    public static Socket getClientThread(String uid) {
        return (Socket) hm.get(uid);
    }

    public static String getAllonLineUserId() {
        Iterator it = hm.keySet().iterator();
        String res = "";
        while (it.hasNext()) {
            res += it.next().toString() + " ";
        }
        return res;
    }
}
