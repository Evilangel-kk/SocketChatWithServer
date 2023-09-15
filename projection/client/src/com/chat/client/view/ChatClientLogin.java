//功能：登陆界面
package com.chat.client.view;

import com.chat.client.model.*;
import com.chat.client.tools.*;
import com.chat.common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ChatClientLogin extends JFrame implements ActionListener {
    Font f;
    //定义上部组件
    JLabel jbl1;
    //定义中部组件
    JPanel jp2;
    JLabel jp2_jbl1, jp2_jbl2, jp2_jbl3, jp2_jbl4;
    JTextField jp2_jtf;
    JPasswordField jp2_jpf;
    JCheckBox jp2_jcb;
    JLabel jp2_null;
    //定义下部组件
    JPanel jp1;
    JButton jp1_jb1, jp1_jb2, jp1_jb3;

    public static void main(String[] args) {
        ChatClientLogin chatcLientLogin = new ChatClientLogin();
    }

    public ChatClientLogin() {
        f = new Font("宋体", Font.BOLD, 20);
        //处理上部
        jbl1 = new JLabel(new ImageIcon("image/head.jpg"));
        jbl1.setOpaque(true);
        jbl1.setBackground(new Color(209, 236, 229));
        //处理下部
        jp1 = new JPanel();
        jp1.setOpaque(true);
        jp1.setBackground(new Color(209, 236, 229));
        jp1_jb1 = new JButton(new ImageIcon("image/login.jpg"));
        //相应用户点击登录
        jp1_jb1.addActionListener(this);
        jp1_jb2 = new JButton(new ImageIcon("image/pass.jpg"));
        jp1_jb3 = new JButton(new ImageIcon("image/signup.jpg"));
        jp1_jb1.setBorderPainted(false);
        jp1_jb1.setContentAreaFilled(false);
        jp1_jb1.setFocusPainted(false);

        jp1_jb2.setBorderPainted(false);
        jp1_jb2.setContentAreaFilled(false);
        jp1_jb2.setFocusPainted(false);

        jp1_jb3.setBorderPainted(false);
        jp1_jb3.setContentAreaFilled(false);
        jp1_jb3.setFocusPainted(false);

        jp1.add(jp1_jb1);
        jp1.add(jp1_jb2);
        jp1.add(jp1_jb3);

        //处理中部
        //中部有三个JPanel，由一个选项卡窗口管理

        jp2 = new JPanel(new GridLayout(3, 3));
        jp2.setOpaque(true);
        jp2.setBackground(new Color(209, 236, 229));
        jp2_jbl1 = new JLabel("账号:", JLabel.RIGHT);
        jp2_jbl1.setFont(f);
        jp2_jbl2 = new JLabel("密码:", JLabel.RIGHT);
        jp2_jbl2.setFont(f);
        jp2_jbl3 = new JLabel("忘记密码", JLabel.CENTER);
        jp2_jbl3.setFont(f);
        jp2_jbl3.setForeground(Color.BLUE);
        jp2_jbl4 = new JLabel("清空", JLabel.CENTER);
        jp2_jbl4.setFont(f);
        jp2_jtf = new JTextField();
        jp2_jpf = new JPasswordField();
        jp2_jcb = new JCheckBox("记住密码");
        jp2_jcb.setFont(f);
        jp2_jcb.setHorizontalAlignment(SwingConstants.CENTER);
        jp2_jcb.setOpaque(true);
        jp2_jcb.setBackground(new Color(209, 236, 229));
        jp2_null = new JLabel("");
        //依次加入
        jp2.add(jp2_jbl1);
        jp2.add(jp2_jtf);
        jp2.add(jp2_jbl4);
        jp2.add(jp2_jbl2);
        jp2.add(jp2_jpf);
        jp2.add(jp2_jbl3);
        jp2.add(jp2_null);
        jp2.add(jp2_jcb);

        this.add(jbl1, "North");
        this.add(jp2, "Center");
        this.add(jp1, "South");
        this.setSize(700, 400);
        this.setIconImage((new ImageIcon("image/icon.png")).getImage());
        this.setTitle("登录中......");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jp1_jb1) {
            ChatClientUser chatClientUser = new ChatClientUser();
            User u = new User();
            u.setUserId(jp2_jtf.getText().trim());
            u.setPassword(new String(jp2_jpf.getPassword()));
            System.out.println("ID:" + u.getUserId() + "\tPassword:" + u.getPassword());

            if (chatClientUser.checkUser(u)) {
                new WorldChatClientConServer(u);

                try {
                    ChatFriendList chatFriendList = new ChatFriendList(u.getUserId(), u.getName());
                    ManageChatFriendList.addChatFriendList(u.getName(), chatFriendList);
                    //发送请求在线好友
                    ObjectOutputStream oos = new ObjectOutputStream(
                            ManageClientConServerThread.getClientConServerThread(
                                    u.getName()).getSocket().getOutputStream());
                    Message m = new Message();
                    m.setMessType(MessageType.message_get_onLineFriend);
                    m.setSender(u.getName());
                    oos.writeObject(m);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                //关闭登陆界面
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "用户名密码错误");
            }
        }
    }
}


