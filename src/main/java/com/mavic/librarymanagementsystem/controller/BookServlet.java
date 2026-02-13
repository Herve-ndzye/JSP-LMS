package com.mavic.librarymanagementsystem.controller;

import com.mavic.librarymanagementsystem.dao.BookDAO;
import com.mavic.librarymanagementsystem.model.Book;
import com.mavic.librarymanagementsystem.util.HibernateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "bookServlet", value = "/books")
public class BookServlet extends HttpServlet {
    private BookDAO bookDAO;
    
    @Override
    public void init() {
        // Initialize Hibernate SessionFactory
        HibernateUtil.getSessionFactory();
        bookDAO = new BookDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Book> books = bookDAO.getAllBooks();
            request.setAttribute("books", books);
            request.getRequestDispatcher("/books.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading books: " + e.getMessage());
            request.getRequestDispatcher("/books.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if ("add".equals(action)) {
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                
                if (title != null && !title.trim().isEmpty() && 
                    author != null && !author.trim().isEmpty()) {
                    Book newBook = new Book(title.trim(), author.trim());
                    bookDAO.addBook(newBook);
                    request.setAttribute("message", "Book '" + title + "' added successfully!");
                } else {
                    request.setAttribute("error", "Title and author are required!");
                }
            } else if ("toggleAvailability".equals(action)) {
                String idParam = request.getParameter("id");
                if (idParam != null) {
                    // Get book by index from the list
                    List<Book> books = bookDAO.getAllBooks();
                    int index = Integer.parseInt(idParam);
                    
                    if (index >= 0 && index < books.size()) {
                        Book book = books.get(index);
                        book.setAvailable(!book.isAvailable());
                        bookDAO.updateBook(book);
                        request.setAttribute("message", "Book availability updated!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        
        // Reload books and forward
        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("books", books);
        request.getRequestDispatcher("/books.jsp").forward(request, response);
    }
    
    @Override
    public void destroy() {
        HibernateUtil.shutdown();
    }
}
