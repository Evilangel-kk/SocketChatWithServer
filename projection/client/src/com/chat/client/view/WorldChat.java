package com.chat.client.view;

import com.chat.client.tools.ManageClientConServerThread;
import com.chat.client.tools.ManageWorldClientConServerThread;
import com.chat.common.Message;
import com.chat.common.MessageType;
import com.chat.common.UserType;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.util.Date;

public class WorldChat extends JFrame implements ActionListener {
    Font f;
    Font text;
    JScrollPane jsp;
    JTextField jtf;
    JTextPane jtp;
    JButton jb;
    //    JPanel jPanel;
    JPanel jp;
    String ownerId;
    StyledDocument doc;
//    int BorderLeftRight;
//    int BorderTopBottom;

    public WorldChat(String ownerId) {
        this.ownerId = ownerId;
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
//        jPanel = new JPanel();
//        jPanel.setBackground(new Color(245, 255, 250));

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
//        this.add(jPanel, "Center");
        this.add(jp, "South");
        this.setSize(700, 500);
        this.setIconImage((new ImageIcon("image/icon.png")).getImage());
        this.setTitle("比奇堡：" + this.ownerId + "的窗口");
        this.setVisible(true);
        Welcome();
    }

    //进入世界自动欢迎
    public void Welcome() {
        Message m = new Message();
        m.setSender(this.ownerId);
        m.setCon("我来了");
        m.setMessType(MessageType.message_common);
        try {
            this.Write(this.ownerId, "我来了", 2);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        //发送给服务器
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageWorldClientConServerThread.getWorldClientConServerThread(ownerId).getSocket().getOutputStream());
            oos.writeObject(m);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //写一个方法，让它显示消息
    public void ShowMessage(Message m, int i) throws BadLocationException {
        String info = m.getCon();
        System.out.println("info: " + info);
        this.Write(m.getSender(), info, i);
    }

    public void Write(String person, String text/*文本内容*/, int textAlign/*对齐方式*/) throws BadLocationException {
//        Border border0 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
//        String imageName = "image/" + UserType.getId(person) + ".jpg";
//        System.out.println(imageName);
//        JLabel jl;
//        System.out.println("textAlign:" + textAlign);
//        System.out.println("Sender:" + person);
//        System.out.println("Con:" + text);
//        JPanel jpline=new JPanel();
//        if (textAlign == 0) {
//            System.out.println(0);
//            jl = new JLabel(text, new ImageIcon(imageName), JLabel.LEFT);
//            jl.setBackground(new Color(170, 180, 199));
//            jl.setBorder(border0);
//
//            jl.setFont(this.text);
//            jl.setForeground(new Color(21, 108, 92));
//            jl.setLayout(new FlowLayout(FlowLayout.LEFT));
//            jpline.add(jl, "East");
//        } else if (textAlign == 2) {
//            System.out.println(2);
//            jl = new JLabel(text, new ImageIcon(imageName), JLabel.RIGHT);
//            jl.setBackground(new Color(170, 180, 199));
//            jl.setBorder(border0);
//
//            jl.setFont(this.text);
//            jl.setForeground(new Color(21, 108, 92));
//            jl.setLayout(new FlowLayout(FlowLayout.RIGHT));
//            jpline.add(jl, "West");
//        }
//        jPanel.add(jpline,"South");
        if (textAlign == 0) {
            text = person + ": " + text + "\r\n";
        } else {
            text = text + " :" + person + "\r\n";
        }
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setAlignment(set, textAlign);//设置对齐方式
        doc.setParagraphAttributes(
                jtp.getText().length(), doc.getLength() - jtp.getText().length(), set, false);
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
                m.setGetter("world");
                m.setCon(jtf.getText());
                m.setSendTime(new Date().toString());
                //发送给服务器
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(
                            ManageWorldClientConServerThread.getWorldClientConServerThread(ownerId).getSocket().getOutputStream());
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
