package com_241047031;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class database {
    private static String DB_URL;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;

    private Connection connection;

    public boolean loaddbfile(){
        Properties properties=new Properties();
        try (FileInputStream fis=new FileInputStream("/Users/lavanya/IdeaProjects/book/src/main/resources/db.properties")) {
            properties.load(fis);
            DB_URL=properties.getProperty("db.url");
            DB_USERNAME=properties.getProperty("db.username");
            DB_PASSWORD=properties.getProperty("db.password");

            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            return true;

        } catch (IOException e) {
            System.out.println("Error loading database"+e.getMessage());
        } catch (SQLException e) {
            System.out.println("error connecting database"+e.getMessage());
        }

        return false;
    }
    public Connection getConnection() {
        return connection;
    }
}
