package com.mavic.librarymanagementsystem.controller;

import com.mavic.librarymanagementsystem.model.Book;
import com.mavic.librarymanagementsystem.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "studentServlet", value = "/students")
public class StudentServlet extends HttpServlet {
    private List<Student> students;
    private List<Book> books;
    
    @Override
    public void init() {
        students = new ArrayList<>();
        books = new ArrayList<>();
        
        // Initialize with sample students
        students.add(new Student("Tresor", 123, "SideJobs"));
        students.add(new Student("Herve", 100, "Robotics"));
        students.add(new Student("Aloys", 101, "Backend"));
        
        // Initialize with sample books
        books.add(new Book("Mathematics", "Happy David"));
        books.add(new Book("English", "Muhizi Brian"));
        books.add(new Book("Software Engineering", "Mulindwa Christian"));
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("borrow".equals(action)) {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            
            Student student = students.get(studentId);
            Book book = books.get(bookId);
            
            student.borrowBook(book);
            request.setAttribute("message", "Book borrowing process completed!");
        } else if ("return".equals(action)) {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            
            Student student = students.get(studentId);
            Book book = books.get(bookId);
            
            student.returnBook(book);
            request.setAttribute("message", "Book returned successfully!");
        }
        
        request.setAttribute("students", students);
        request.setAttribute("books", books);
        request.getRequestDispatcher("/students.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            String name = request.getParameter("name");
            int id = Integer.parseInt(request.getParameter("id"));
            String department = request.getParameter("department");
            
            Student newStudent = new Student(name, id, department);
            students.add(newStudent);
            request.setAttribute("message", "Student added successfully!");
        }
        
        request.setAttribute("students", students);
        request.setAttribute("books", books);
        request.getRequestDispatcher("/students.jsp").forward(request, response);
    }
    
    public List<Student> getStudents() {
        return students;
    }
}
