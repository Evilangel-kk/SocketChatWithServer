package com.chat.sever.view;

import com.chat.sever.model.ServerFrameHashMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MouseClickGetIndex implements ActionListener {
    public int ButtonNumber;
    public MouseClickGetIndex(int x)
    {
        ButtonNumber = x;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MySeverFrame mySeverFrame=ServerFrameHashMap.getaddServerFrame("myserverframe");
        mySeverFrame.KillUesr(ButtonNumber);
    }
}
