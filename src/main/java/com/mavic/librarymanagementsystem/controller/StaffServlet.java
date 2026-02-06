package com.mavic.librarymanagementsystem.controller;

import com.mavic.librarymanagementsystem.dao.BookDAO;
import com.mavic.librarymanagementsystem.dao.StaffDAO;
import com.mavic.librarymanagementsystem.model.Book;
import com.mavic.librarymanagementsystem.model.Staff;
import com.mavic.librarymanagementsystem.util.DatabaseUtil;
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
        DatabaseUtil.initializeDatabase();
        staffDAO = new StaffDAO();
        bookDAO = new BookDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("manage".equals(action)) {
            int staffId = Integer.parseInt(request.getParameter("staffId"));
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            boolean add = Boolean.parseBoolean(request.getParameter("add"));
            
            Staff staff = staffDAO.getStaffById(staffId);
            Book book = bookDAO.getBookById(bookId);
            
            if (staff != null && book != null) {
                staff.manageBook(book, add);
                bookDAO.updateBookAvailability(bookId, book.isAvailable());
                request.setAttribute("message", "Book management operation completed!");
            }
        }
        
        List<Staff> staffMembers = staffDAO.getAllStaff();
        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("staffMembers", staffMembers);
        request.setAttribute("books", books);
        request.getRequestDispatcher("/staff.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            String name = request.getParameter("name");
            int id = Integer.parseInt(request.getParameter("id"));
            
            Staff newStaff = new Staff(name, id);
            staffDAO.addStaff(newStaff);
            request.setAttribute("message", "Staff member added successfully!");
        }
        
        List<Staff> staffMembers = staffDAO.getAllStaff();
        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("staffMembers", staffMembers);
        request.setAttribute("books", books);
        request.getRequestDispatcher("/staff.jsp").forward(request, response);
    }
    
    public List<Staff> getStaffMembers() {
        return staffDAO.getAllStaff();
    }
}
