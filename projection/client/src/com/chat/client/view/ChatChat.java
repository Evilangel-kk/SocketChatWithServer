//聊天界面
//客户端要处于读取状态，做成一个线程
package com.chat.client.view;

import com.chat.client.tools.ManageClientConServerThread;
import com.chat.common.Message;
import com.chat.common.MessageType;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ChatChat extends JFrame implements ActionListener {
    Font f;
    Font text;
    JScrollPane jsp;
    JTextField jtf;
    JTextPane jtp;
    JButton jb;
    JPanel jp;
    String ownerId;
    String friendId;
    StyledDocument doc;

    public static void main(String[] args) {
        ChatChat chatChat = new ChatChat("海绵宝宝", "派大星");
    }

    public ChatChat(String ownerId, String friend) {
        this.ownerId = ownerId;
        this.friendId = friend;
        f = new Font("宋体", Font.BOLD, 15);
        text = new Font("宋体", Font.BOLD, 20);

        //消息条
        jtp = new JTextPane();
        jtp.setFont(text);
        jtp.setForeground(new Color(21, 108, 92));
        jsp = new JScrollPane(jtp);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jtp.setCaretPosition(jtp.getDocument().getLength());
        jtp.setOpaque(true);
        jtp.setBackground(new Color(245, 255, 250));
        doc = jtp.getStyledDocument();
        jtp.select(doc.getLength(), doc.getLength());


        //输入框
        jtf = new JTextField(50);
        jb = new JButton("发送");
        jb.addActionListener(this);
        jb.setBorderPainted(false);
        jb.setContentAreaFilled(false);
        jb.setFocusPainted(false);
        jb.setOpaque(true);
        jb.setBackground(new Color(209, 236, 229));
        jb.setFont(f);
        jp = new JPanel();
        jp.add(jtf);
        jp.add(jb);

        this.add(jsp, "Center");
        this.add(jp, "South");
        this.setSize(600, 400);
        this.setIconImage((new ImageIcon("image/icon.png")).getImage());
        this.setTitle(ownerId + " 正在和 " + friend + " 聊天...");
        this.setVisible(true);
    }

    //写一个方法，让它显示消息
    public void ShowMessage(Message m, int i) throws BadLocationException {
        String info;
        if (i == 0) {
            info = m.getSender() + ": " + m.getCon() + "\r\n";
        } else {
            info = m.getCon() + " :" + m.getSender() + "\r\n";
        }

        this.Write(info, i);
    }

    public void Write(String text/*文本内容*/, int textAlign/*对齐方式*/) throws BadLocationException {
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setAlignment(set, textAlign);//设置对齐方式
        doc.setParagraphAttributes(jtp.getText().length(), doc.getLength() - jtp.getText().length(), set, false);
        doc.insertString(doc.getLength(), text, set);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb) {
            //用户点击发送
            if (jtf.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "发送内容不能为空");
            } else {
                Message m = new Message();
                m.setMessType(MessageType.message_common);
                m.setSender(this.ownerId);
                m.setGetter(this.friendId);
                m.setCon(jtf.getText());
                m.setSendTime(new Date().toString());
                //发送给服务器
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(
                            ManageClientConServerThread.getClientConServerThread(ownerId).getSocket().getOutputStream());
                    oos.writeObject(m);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                jtf.setText("");
                try {
                    this.ShowMessage(m, 2);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}