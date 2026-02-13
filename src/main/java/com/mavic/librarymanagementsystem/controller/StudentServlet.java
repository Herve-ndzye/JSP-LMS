package com.mavic.librarymanagementsystem.controller;

import com.mavic.librarymanagementsystem.dao.BookDAO;
import com.mavic.librarymanagementsystem.dao.StudentDAO;
import com.mavic.librarymanagementsystem.model.Book;
import com.mavic.librarymanagementsystem.model.Student;
import com.mavic.librarymanagementsystem.util.HibernateUtil;
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
        // Initialize Hibernate SessionFactory
        HibernateUtil.getSessionFactory();
        studentDAO = new StudentDAO();
        bookDAO = new BookDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Student> students = studentDAO.getAllStudents();
            List<Book> books = bookDAO.getAllBooks();
            request.setAttribute("students", students);
            request.setAttribute("books", books);
            request.getRequestDispatcher("/students.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading data: " + e.getMessage());
            request.getRequestDispatcher("/students.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if ("add".equals(action)) {
                String name = request.getParameter("name");
                String idParam = request.getParameter("id");
                String department = request.getParameter("department");
                
                if (name != null && !name.trim().isEmpty() && 
                    idParam != null && department != null && !department.trim().isEmpty()) {
                    int userId = Integer.parseInt(idParam);
                    Student newStudent = new Student(name.trim(), userId, department.trim());
                    studentDAO.addStudent(newStudent);
                    request.setAttribute("message", "Student '" + name + "' registered successfully!");
                } else {
                    request.setAttribute("error", "All fields are required!");
                }
            } else if ("borrow".equals(action)) {
                String studentIdParam = request.getParameter("studentId");
                String bookIdParam = request.getParameter("bookId");
                
                if (studentIdParam != null && bookIdParam != null) {
                    List<Student> students = studentDAO.getAllStudents();
                    List<Book> books = bookDAO.getAllBooks();
                    
                    int studentIndex = Integer.parseInt(studentIdParam);
                    int bookIndex = Integer.parseInt(bookIdParam);
                    
                    if (studentIndex >= 0 && studentIndex < students.size() &&
                        bookIndex >= 0 && bookIndex < books.size()) {
                        
                        Student student = students.get(studentIndex);
                        Book book = books.get(bookIndex);
                        
                        if (book.isAvailable()) {
                            student.borrowBook(book);
                            bookDAO.updateBook(book);
                            request.setAttribute("message", student.getName() + " borrowed '" + book.getTitle() + "'");
                        } else {
                            request.setAttribute("error", "Book is not available!");
                        }
                    }
                }
            } else if ("return".equals(action)) {
                String studentIdParam = request.getParameter("studentId");
                String bookIdParam = request.getParameter("bookId");
                
                if (studentIdParam != null && bookIdParam != null) {
                    List<Student> students = studentDAO.getAllStudents();
                    List<Book> books = bookDAO.getAllBooks();
                    
                    int studentIndex = Integer.parseInt(studentIdParam);
                    int bookIndex = Integer.parseInt(bookIdParam);
                    
                    if (studentIndex >= 0 && studentIndex < students.size() &&
                        bookIndex >= 0 && bookIndex < books.size()) {
                        
                        Student student = students.get(studentIndex);
                        Book book = books.get(bookIndex);
                        
                        student.returnBook(book);
                        bookDAO.updateBook(book);
                        request.setAttribute("message", student.getName() + " returned '" + book.getTitle() + "'");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        
        // Reload data and forward
        List<Student> students = studentDAO.getAllStudents();
        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("students", students);
        request.setAttribute("books", books);
        request.getRequestDispatcher("/students.jsp").forward(request, response);
    }
    
    @Override
    public void destroy() {
        HibernateUtil.shutdown();
    }
}
