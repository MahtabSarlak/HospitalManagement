package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {
    Connection conn=null;
    public static Connection connectdb()
    {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","ms","myjava123");
            return conn;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}