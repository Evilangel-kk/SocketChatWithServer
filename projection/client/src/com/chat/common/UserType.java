package com.chat.common;

public class UserType {
    public static int getId(String name){
        int i=0;
        if(name.equals("海绵宝宝")){
            i = 1;
        } else if(name.equals("派大星")){
            i = 2;
        } else if(name.equals("蟹老板")){
            i = 3;
        } else if(name.equals("章鱼哥")){
            i = 4;
        } else if(name.equals("珊迪")){
            i = 5;
        } else if(name.equals("泡芙老师")){
            i = 6;
        } else if(name.equals("小蜗")){
            i = 7;
        }
        return i;
    }
    public static String getId(int i){
        String name="";
        if(i==1){
            name="海绵宝宝";
        } else if(i==2){
            name="派大星";
        } else if(i==3){
            name="蟹老板";
        } else if(i==4){
            name="章鱼哥";
        } else if(i==5){
            name="珊迪";
        } else if(i==6){
            name="泡芙老师";
        } else if(i==7){
            name="小蜗";
        }
        return name;
    }
}
