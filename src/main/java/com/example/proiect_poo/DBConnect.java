//package com.example.proiect_poo;
//
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBConnect {
//
//    private static Connection conn;
//    private static String url = "jdbc:mysql://localhost:3306/fit_track";
//    private static String user = "root";
//    private static String pass = "Seneca2004";
////  am folosit jdbc nu jpa, fara chestiile de aici cum e in lab 10
//
//    public static Connection connect() throws SQLException {
//
//
//        conn = DriverManager.getConnection(url,user,pass);
//        return conn;
//    }
//    public static Connection getConnection() throws SQLException, ClassNotFoundException{
//        if(conn !=null && !conn.isClosed())
//            return conn;
//        connect();
//        return conn;
//
//    }
//}
//
