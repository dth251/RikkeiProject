package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionDB {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/module2_rikkei";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Ndt2501><";

    public static Connection getConnection() {
        // khai bao Driver
        try{
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void main(String[] args) {
//        Connection conn = getConnection();
//        if (conn != null) {
//            System.out.println("Kết nối thành công!");
//        } else {
//            System.out.println("Kết nối thất bại!");
//        }
//    }
}

