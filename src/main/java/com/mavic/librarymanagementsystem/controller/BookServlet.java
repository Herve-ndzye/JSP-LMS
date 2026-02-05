package com.mavic.librarymanagementsystem.controller;

import com.mavic.librarymanagementsystem.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "bookServlet", value = "/books")
public class BookServlet extends HttpServlet {
    private List<Book> books;
    
    @Override
    public void init() {
        books = new ArrayList<>();
        // Initialize with some sample books
        books.add(new Book("Mathematics", "Happy David"));
        books.add(new Book("English", "Muhizi Brian"));
        books.add(new Book("Software Engineering", "Mulindwa Christian"));
        books.add(new Book("A Level Mathematics", "Niyonkuru Darius"));
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("view".equals(action)) {
            int bookId = Integer.parseInt(request.getParameter("id"));
            Book book = books.get(bookId);
            request.setAttribute("book", book);
            request.getRequestDispatcher("/book-details.jsp").forward(request, response);
        } else {
            // Show all books
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
            books.add(newBook);
            request.setAttribute("message", "Book added successfully!");
        } else if ("toggleAvailability".equals(action)) {
            int bookId = Integer.parseInt(request.getParameter("id"));
            Book book = books.get(bookId);
            book.setAvailable(!book.isAvailable());
            request.setAttribute("message", "Book availability updated!");
        }
        
        request.setAttribute("books", books);
        request.getRequestDispatcher("/books.jsp").forward(request, response);
    }
    
    public List<Book> getBooks() {
        return books;
    }
}
