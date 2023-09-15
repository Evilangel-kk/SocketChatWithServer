package com.chat.sever.db;

import com.chat.common.User;

import java.sql.*;

public class SqlHelper {

    public static boolean UserCheck(User u) throws ClassNotFoundException, SQLException {
        boolean b = false;
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/user";
        String user = "root";
        String pwd = "123456";

        Connection connection = DriverManager.getConnection(url, user, pwd);

        if (connection == null) {
            System.out.println("连接失败");
        } else {
            System.out.println("连接成功");


            String sql = "select count(1) as 'num' from bqb_user where userId = ? and userPasswd = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,u.getUserId());
            statement.setString(2,u.getPassword());
            ResultSet rs = statement.executeQuery();
            rs.next();
            if (rs.getInt("num") == 1) {
                u.setName(u.getUserId());
                System.out.println("登陆成功");
                b = true;
            } else {
                System.out.println("登陆失败");
            }
//            connection.close();
        }
        return b;
    }
}
