//管理客服端与服务器保持通讯
package com.chat.client.tools;

import java.util.HashMap;

public class ManageClientConServerThread {
    private static HashMap hm =new HashMap<String,ClientConServerThread>();
    //把创建好的通讯线程放入到hm
    public static void addClientConServerThread(String Id,ClientConServerThread ccst){
        hm.put(Id,ccst);
    }
    //通过Id取得线程
    public static ClientConServerThread getClientConServerThread(String Id){
        return (ClientConServerThread) hm.get(Id);
    }
}
