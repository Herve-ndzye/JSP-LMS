package com.mavic.librarymanagementsystem.controller;

import com.mavic.librarymanagementsystem.dao.BookDAO;
import com.mavic.librarymanagementsystem.model.Book;
import com.mavic.librarymanagementsystem.util.DatabaseUtil;
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
        DatabaseUtil.initializeDatabase();
        bookDAO = new BookDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("view".equals(action)) {
            int bookId = Integer.parseInt(request.getParameter("id"));
            Book book = bookDAO.getBookById(bookId);
            request.setAttribute("book", book);
            request.getRequestDispatcher("/book-details.jsp").forward(request, response);
        } else {
            // Show all books
            List<Book> books = bookDAO.getAllBooks();
            request.setAttribute("books", books);
            request.getRequestDispatcher("/books.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            Book newBook = new Book(title, author);
            bookDAO.addBook(newBook);
            request.setAttribute("message", "Book added successfully!");
        } else if ("toggleAvailability".equals(action)) {
            int bookId = Integer.parseInt(request.getParameter("id"));
            Book book = bookDAO.getBookById(bookId);
            if (book != null) {
                bookDAO.updateBookAvailability(bookId, !book.isAvailable());
                request.setAttribute("message", "Book availability updated!");
            }
        }
        
        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("books", books);
        request.getRequestDispatcher("/books.jsp").forward(request, response);
    }
    
    public List<Book> getBooks() {
        return bookDAO.getAllBooks();
    }
}
