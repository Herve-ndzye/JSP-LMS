package com.mavic.librarymanagementsystem.test;

import com.mavic.librarymanagementsystem.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseTest {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        System.out.println("Database URL: " + "jdbc:mysql://localhost:3030/librarydb");
        System.out.println("Username: " + "user");
        System.out.println("Password: " + "admin@123");
        
        try {
            // Test basic connection
            Connection conn = DatabaseUtil.getConnection();
            System.out.println("✓ Database connection successful!");
            
            // Test database access
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT VERSION()");
            if (rs.next()) {
                System.out.println("✓ MySQL Version: " + rs.getString(1));
            }
            
            // Test database and tables
            stmt.execute("USE librarydb");
            System.out.println("✓ Database 'librarydb' accessible");
            
            // Check tables
            ResultSet tables = stmt.executeQuery("SHOW TABLES");
            System.out.println("Tables in database:");
            while (tables.next()) {
                System.out.println("  - " + tables.getString(1));
            }
            
            conn.close();
            System.out.println("✓ Database test completed successfully!");
            
        } catch (SQLException e) {
            System.err.println("✗ Database connection failed:");
            System.err.println("Error: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
        }
    }
}
