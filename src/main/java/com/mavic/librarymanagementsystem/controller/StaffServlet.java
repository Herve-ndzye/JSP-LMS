package com.mavic.librarymanagementsystem.controller;

import com.mavic.librarymanagementsystem.model.Book;
import com.mavic.librarymanagementsystem.model.Staff;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "staffServlet", value = "/staff")
public class StaffServlet extends HttpServlet {
    private List<Staff> staffMembers;
    private List<Book> books;
    
    @Override
    public void init() {
        staffMembers = new ArrayList<>();
        books = new ArrayList<>();
        
        // Initialize with sample staff
        staffMembers.add(new Staff("Isaac", 1));
        staffMembers.add(new Staff("Herve", 99));
        
        // Initialize with sample books
        books.add(new Book("Mathematics", "Happy David"));
        books.add(new Book("English", "Muhizi Brian"));
        books.add(new Book("Software Engineering", "Mulindwa Christian"));
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("manage".equals(action)) {
            int staffId = Integer.parseInt(request.getParameter("staffId"));
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            boolean add = Boolean.parseBoolean(request.getParameter("add"));
            
            Staff staff = staffMembers.get(staffId);
            Book book = books.get(bookId);
            
            staff.manageBook(book, add);
            request.setAttribute("message", "Book management operation completed!");
        }
        
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
            staffMembers.add(newStaff);
            request.setAttribute("message", "Staff member added successfully!");
        }
        
        request.setAttribute("staffMembers", staffMembers);
        request.setAttribute("books", books);
        request.getRequestDispatcher("/staff.jsp").forward(request, response);
    }
    
    public List<Staff> getStaffMembers() {
        return staffMembers;
    }
}
