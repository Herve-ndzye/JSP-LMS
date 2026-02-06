 package com.mavic.librarymanagementsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3030/librarydb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "admin@123";
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL driver", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Create database if not exists (MySQL specific)
            //noinspection SqlDialectInspection
            stmt.execute("CREATE DATABASE IF NOT EXISTS librarydb");
            //noinspection SqlDialectInspection
            stmt.execute("USE librarydb");
            
            // Create books table (MySQL syntax)
            //noinspection SqlDialectInspection
            stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "title VARCHAR(255) NOT NULL, " +
                        "author VARCHAR(255) NOT NULL, " +
                        "is_available BOOLEAN DEFAULT TRUE)");
            
            // Create students table (MySQL syntax)
            //noinspection SqlDialectInspection
            stmt.execute("CREATE TABLE IF NOT EXISTS students (" +
                        "id INT PRIMARY KEY, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "department VARCHAR(255) NOT NULL)");
            
            // Create staff table (MySQL syntax)
            //noinspection SqlDialectInspection
            stmt.execute("CREATE TABLE IF NOT EXISTS staff (" +
                        "id INT PRIMARY KEY, " +
                        "name VARCHAR(255) NOT NULL)");
            
            // Insert sample data if tables are empty
            insertSampleData(stmt);
            
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
    
    private static void insertSampleData(Statement stmt) throws SQLException {
        try {
            // Check if books table is empty
            //noinspection SqlDialectInspection
            boolean booksEmpty = stmt.executeQuery("SELECT COUNT(*) FROM books").next() && 
                               stmt.executeQuery("SELECT COUNT(*) FROM books").getInt(1) == 0;
            
            if (booksEmpty) {
                //noinspection SqlDialectInspection
                stmt.execute("INSERT INTO books (title, author, is_available) VALUES " +
                            "('Mathematics', 'Happy David', TRUE), " +
                            "('English', 'Muhizi Brian', TRUE), " +
                            "('Software Engineering', 'Mulindwa Christian', TRUE), " +
                            "('A Level Mathematics', 'Niyonkuru Darius', TRUE)");
            }
            
            // Check if students table is empty
            //noinspection SqlDialectInspection
            boolean studentsEmpty = stmt.executeQuery("SELECT COUNT(*) FROM students").next() && 
                                   stmt.executeQuery("SELECT COUNT(*) FROM students").getInt(1) == 0;
            
            if (studentsEmpty) {
                //noinspection SqlDialectInspection
                stmt.execute("INSERT INTO students (id, name, department) VALUES " +
                            "(123, 'Tresor', 'SideJobs'), " +
                            "(100, 'Herve', 'Robotics'), " +
                            "(101, 'Aloys', 'Backend')");
            }
            
            // Check if staff table is empty
            //noinspection SqlDialectInspection
            boolean staffEmpty = stmt.executeQuery("SELECT COUNT(*) FROM staff").next() && 
                               stmt.executeQuery("SELECT COUNT(*) FROM staff").getInt(1) == 0;
            
            if (staffEmpty) {
                //noinspection SqlDialectInspection
                stmt.execute("INSERT INTO staff (id, name) VALUES " +
                            "(1, 'Isaac'), " +
                            "(99, 'Herve')");
            }
            
        } catch (SQLException e) {
            // Ignore errors if data already exists
            System.out.println("Sample data may already exist: " + e.getMessage());
        }
    }
}
