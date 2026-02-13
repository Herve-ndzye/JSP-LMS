<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mavic.librarymanagementsystem.model.Student" %>
<%@ page import="com.mavic.librarymanagementsystem.model.Book" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Students - LibraryHub</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&family=Space+Grotesk:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        :root {
            --primary: #6366f1;
            --primary-dark: #4f46e5;
            --primary-light: #818cf8;
            --secondary: #ec4899;
            --accent: #14b8a6;
            --success: #10b981;
            --warning: #f59e0b;
            --danger: #ef4444;
            
            --dark: #0f172a;
            --text: #334155;
            --text-light: #64748b;
            
            --bg: #f8fafc;
            --bg-card: #ffffff;
            --border: #e2e8f0;
        }

        body {
            font-family: 'Poppins', sans-serif;
            line-height: 1.7;
            color: var(--text);
            background: var(--bg);
            overflow-x: hidden;
        }

        body::before {
            content: '';
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            height: 100vh;
            background: 
                radial-gradient(circle at 20% 50%, rgba(99, 102, 241, 0.05) 0%, transparent 50%),
                radial-gradient(circle at 80% 80%, rgba(236, 72, 153, 0.05) 0%, transparent 50%);
            pointer-events: none;
            z-index: -1;
        }

        /* Navbar */
        .navbar {
            background: rgba(255, 255, 255, 0.9);
            backdrop-filter: blur(20px);
            border-bottom: 1px solid var(--border);
            padding: 1rem 0;
            position: sticky;
            top: 0;
            z-index: 1000;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.03);
        }

        .nav-container {
            max-width: 1400px;
            margin: 0 auto;
            padding: 0 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-family: 'Space Grotesk', sans-serif;
            font-size: 1.75rem;
            font-weight: 700;
            background: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            text-decoration: none;
        }

        .logo-icon {
            width: 40px;
            height: 40px;
            background: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%);
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
        }

        .nav-links {
            display: flex;
            gap: 2rem;
            align-items: center;
        }

        .nav-links a {
            text-decoration: none;
            color: var(--text);
            font-weight: 500;
            font-size: 0.95rem;
            transition: color 0.3s ease;
            position: relative;
        }

        .nav-links a.active {
            color: var(--primary);
        }

        .nav-links a::after {
            content: '';
            position: absolute;
            bottom: -5px;
            left: 0;
            width: 0;
            height: 2px;
            background: var(--primary);
            transition: width 0.3s ease;
        }

        .nav-links a.active::after {
            width: 100%;
        }

        .nav-links a:hover {
            color: var(--primary);
        }

        .nav-links a:hover::after {
            width: 100%;
        }

        /* Page Header */
        .page-header {
            padding: 3rem 2rem 2rem;
            max-width: 1400px;
            margin: 0 auto;
        }

        .page-title {
            font-family: 'Space Grotesk', sans-serif;
            font-size: 2.5rem;
            font-weight: 700;
            color: var(--dark);
            margin-bottom: 0.5rem;
        }

        .page-description {
            color: var(--text-light);
            font-size: 1.125rem;
        }

        /* Container */
        .container {
            max-width: 1400px;
            margin: 0 auto;
            padding: 0 2rem 4rem;
        }

        /* Alert Message */
        .alert {
            background: linear-gradient(135deg, var(--success) 0%, #059669 100%);
            color: white;
            padding: 1rem 1.5rem;
            border-radius: 12px;
            margin-bottom: 2rem;
            display: flex;
            align-items: center;
            gap: 1rem;
            box-shadow: 0 10px 30px rgba(16, 185, 129, 0.3);
            animation: slideDown 0.5s ease-out;
        }

        @keyframes slideDown {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .alert-icon {
            width: 40px;
            height: 40px;
            background: rgba(255, 255, 255, 0.2);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.25rem;
            flex-shrink: 0;
        }

        /* Form Card */
        .form-card {
            background: white;
            padding: 2.5rem;
            border-radius: 24px;
            border: 1px solid var(--border);
            margin-bottom: 3rem;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.05);
        }

        .form-header {
            margin-bottom: 2rem;
        }

        .form-title {
            font-size: 1.5rem;
            font-weight: 700;
            color: var(--dark);
            margin-bottom: 0.5rem;
        }

        .form-subtitle {
            color: var(--text-light);
            font-size: 0.95rem;
        }

        .form-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-label {
            font-weight: 600;
            margin-bottom: 0.5rem;
            color: var(--dark);
            font-size: 0.95rem;
        }

        .form-input, .form-select {
            padding: 0.875rem 1.25rem;
            border: 2px solid var(--border);
            border-radius: 12px;
            font-size: 0.95rem;
            font-family: 'Poppins', sans-serif;
            transition: all 0.3s ease;
            background: white;
        }

        .form-input:hover, .form-select:hover {
            border-color: var(--primary-light);
        }

        .form-input:focus, .form-select:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.1);
        }

        .btn {
            padding: 0.875rem 2rem;
            border: none;
            border-radius: 12px;
            font-weight: 600;
            font-size: 0.95rem;
            cursor: pointer;
            transition: all 0.3s ease;
            font-family: 'Poppins', sans-serif;
            position: relative;
            overflow: hidden;
            display: inline-block;
            margin-right: 0.5rem;
            margin-bottom: 0.5rem;
        }

        .btn-primary {
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-dark) 100%);
            color: white;
            box-shadow: 0 10px 30px rgba(99, 102, 241, 0.3);
        }

        .btn-primary::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
            transition: left 0.5s ease;
        }

        .btn-primary:hover::before {
            left: 100%;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 15px 40px rgba(99, 102, 241, 0.4);
        }

        .btn-success {
            background: linear-gradient(135deg, var(--success) 0%, #059669 100%);
            color: white;
            box-shadow: 0 10px 30px rgba(16, 185, 129, 0.3);
        }

        .btn-success:hover {
            transform: translateY(-2px);
            box-shadow: 0 15px 40px rgba(16, 185, 129, 0.4);
        }

        .btn-success:disabled {
            opacity: 0.5;
            cursor: not-allowed;
            transform: none;
        }

        .btn-warning {
            background: linear-gradient(135deg, var(--warning) 0%, #d97706 100%);
            color: white;
            box-shadow: 0 10px 30px rgba(245, 158, 11, 0.3);
        }

        .btn-warning:hover {
            transform: translateY(-2px);
            box-shadow: 0 15px 40px rgba(245, 158, 11, 0.4);
        }

        /* Content Layout */
        .content-grid {
            display: grid;
            grid-template-columns: 1fr 2fr;
            gap: 2rem;
            margin-top: 2rem;
        }

        /* Section */
        .section-title {
            font-size: 1.75rem;
            font-weight: 700;
            color: var(--dark);
            margin-bottom: 1.5rem;
        }

        /* Student Cards */
        .students-list {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }

        .student-card {
            background: white;
            border-radius: 16px;
            border: 1px solid var(--border);
            padding: 1.5rem;
            transition: all 0.3s ease;
        }

        .student-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
            border-color: var(--primary-light);
        }

        .student-name {
            font-size: 1.125rem;
            font-weight: 700;
            color: var(--dark);
            margin-bottom: 0.5rem;
        }

        .student-info {
            color: var(--text-light);
            font-size: 0.95rem;
            margin-bottom: 0.25rem;
        }

        /* Book Cards */
        .books-list {
            display: flex;
            flex-direction: column;
            gap: 1.5rem;
        }

        .book-card {
            background: white;
            border-radius: 20px;
            border: 1px solid var(--border);
            overflow: hidden;
            transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
            position: relative;
        }

        .book-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(90deg, var(--primary), var(--secondary));
            transform: scaleX(0);
            transition: transform 0.4s ease;
        }

        .book-card:hover::before {
            transform: scaleX(1);
        }

        .book-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.12);
            border-color: var(--primary-light);
        }

        .book-header {
            padding: 1.5rem;
            background: linear-gradient(135deg, rgba(99, 102, 241, 0.05) 0%, rgba(236, 72, 153, 0.05) 100%);
        }

        .book-title {
            font-size: 1.125rem;
            font-weight: 700;
            color: var(--dark);
            margin-bottom: 0.5rem;
        }

        .book-author {
            color: var(--text-light);
            font-size: 0.95rem;
        }

        .book-body {
            padding: 1.5rem;
        }

        .book-status {
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            padding: 0.5rem 1rem;
            border-radius: 50px;
            font-size: 0.875rem;
            font-weight: 600;
            margin-bottom: 1rem;
        }

        .book-status.available {
            background: linear-gradient(135deg, rgba(16, 185, 129, 0.1) 0%, rgba(16, 185, 129, 0.15) 100%);
            color: var(--success);
            border: 2px solid rgba(16, 185, 129, 0.3);
        }

        .book-status.unavailable {
            background: linear-gradient(135deg, rgba(239, 68, 68, 0.1) 0%, rgba(239, 68, 68, 0.15) 100%);
            color: var(--danger);
            border: 2px solid rgba(239, 68, 68, 0.3);
        }

        .book-actions {
            padding-top: 1rem;
            border-top: 1px solid var(--border);
            display: flex;
            flex-wrap: wrap;
            gap: 0.5rem;
        }

        .book-actions form {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            flex-wrap: wrap;
        }

        /* Empty State */
        .empty-state {
            text-align: center;
            padding: 4rem 2rem;
            background: white;
            border-radius: 24px;
            border: 2px dashed var(--border);
        }

        .empty-icon {
            width: 80px;
            height: 80px;
            margin: 0 auto 1.5rem;
            background: linear-gradient(135deg, rgba(99, 102, 241, 0.1) 0%, rgba(236, 72, 153, 0.1) 100%);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2.5rem;
        }

        .empty-title {
            font-size: 1.25rem;
            font-weight: 700;
            color: var(--dark);
            margin-bottom: 0.5rem;
        }

        .empty-description {
            color: var(--text-light);
            font-size: 0.95rem;
        }

        /* Responsive */
        @media (max-width: 968px) {
            .content-grid {
                grid-template-columns: 1fr;
            }
        }

        @media (max-width: 768px) {
            .page-title {
                font-size: 2rem;
            }

            .form-grid {
                grid-template-columns: 1fr;
            }

            .nav-links {
                display: none;
            }
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar">
        <div class="nav-container">
            <a href="index.jsp" class="logo">
                <div class="logo-icon">📚</div>
                LibraryHub
            </a>
            <div class="nav-links">
                <a href="index.jsp">Home</a>
                <a href="books">Books</a>
                <a href="students" class="active">Students</a>
                <a href="staff">Staff</a>
            </div>
        </div>
    </nav>

    <!-- Page Header -->
    <div class="page-header">
        <h1 class="page-title">👨‍🎓 Student Portal</h1>
        <p class="page-description">Manage student accounts and book borrowing operations</p>
    </div>

    <!-- Main Container -->
    <div class="container">
        <!-- Alert Message -->
        <% if (request.getAttribute("message") != null) { %>
            <div class="alert">
                <div class="alert-icon">✓</div>
                <div><%= request.getAttribute("message") %></div>
            </div>
        <% } %>

        <!-- Add Student Form -->
        <div class="form-card">
            <div class="form-header">
                <h2 class="form-title">Register New Student</h2>
                <p class="form-subtitle">Add a new student to the library system</p>
            </div>
            <form action="students" method="post">
                <input type="hidden" name="action" value="add">
                <div class="form-grid">
                    <div class="form-group">
                        <label class="form-label" for="name">Student Name</label>
                        <input type="text" id="name" name="name" class="form-input" required placeholder="Enter student name">
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="id">Student ID</label>
                        <input type="number" id="id" name="id" class="form-input" required placeholder="Enter student ID">
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="department">Department</label>
                        <input type="text" id="department" name="department" class="form-input" required placeholder="Enter department">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">➕ Register Student</button>
            </form>
        </div>

        <!-- Content Grid -->
        <div class="content-grid">
            <!-- Students Section -->
            <div>
                <h2 class="section-title">Registered Students</h2>
                <% 
                List<Student> students = (List<Student>) request.getAttribute("students");
                if (students != null && !students.isEmpty()) {
                %>
                    <div class="students-list">
                        <% for (Student student : students) { %>
                            <div class="student-card">
                                <div class="student-name"><%= student.getName() %></div>
                                <div class="student-info"><strong>ID:</strong> <%= student.getUserId() %></div>
                                <div class="student-info"><strong>Department:</strong> <%= student.getDepartment() %></div>
                            </div>
                        <% } %>
                    </div>
                <% } else { %>
                    <div class="empty-state">
                        <div class="empty-icon">👨‍🎓</div>
                        <h3 class="empty-title">No Students Yet</h3>
                        <p class="empty-description">Register your first student to get started</p>
                    </div>
                <% } %>
            </div>

            <!-- Books Section -->
            <div>
                <h2 class="section-title">Book Operations</h2>
                <% 
                List<Book> books = (List<Book>) request.getAttribute("books");
                if (books != null && !books.isEmpty()) {
                %>
                    <div class="books-list">
                        <% for (int i = 0; i < books.size(); i++) {
                            Book book = books.get(i);
                        %>
                            <div class="book-card">
                                <div class="book-header">
                                    <div class="book-title"><%= book.getTitle() %></div>
                                    <div class="book-author">✍️ <%= book.getAuthor() %></div>
                                </div>
                                <div class="book-body">
                                    <div class="book-status <%= book.isAvailable() ? "available" : "unavailable" %>">
                                        <span><%= book.isAvailable() ? "✓" : "✗" %></span>
                                        <span><%= book.isAvailable() ? "Available" : "Not Available" %></span>
                                    </div>
                                    <% if (students != null && !students.isEmpty()) { %>
                                        <div class="book-actions">
                                            <form action="students" method="post">
                                                <input type="hidden" name="action" value="borrow">
                                                <input type="hidden" name="bookId" value="<%= i %>">
                                                <select name="studentId" class="form-select" required style="width: 200px;">
                                                    <% for (int j = 0; j < students.size(); j++) { %>
                                                        <option value="<%= j %>"><%= students.get(j).getName() %></option>
                                                    <% } %>
                                                </select>
                                                <button type="submit" class="btn btn-success" <%= !book.isAvailable() ? "disabled" : "" %>>📖 Borrow</button>
                                            </form>
                                            <form action="students" method="post">
                                                <input type="hidden" name="action" value="return">
                                                <input type="hidden" name="bookId" value="<%= i %>">
                                                <select name="studentId" class="form-select" required style="width: 200px;">
                                                    <% for (int j = 0; j < students.size(); j++) { %>
                                                        <option value="<%= j %>"><%= students.get(j).getName() %></option>
                                                    <% } %>
                                                </select>
                                                <button type="submit" class="btn btn-warning">📤 Return</button>
                                            </form>
                                        </div>
                                    <% } %>
                                </div>
                            </div>
                        <% } %>
                    </div>
                <% } else { %>
                    <div class="empty-state">
                        <div class="empty-icon">📚</div>
                        <h3 class="empty-title">No Books Available</h3>
                        <p class="empty-description">Add books to enable borrowing operations</p>
                    </div>
                <% } %>
            </div>
        </div>
    </div>
</body>
</html>
