package com.chat.client.tools;

import java.util.HashMap;

public class ManageWorldClientConServerThread {
    public static HashMap hm =new HashMap<String,WorldClientConServerThread>();
    public static void addWorldClientConServerThread(String name,WorldClientConServerThread wccst){
        hm.put(name,wccst);
    }
    public static WorldClientConServerThread getWorldClientConServerThread(String name){
        return (WorldClientConServerThread) hm.get(name);
    }
}
