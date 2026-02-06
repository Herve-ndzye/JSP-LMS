package com.mavic.librarymanagementsystem.controller;

import com.mavic.librarymanagementsystem.dao.BookDAO;
import com.mavic.librarymanagementsystem.dao.StudentDAO;
import com.mavic.librarymanagementsystem.model.Book;
import com.mavic.librarymanagementsystem.model.Student;
import com.mavic.librarymanagementsystem.util.DatabaseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "studentServlet", value = "/students")
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO;
    private BookDAO bookDAO;
    
    @Override
    public void init() {
        DatabaseUtil.initializeDatabase();
        studentDAO = new StudentDAO();
        bookDAO = new BookDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("borrow".equals(action)) {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            
            Student student = studentDAO.getStudentById(studentId);
            Book book = bookDAO.getBookById(bookId);
            
            if (student != null && book != null) {
                student.borrowBook(book);
                bookDAO.updateBookAvailability(bookId, book.isAvailable());
                request.setAttribute("message", "Book borrowing process completed!");
            }
        } else if ("return".equals(action)) {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            
            Student student = studentDAO.getStudentById(studentId);
            Book book = bookDAO.getBookById(bookId);
            
            if (student != null && book != null) {
                student.returnBook(book);
                bookDAO.updateBookAvailability(bookId, book.isAvailable());
                request.setAttribute("message", "Book returned successfully!");
            }
        }
        
        List<Student> students = studentDAO.getAllStudents();
        List<Book> books = bookDAO.getAllBooks();
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
            studentDAO.addStudent(newStudent);
            request.setAttribute("message", "Student added successfully!");
        }
        
        List<Student> students = studentDAO.getAllStudents();
        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("students", students);
        request.setAttribute("books", books);
        request.getRequestDispatcher("/students.jsp").forward(request, response);
    }
    
    public List<Student> getStudents() {
        return studentDAO.getAllStudents();
    }
}
