package com.mavic.librarymanagementsystem.dao;

import com.mavic.librarymanagementsystem.model.Staff;
import com.mavic.librarymanagementsystem.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    
    public List<Staff> getAllStaff() {
        List<Staff> staffMembers = new ArrayList<>();
        String sql = "SELECT * FROM staff";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Staff staff = new Staff(rs.getString("name"), rs.getInt("id"));
                staffMembers.add(staff);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving staff members", e);
        }
        
        return staffMembers;
    }
    
    public Staff getStaffById(int id) {
        String sql = "SELECT * FROM staff WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Staff(rs.getString("name"), rs.getInt("id"));
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving staff member", e);
        }
        
        return null;
    }
    
    public void addStaff(Staff staff) {
        String sql = "INSERT INTO staff (id, name) VALUES (?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, staff.getId());
            pstmt.setString(2, staff.getName());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error adding staff member", e);
        }
    }
    
    public void updateStaff(Staff staff) {
        String sql = "UPDATE staff SET name = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, staff.getName());
            pstmt.setInt(2, staff.getId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error updating staff member", e);
        }
    }
    
    public void deleteStaff(int id) {
        String sql = "DELETE FROM staff WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting staff member", e);
        }
    }
}
