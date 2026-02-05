<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Library Management System</title>
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
            padding: 60px 20px;
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
            font-size: 3.5rem;
            font-weight: 700;
            margin-bottom: 12px;
            letter-spacing: -0.5px;
        }

        .header p {
            font-size: 1.25rem;
            opacity: 0.95;
            font-weight: 400;
            letter-spacing: 0.3px;
        }

        /* Container */
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }

        /* Features Section */
        .features {
            margin: 80px auto;
            padding: 60px 40px;
            background: var(--bg-white);
            border-radius: 16px;
            box-shadow: var(--shadow-xl);
        }

        .features h2 {
            font-family: 'Merriweather', serif;
            text-align: center;
            color: var(--primary-dark);
            margin-bottom: 50px;
            font-size: 2.5rem;
            font-weight: 700;
            letter-spacing: -0.3px;
        }

        .feature-list {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
            gap: 30px;
        }

        .feature-item {
            padding: 30px;
            text-align: center;
            border-radius: 12px;
            background: var(--bg-light);
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            border: 1px solid var(--border);
        }

        .feature-item:hover {
            transform: translateY(-4px);
            box-shadow: var(--shadow-lg);
            background: var(--bg-white);
            border-color: var(--primary-light);
        }

        .feature-item h4 {
            color: var(--primary);
            margin-bottom: 12px;
            font-size: 1.25rem;
            font-weight: 600;
        }

        .feature-item p {
            color: var(--text-secondary);
            font-size: 0.95rem;
            line-height: 1.7;
        }

        /* Navigation Grid */
        .nav-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
            gap: 32px;
            margin: 60px auto 80px;
        }

        .nav-card {
            background: var(--bg-white);
            padding: 40px 30px;
            border-radius: 16px;
            box-shadow: var(--shadow-md);
            border: 1px solid var(--border);
            text-align: center;
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            position: relative;
            overflow: hidden;
        }

        .nav-card::before {
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

        .nav-card:hover {
            transform: translateY(-8px);
            box-shadow: var(--shadow-xl);
            border-color: var(--primary-light);
        }

        .nav-card:hover::before {
            transform: scaleX(1);
        }

        .nav-card h3 {
            margin-bottom: 16px;
            color: var(--primary-dark);
            font-size: 1.5rem;
            font-weight: 600;
            font-family: 'Merriweather', serif;
        }

        .nav-card p {
            margin-bottom: 24px;
            color: var(--text-secondary);
            line-height: 1.8;
            font-size: 0.95rem;
        }

        .nav-link {
            display: inline-block;
            padding: 12px 32px;
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-light) 100%);
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 600;
            font-size: 0.95rem;
            transition: all 0.3s ease;
            border: 2px solid transparent;
            letter-spacing: 0.5px;
            cursor: pointer;
        }

        .nav-link:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(30, 64, 175, 0.3);
        }

        .nav-link:active {
            transform: translateY(0);
        }

        /* Footer */
        .footer {
            text-align: center;
            padding: 40px 20px;
            color: var(--text-secondary);
            background: var(--bg-white);
            margin-top: 60px;
            border-top: 1px solid var(--border);
            font-size: 0.9rem;
        }

        .footer p {
            max-width: 600px;
            margin: 0 auto;
            line-height: 1.8;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .header h1 {
                font-size: 2.5rem;
            }

            .header p {
                font-size: 1.1rem;
            }

            .features {
                padding: 40px 24px;
                margin: 60px auto;
            }

            .features h2 {
                font-size: 2rem;
                margin-bottom: 40px;
            }

            .feature-list {
                grid-template-columns: 1fr;
            }

            .nav-grid {
                grid-template-columns: 1fr;
                gap: 24px;
                margin: 40px auto 60px;
            }

            .nav-card {
                padding: 32px 24px;
            }

            .nav-card h3 {
                font-size: 1.25rem;
            }
        }

        @media (max-width: 480px) {
            .header {
                padding: 40px 16px;
            }

            .header h1 {
                font-size: 2rem;
                margin-bottom: 8px;
            }

            .header p {
                font-size: 1rem;
            }

            .container {
                padding: 0 16px;
            }

            .features {
                padding: 30px 16px;
                margin: 40px auto;
                border-radius: 12px;
            }

            .features h2 {
                font-size: 1.5rem;
                margin-bottom: 30px;
            }

            .feature-item {
                padding: 20px;
            }

            .nav-card {
                padding: 24px 16px;
                border-radius: 12px;
            }

            .nav-card h3 {
                font-size: 1.1rem;
            }

            .nav-link {
                padding: 10px 24px;
                font-size: 0.9rem;
            }
        }
    </style>
</head>
<body>
<div class="header">
    <div class="header-content">
        <h1>📚 Library Management System</h1>
        <p>Complete solution for managing books, students, and library operations</p>
    </div>
</div>

<div class="container">
    <div class="features">
        <h2>System Features</h2>
        <div class="feature-list">
            <div class="feature-item">
                <h4>📖 Book Management</h4>
                <p>Add, update, and track book availability with comprehensive catalog management and real-time inventory updates.</p>
            </div>
            <div class="feature-item">
                <h4>👨‍🎓 Student Portal</h4>
                <p>Intuitive interface for students to borrow and return books with ease, view borrowing history, and reserve titles.</p>
            </div>
            <div class="feature-item">
                <h4>👨‍💼 Staff Administration</h4>
                <p>Powerful tools for library staff to manage operations, oversee transactions, and maintain system integrity.</p>
            </div>
            <div class="feature-item">
                <h4>🔄 Real-time Updates</h4>
                <p>Instant notifications and status updates for all operations, ensuring everyone stays informed and synchronized.</p>
            </div>
        </div>
    </div>

    <div class="nav-grid">
        <div class="nav-card">
            <h3>📚 Books</h3>
            <p>Browse and manage the complete book catalog with advanced filtering and search capabilities. Add new books, update availability, and view detailed information including author, ISBN, and publication details.</p>
            <a href="books" class="nav-link">Manage Books</a>
        </div>

        <div class="nav-card">
            <h3>👨‍🎓 Students</h3>
            <p>Access the student portal for borrowing and returning books seamlessly. Register new students, track borrowing history, manage dues, and view personalized recommendations.</p>
            <a href="students" class="nav-link">Student Portal</a>
        </div>

        <div class="nav-card">
            <h3>👨‍💼 Staff</h3>
            <p>Administrative interface for library staff with comprehensive management tools. Manage books, oversee operations, generate reports, and maintain the library system efficiently.</p>
            <a href="staff" class="nav-link">Staff Admin</a>
        </div>
    </div>
</div>

<div class="footer">
    <p>&copy; 2024 Library Management System. Built with Java Servlets and JSP.</p>
</div>
</body>
</html>
