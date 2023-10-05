package com.JDBC;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyJDBC {
    public static void main(String[] arg) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/przychodnia",
                    "root", "root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from pacjenci");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("loginName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
