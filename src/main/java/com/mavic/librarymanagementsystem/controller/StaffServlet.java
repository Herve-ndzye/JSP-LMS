package com.mavic.librarymanagementsystem.controller;

import com.mavic.librarymanagementsystem.dao.BookDAO;
import com.mavic.librarymanagementsystem.dao.StaffDAO;
import com.mavic.librarymanagementsystem.model.Book;
import com.mavic.librarymanagementsystem.model.Staff;
import com.mavic.librarymanagementsystem.util.HibernateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "staffServlet", value = "/staff")
public class StaffServlet extends HttpServlet {
    private StaffDAO staffDAO;
    private BookDAO bookDAO;
    
    @Override
    public void init() {
        // Initialize Hibernate SessionFactory
        HibernateUtil.getSessionFactory();
        staffDAO = new StaffDAO();
        bookDAO = new BookDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Staff> staffMembers = staffDAO.getAllStaff();
            List<Book> books = bookDAO.getAllBooks();
            request.setAttribute("staffMembers", staffMembers);
            request.setAttribute("books", books);
            request.getRequestDispatcher("/staff.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading data: " + e.getMessage());
            request.getRequestDispatcher("/staff.jsp").forward(request, response);
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
                
                if (name != null && !name.trim().isEmpty() && idParam != null) {
                    int userId = Integer.parseInt(idParam);
                    Staff newStaff = new Staff(name.trim(), userId);
                    staffDAO.addStaff(newStaff);
                    request.setAttribute("message", "Staff member '" + name + "' added successfully!");
                } else {
                    request.setAttribute("error", "Name and ID are required!");
                }
            } else if ("manage".equals(action)) {
                String staffIdParam = request.getParameter("staffId");
                String bookIdParam = request.getParameter("bookId");
                String addParam = request.getParameter("add");
                
                if (staffIdParam != null && bookIdParam != null && addParam != null) {
                    List<Staff> staffMembers = staffDAO.getAllStaff();
                    List<Book> books = bookDAO.getAllBooks();
                    
                    int staffIndex = Integer.parseInt(staffIdParam);
                    int bookIndex = Integer.parseInt(bookIdParam);
                    boolean add = Boolean.parseBoolean(addParam);
                    
                    if (staffIndex >= 0 && staffIndex < staffMembers.size() &&
                        bookIndex >= 0 && bookIndex < books.size()) {
                        
                        Staff staff = staffMembers.get(staffIndex);
                        Book book = books.get(bookIndex);
                        
                        staff.manageBook(book, add);
                        bookDAO.updateBook(book);
                        
                        String operation = add ? "added to" : "removed from";
                        request.setAttribute("message", "Book '" + book.getTitle() + "' " + operation + " library by " + staff.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        
        // Reload data and forward
        List<Staff> staffMembers = staffDAO.getAllStaff();
        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("staffMembers", staffMembers);
        request.setAttribute("books", books);
        request.getRequestDispatcher("/staff.jsp").forward(request, response);
    }
    
    @Override
    public void destroy() {
        HibernateUtil.shutdown();
    }
}
