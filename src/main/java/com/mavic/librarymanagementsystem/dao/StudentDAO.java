package com.mavic.librarymanagementsystem.dao;

import com.mavic.librarymanagementsystem.model.Student;
import com.mavic.librarymanagementsystem.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Student student = new Student(rs.getString("name"), rs.getInt("id"), rs.getString("department"));
                students.add(student);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving students", e);
        }
        
        return students;
    }
    
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(rs.getString("name"), rs.getInt("id"), rs.getString("department"));
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving student", e);
        }
        
        return null;
    }
    
    public void addStudent(Student student) {
        String sql = "INSERT INTO students (id, name, department) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getDepartment());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error adding student", e);
        }
    }
    
    public void updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, department = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getDepartment());
            pstmt.setInt(3, student.getId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error updating student", e);
        }
    }
    
    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting student", e);
        }
    }
}
