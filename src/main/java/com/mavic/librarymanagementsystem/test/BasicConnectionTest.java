package com.mavic.librarymanagementsystem.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class BasicConnectionTest {
    public static void main(String[] args) {
        System.out.println("Testing basic MySQL connection...");
        
        // Test connection without specifying database
        String basicUrl = "jdbc:mysql://localhost:3030?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "user";
        String password = "admin@123";
        
        try {
            System.out.println("Attempting connection to: " + basicUrl);
            Connection conn = DriverManager.getConnection(basicUrl, user, password);
            System.out.println("✓ Basic MySQL connection successful!");
            
            // Test MySQL version
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT VERSION()");
            if (rs.next()) {
                System.out.println("✓ MySQL Version: " + rs.getString(1));
            }
            
            // List databases
            ResultSet dbs = stmt.executeQuery("SHOW DATABASES");
            System.out.println("Available databases:");
            while (dbs.next()) {
                System.out.println("  - " + dbs.getString(1));
            }
            
            // Try to create/use librarydb
            stmt.execute("CREATE DATABASE IF NOT EXISTS librarydb");
            stmt.execute("USE librarydb");
            System.out.println("✓ Database 'librarydb' created/selected");
            
            conn.close();
            System.out.println("✓ Connection test completed successfully!");
            
        } catch (SQLException e) {
            System.err.println("✗ Connection failed:");
            System.err.println("Error: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            
            // Try alternative connection parameters
            System.out.println("\nTrying alternative connection parameters...");
            tryAlternativeConnections();
        }
    }
    
    private static void tryAlternativeConnections() {
        String[] alternativeUrls = {
            "jdbc:mysql://localhost:3030",
            "jdbc:mysql://127.0.0.1:3030",
            "jdbc:mysql://localhost:3030/librarydb?useSSL=false&serverTimezone=UTC",
            "jdbc:mysql://localhost:3030/librarydb"
        };
        
        for (String url : alternativeUrls) {
            try {
                System.out.println("Trying: " + url);
                Connection conn = DriverManager.getConnection(url, "user", "admin@123");
                System.out.println("✓ Success with: " + url);
                conn.close();
                return;
            } catch (SQLException e) {
                System.out.println("✗ Failed: " + e.getMessage());
            }
        }
    }
}
