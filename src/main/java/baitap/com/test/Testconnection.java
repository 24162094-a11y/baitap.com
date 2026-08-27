package baitap.com.test;


import java.sql.Connection;

import baitap.com.connection.DBConnection;

public class Testconnection {

    public static void main(String[] args) {

        try {

            Connection conn =
                    new DBConnection().getConnection();

            if (conn != null) {
                System.out.println(
                    "KET NOI MYSQL THANH CONG!"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}