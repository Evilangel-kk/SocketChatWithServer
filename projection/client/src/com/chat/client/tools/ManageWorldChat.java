package com.chat.client.tools;

import com.chat.client.view.WorldChat;
import java.util.HashMap;

public class ManageWorldChat {
    public static HashMap hm = new HashMap<String, WorldChat>();

    public static void addWorldChat(String name, WorldChat worldChat) {
        hm.put(name, worldChat);
    }

    public static WorldChat getWorldChat(String name) {
        return (WorldChat) hm.get(name);
    }
}
