package dev.slavin;

import dev.slavin.util.ConnectionUtility;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {

    public static void main(String[] args) {
        try {
            Connection c = ConnectionUtility.getConnection();
            System.out.println(c.getMetaData().getDriverName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}