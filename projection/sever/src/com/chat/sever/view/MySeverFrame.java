// 服务器控制界面，
// 可以完成启动服务器
// 可以关闭服务器
// 可以管理和监控用户
package com.chat.sever.view;

import com.chat.common.UserType;
import com.chat.sever.model.ManageClientThread;
import com.chat.sever.model.MyChatSever;
import com.chat.sever.model.ServerFrameHashMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;

public class MySeverFrame extends JFrame implements MouseListener, ActionListener {

    JPanel jp1;
    JPanel jp2;
    JLabel[] jbls1;
    JButton[] jbs;
    JButton jb1, jb2;
    String userName;
    Font f;
    MyChatSever myChatSever;

    public static void main(String[] args) {
        MySeverFrame mySeverFrame = new MySeverFrame();
        setFrame(mySeverFrame);
    }

    public static void setFrame(MySeverFrame m) {
        ServerFrameHashMap.addServerFrame("myserverframe", m);
    }

    public MySeverFrame() {

        f = new Font("宋体", Font.BOLD, 20);
        jp1 = new JPanel();

        jb1 = new JButton("启动服务器");
        jb1.setBorderPainted(false);
        jb1.setContentAreaFilled(false);
        jb1.setFocusPainted(false);
        jb1.setOpaque(true);
        jb1.setBackground(new Color(209, 236, 229));
        jb1.setFont(f);
        jb1.setForeground(new Color(21, 108, 92));
        jb1.setEnabled(true);

        jb2 = new JButton("关闭服务器");
        jb2.setBorderPainted(false);
        jb2.setContentAreaFilled(false);
        jb2.setFocusPainted(false);
        jb2.setOpaque(true);
        jb2.setBackground(new Color(209, 236, 229));
        jb2.setFont(f);
        jb2.setForeground(new Color(21, 108, 92));
        jb2.setEnabled(true);

        jp1.add(jb1);
        jp1.add(jb2);
        jp1.setBackground(new Color(245, 255, 250));

        jb1.addMouseListener(this);
        jb2.addActionListener(this);
        jp2 = new JPanel(new GridLayout(7, 10, 4, 4));
        jp2.setSize(100, 120);
        jp2.setOpaque(true);
        jp2.setBackground(new Color(245, 255, 250));
        //初始化7个好友
        jbls1 = new JLabel[7];
        jbs = new JButton[7];
        for (int i = 0; i < jbls1.length; i++) {
            this.userName = UserType.getId(i + 1);
            jbls1[i] = new JLabel(this.userName, new ImageIcon(String.format("image/%s.jpg", i + 1 + "")), JLabel.LEFT);
            jbls1[i].setEnabled(false);
            jbs[i] = new JButton("强制下线");
            jbs[i].setEnabled(false);
            jbs[i].setBorderPainted(false);
            jbs[i].setContentAreaFilled(false);
            jbs[i].setFocusPainted(false);
            jbs[i].setOpaque(true);
            jbs[i].setFont(f);
            jbs[i].setBackground(new Color(127, 189, 173));
//            jbs[i].addMouseListener(this);
            jbs[i].addActionListener(new MouseClickGetIndex(i));
            jp2.add(jbls1[i], "East");
            jp2.add(jbs[i], "West");
        }

        JScrollPane scrollPane = new JScrollPane(jp2);
        scrollPane.setPreferredSize(new Dimension(450, 300));
        jp1.add(scrollPane, "South");

        this.add(jp1);
        this.setSize(500, 400);
        this.setIconImage((new ImageIcon("image/severIcon.png")).getImage());
        this.setTitle("服务器");
        this.setLocationRelativeTo(null);// 窗口居中
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void UpdateUsers(String uname) {
        int num = UserType.getId(uname) - 1;
        System.out.println(num);
        jbls1[num].setEnabled(true);
        jbs[num].setEnabled(true);
    }

    public void KillUesr(int index) {
        String user = UserType.getId(index + 1);
        try {
            System.out.println(user + "被强制下线");
            Socket s = ManageClientThread.getClientThread(user);
            s.shutdownInput();
            s.shutdownOutput();
            s.close();
            s = null;
            ManageClientThread.removeClientThread(user);
            jbs[index].setEnabled(false);
            jbls1[index].setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
            if (e.getSource() == jb1) {
                jb1.setEnabled(false);
                jb2.setEnabled(true);
                System.out.println("开启服务器");
                myChatSever = new MyChatSever();
                myChatSever.start();
            } else if (e.getSource() == jb2) {
                jb1.setEnabled(true);
                jb2.setEnabled(false);
                System.out.println("关闭服务器");
                myChatSever.setRun(false);
                myChatSever = null;
            }
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

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
