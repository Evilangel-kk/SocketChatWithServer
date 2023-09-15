//用户信息类
package com.chat.common;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 11213423535L;

    private String userId;
    private String password;
    private String name;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(this.userId.equals("1")){
            this.name="海绵宝宝";
        } else if(this.userId.equals("2")){
            this.name="派大星";
        }else if(this.userId.equals("3")) {
            this.name = "蟹老板";
        }else if(this.userId.equals("4")) {
            this.name = "章鱼哥";
        }else if(this.userId.equals("5")) {
            this.name = "珊迪";
        }else if(this.userId.equals("6")) {
            this.name = "泡芙老师";
        }else if(this.userId.equals("7")) {
            this.name = "小蜗";
        }
    }
}
