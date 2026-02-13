package com.mavic.librarymanagementsystem.util;

import com.mavic.librarymanagementsystem.dao.BookDAO;
import com.mavic.librarymanagementsystem.dao.StaffDAO;
import com.mavic.librarymanagementsystem.dao.StudentDAO;
import com.mavic.librarymanagementsystem.model.Book;
import com.mavic.librarymanagementsystem.model.Staff;
import com.mavic.librarymanagementsystem.model.Student;

/**
 * Utility class to initialize sample data in the database
 */
public class DataInitializer {
    
    public static void initializeSampleData() {
        BookDAO bookDAO = new BookDAO();
        StudentDAO studentDAO = new StudentDAO();
        StaffDAO staffDAO = new StaffDAO();
        
        try {
            // Check if data already exists
            try {
                if (bookDAO.getAllBooks().isEmpty()) {
                    // Add sample books
                    bookDAO.addBook(new Book("Mathematics", "Happy David"));
                    bookDAO.addBook(new Book("English", "Muhizi Brian"));
                    bookDAO.addBook(new Book("Software Engineering", "Mulindwa Christian"));
                    bookDAO.addBook(new Book("A Level Mathematics", "Niyonkuru Darius"));
                    System.out.println("Sample books added successfully!");
                }
            } catch (Exception e) {
                System.err.println("Error checking/adding books: " + e.getMessage());
            }
            
            try {
                if (studentDAO.getAllStudents().isEmpty()) {
                    // Add sample students
                    studentDAO.addStudent(new Student("Tresor", 123, "SideJobs"));
                    studentDAO.addStudent(new Student("Herve", 100, "Robotics"));
                    studentDAO.addStudent(new Student("Aloys", 101, "Backend"));
                    System.out.println("Sample students added successfully!");
                }
            } catch (Exception e) {
                System.err.println("Error checking/adding students: " + e.getMessage());
            }
            
            try {
                if (staffDAO.getAllStaff().isEmpty()) {
                    // Add sample staff
                    staffDAO.addStaff(new Staff("Isaac", 1));
                    staffDAO.addStaff(new Staff("Herve", 99));
                    System.out.println("Sample staff added successfully!");
                }
            } catch (Exception e) {
                System.err.println("Error checking/adding staff: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("Error initializing sample data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
