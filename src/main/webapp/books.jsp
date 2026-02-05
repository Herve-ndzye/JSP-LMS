<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mavic.librarymanagementsystem.model.Book" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Library Management System - Books</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Merriweather:wght@400;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        :root {
            --primary: #1e40af;
            --primary-light: #3b82f6;
            --primary-dark: #1e3a8a;
            --accent: #0891b2;
            --success: #059669;
            --warning: #d97706;
            --danger: #dc2626;
            --text-primary: #0f172a;
            --text-secondary: #475569;
            --bg-light: #f8fafc;
            --bg-white: #ffffff;
            --border: #e2e8f0;
            --shadow-sm: 0 1px 2px 0 rgba(15, 23, 42, 0.05);
            --shadow-md: 0 4px 6px -1px rgba(15, 23, 42, 0.1);
            --shadow-lg: 0 10px 15px -3px rgba(15, 23, 42, 0.1);
            --shadow-xl: 0 20px 25px -5px rgba(15, 23, 42, 0.1);
        }

        html {
            scroll-behavior: smooth;
        }

        body {
            font-family: 'Inter', sans-serif;
            line-height: 1.6;
            color: var(--text-primary);
            background-color: var(--bg-light);
            overflow-x: hidden;
        }

        /* Header Styles */
        .header {
            background: linear-gradient(135deg, var(--primary-dark) 0%, var(--primary) 100%);
            color: white;
            padding: 40px 20px;
            text-align: center;
            box-shadow: var(--shadow-lg);
            position: relative;
            overflow: hidden;
        }

        .header::before {
            content: '';
            position: absolute;
            top: -50%;
            right: -10%;
            width: 400px;
            height: 400px;
            background: rgba(255, 255, 255, 0.05);
            border-radius: 50%;
        }

        .header-content {
            position: relative;
            z-index: 1;
            max-width: 800px;
            margin: 0 auto;
        }

        .header h1 {
            font-family: 'Merriweather', serif;
            font-size: 2.5rem;
            font-weight: 700;
            margin-bottom: 8px;
            letter-spacing: -0.5px;
        }

        .header p {
            font-size: 1.1rem;
            opacity: 0.95;
            font-weight: 400;
        }

        /* Navigation */
        .nav {
            background: var(--bg-white);
            padding: 16px 0;
            box-shadow: var(--shadow-sm);
            position: sticky;
            top: 0;
            z-index: 100;
            border-bottom: 1px solid var(--border);
        }

        .nav-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            display: flex;
            justify-content: center;
            gap: 32px;
            flex-wrap: wrap;
        }

        .nav a {
            text-decoration: none;
            color: var(--text-secondary);
            font-weight: 500;
            padding: 8px 16px;
            border-radius: 6px;
            transition: all 0.3s ease;
            font-size: 0.95rem;
        }

        .nav a:hover, .nav a.active {
            color: var(--primary);
            background: rgba(59, 130, 246, 0.1);
        }

        /* Container */
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }

        /* Message */
        .message {
            background: linear-gradient(135deg, #10b981 0%, #059669 100%);
            color: white;
            padding: 16px 24px;
            border-radius: 8px;
            margin: 24px 0;
            box-shadow: var(--shadow-md);
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .message::before {
            content: '✓';
            display: inline-block;
            width: 20px;
            height: 20px;
            background: rgba(255, 255, 255, 0.2);
            border-radius: 50%;
            text-align: center;
            line-height: 20px;
            font-weight: bold;
        }

        /* Form Section */
        .form-section {
            background: var(--bg-white);
            padding: 40px;
            border-radius: 16px;
            box-shadow: var(--shadow-xl);
            margin: 32px 0;
            border: 1px solid var(--border);
        }

        .form-section h3 {
            font-family: 'Merriweather', serif;
            color: var(--primary-dark);
            margin-bottom: 24px;
            font-size: 1.5rem;
            font-weight: 700;
        }

        .form-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 24px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group label {
            font-weight: 600;
            margin-bottom: 8px;
            color: var(--text-primary);
            font-size: 0.95rem;
        }

        .form-group input {
            padding: 12px 16px;
            border: 2px solid var(--border);
            border-radius: 8px;
            font-size: 0.95rem;
            transition: all 0.3s ease;
            background: var(--bg-white);
        }

        .form-group input:focus {
            outline: none;
            border-color: var(--primary-light);
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
        }

        .btn {
            padding: 12px 32px;
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-light) 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-weight: 600;
            font-size: 0.95rem;
            cursor: pointer;
            transition: all 0.3s ease;
            border: 2px solid transparent;
            letter-spacing: 0.5px;
        }

        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(30, 64, 175, 0.3);
        }

        .btn:active {
            transform: translateY(0);
        }

        .btn-secondary {
            background: linear-gradient(135deg, var(--text-secondary) 0%, #64748b 100%);
        }

        .btn-secondary:hover {
            box-shadow: 0 10px 20px rgba(71, 85, 105, 0.3);
        }

        /* Book List */
        .book-list {
            margin: 32px 0;
        }

        .book-list h3 {
            font-family: 'Merriweather', serif;
            color: var(--primary-dark);
            margin-bottom: 24px;
            font-size: 1.8rem;
            font-weight: 700;
        }

        .book-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
            gap: 24px;
        }

        .book-item {
            background: var(--bg-white);
            padding: 24px;
            border-radius: 12px;
            box-shadow: var(--shadow-md);
            border: 1px solid var(--border);
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            position: relative;
            overflow: hidden;
        }

        .book-item::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(90deg, var(--primary), var(--accent));
            transform: scaleX(0);
            transform-origin: left;
            transition: transform 0.3s ease;
        }

        .book-item:hover {
            transform: translateY(-4px);
            box-shadow: var(--shadow-xl);
            border-color: var(--primary-light);
        }

        .book-item:hover::before {
            transform: scaleX(1);
        }

        .book-item h3 {
            color: var(--primary-dark);
            margin-bottom: 12px;
            font-size: 1.25rem;
            font-weight: 600;
            font-family: 'Merriweather', serif;
        }

        .book-item p {
            color: var(--text-secondary);
            margin-bottom: 8px;
            font-size: 0.95rem;
        }

        .status-badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 0.85rem;
            font-weight: 600;
            margin-bottom: 16px;
        }

        .status-badge.available {
            background: rgba(5, 150, 105, 0.1);
            color: var(--success);
        }

        .status-badge.not-available {
            background: rgba(220, 38, 38, 0.1);
            color: var(--danger);
        }

        .book-actions {
            margin-top: 16px;
            padding-top: 16px;
            border-top: 1px solid var(--border);
        }

        .book-actions form {
            display: inline-block;
        }

        /* Empty State */
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            background: var(--bg-white);
            border-radius: 12px;
            box-shadow: var(--shadow-md);
            border: 1px solid var(--border);
        }

        .empty-state h3 {
            color: var(--text-secondary);
            margin-bottom: 12px;
            font-size: 1.25rem;
            font-weight: 600;
        }

        .empty-state p {
            color: var(--text-secondary);
            opacity: 0.8;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .header h1 {
                font-size: 2rem;
            }

            .form-section {
                padding: 24px;
            }

            .book-grid {
                grid-template-columns: 1fr;
            }

            .nav-container {
                gap: 16px;
            }
        }

        @media (max-width: 480px) {
            .header {
                padding: 24px 16px;
            }

            .header h1 {
                font-size: 1.5rem;
            }

            .container {
                padding: 0 16px;
            }

            .form-section {
                padding: 20px 16px;
            }

            .form-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="header-content">
            <h1>📚 Book Catalog</h1>
            <p>Manage and browse the complete library collection</p>
        </div>
    </div>
    
    <nav class="nav">
        <div class="nav-container">
            <a href="index.jsp">🏠 Home</a>
            <a href="books" class="active">📚 Books</a>
            <a href="students">👨‍🎓 Students</a>
            <a href="staff">👨‍💼 Staff</a>
        </div>
    </nav>

    <div class="container">
        <% if (request.getAttribute("message") != null) { %>
            <div class="message">
                <%= request.getAttribute("message") %>
            </div>
        <% } %>

        <div class="form-section">
            <h3>Add New Book</h3>
            <form action="books" method="post">
                <input type="hidden" name="action" value="add">
                <div class="form-grid">
                    <div class="form-group">
                        <label for="title">Book Title</label>
                        <input type="text" id="title" name="title" required placeholder="Enter book title">
                    </div>
                    <div class="form-group">
                        <label for="author">Author Name</label>
                        <input type="text" id="author" name="author" required placeholder="Enter author name">
                    </div>
                </div>
                <button type="submit" class="btn">➕ Add Book</button>
            </form>
        </div>

        <div class="book-list">
            <h3>Library Collection</h3>
            <% 
            List<Book> books = (List<Book>) request.getAttribute("books");
            if (books != null && !books.isEmpty()) {
            %>
                <div class="book-grid">
                    <% for (int i = 0; i < books.size(); i++) {
                        Book book = books.get(i);
                    %>
                        <div class="book-item">
                            <h3><%= book.getTitle() %></h3>
                            <p><strong>Author:</strong> <%= book.getAuthor() %></p>
                            <span class="status-badge <%= book.isAvailable() ? "available" : "not-available" %>">
                                <%= book.isAvailable() ? "✓ Available" : "✗ Not Available" %>
                            </span>
                            <div class="book-actions">
                                <form action="books" method="post">
                                    <input type="hidden" name="action" value="toggleAvailability">
                                    <input type="hidden" name="id" value="<%= i %>">
                                    <button type="submit" class="btn <%= book.isAvailable() ? "btn-secondary" : "" %>">
                                        <%= book.isAvailable() ? "📕 Mark Unavailable" : "📗 Mark Available" %>
                                    </button>
                                </form>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } else { %>
                <div class="empty-state">
                    <h3>📚 No Books Available</h3>
                    <p>Start building your library collection by adding your first book above.</p>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>
