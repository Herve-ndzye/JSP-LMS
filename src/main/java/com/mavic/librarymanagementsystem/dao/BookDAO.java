package com.mavic.librarymanagementsystem.dao;

import com.mavic.librarymanagementsystem.model.Book;
import com.mavic.librarymanagementsystem.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Book book = new Book(rs.getString("title"), rs.getString("author"));
                book.setAvailable(rs.getBoolean("is_available"));
                books.add(book);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving books", e);
        }
        
        return books;
    }
    
    public Book getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book(rs.getString("title"), rs.getString("author"));
                    book.setAvailable(rs.getBoolean("is_available"));
                    return book;
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving book", e);
        }
        
        return null;
    }
    
    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, is_available) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setBoolean(3, book.isAvailable());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error adding book", e);
        }
    }
    
    public void updateBookAvailability(int id, boolean available) {
        String sql = "UPDATE books SET is_available = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBoolean(1, available);
            pstmt.setInt(2, id);
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book availability", e);
        }
    }
    
    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book", e);
        }
    }
}
