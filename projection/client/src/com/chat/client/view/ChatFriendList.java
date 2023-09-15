//好友列表
package com.chat.client.view;

import com.chat.client.tools.ManageChatChat;
import com.chat.client.tools.ManageWorldChat;
import com.chat.common.Message;
import com.chat.common.UserType;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class ChatFriendList extends JFrame implements MouseListener, ActionListener {
    private String ownerId;
    private String name;
    //第一张
    JPanel jphy1, jphy2;
    JButton jphy_jb1, jphy_jb2;
    JScrollPane jsp;
    String friendName;
    JLabel[] jbls1;

    public static void main(String[] args) {
        ChatFriendList chatClientLogin = new ChatFriendList("1", "海绵宝宝");
    }

    public void UpdateFriend(Message m) {
        String[] onLineFriend = m.getCon().split(" ");
        for (int i = 0; i < onLineFriend.length; i++) {
            onLineFriend[i] = UserType.getId(onLineFriend[i]) + "";
        }
        List<String> list = Arrays.asList(onLineFriend);
        for (int i = 0; i < jbls1.length; i++) {
            if (list.contains(i + 1 + "")) {
                jbls1[i].setEnabled(true);
            }
        }
    }

    public ChatFriendList(String ownerId, String name) {
        this.ownerId = ownerId;
        this.name = name;
        //处理第一张
        jphy1 = new JPanel(new BorderLayout());
        //假定7个好友
        jphy2 = new JPanel(new GridLayout(7, 1, 4, 4));
        jphy2.setOpaque(true);
        jphy2.setBackground(new Color(245, 255, 250));
        //初始化7个好友
        jbls1 = new JLabel[7];
        for (int i = 0; i < jbls1.length; i++) {
            this.friendName = UserType.getId(i + 1);
            jbls1[i] = new JLabel(this.friendName, new ImageIcon(String.format("image/%s.jpg", i + 1 + "")), JLabel.LEFT);
            jbls1[i].addMouseListener(this);
            jbls1[i].setEnabled(false);
            jphy2.add(jbls1[i]);
        }

        jphy_jb1 = new JButton("我的好友");
        jphy_jb1.setBorderPainted(false);
        jphy_jb1.setContentAreaFilled(false);
        jphy_jb1.setFocusPainted(false);
        jphy_jb1.setOpaque(true);
        jphy_jb1.setBackground(new Color(209, 236, 229));

        jphy_jb2 = new JButton("比奇堡");
        jphy_jb2.setBorderPainted(false);
        jphy_jb2.setContentAreaFilled(false);
        jphy_jb2.setFocusPainted(false);
        jphy_jb2.setOpaque(true);
        jphy_jb2.setBackground(new Color(127, 189, 173));
        jphy_jb2.addActionListener(this);

        jsp = new JScrollPane(jphy2);

        //对jphy1依次放入
        jphy1.add(jphy_jb2, "North");
        jphy1.add(jphy_jb1, "Center");
        jphy1.add(jsp, "South");

        this.add(jphy1);
        //在窗口显示自己的编号
        this.setIconImage((new ImageIcon("image/icon.png")).getImage());
        this.setTitle(this.name + "的好友列表");
        this.setSize(300, 535);
        this.setVisible(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel jl = (JLabel) e.getSource();
        jl.setForeground(Color.BLACK);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //相应用户双击时间，并获得编号
        if (e.getClickCount() == 2) {
            //得到编号
            String friendName = ((JLabel) e.getSource()).getText();
            ChatChat chatChat = new ChatChat(this.name, friendName);
            ManageChatChat.addChatChat(this.name + "_" + friendName, chatChat);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel jl = (JLabel) e.getSource();
        jl.setForeground(Color.BLUE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WorldChat worldChat = new WorldChat(this.name);
        ManageWorldChat.addWorldChat(this.name, worldChat);
    }
}