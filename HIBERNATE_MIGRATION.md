# Hibernate Migration Guide

## Overview
This project has been successfully migrated from plain JDBC to Hibernate ORM for better database management and object-relational mapping.

## What Changed

### 1. Dependencies (pom.xml)
- ✅ Added Hibernate Core 6.4.4.Final
- ✅ Added Jakarta Persistence API 3.1.0
- ✅ Kept MySQL Connector for database connectivity

### 2. Configuration
- ✅ Created `hibernate.cfg.xml` in `src/main/resources/`
- ✅ Configured MySQL connection settings
- ✅ Enabled automatic schema generation (`hbm2ddl.auto=update`)
- ✅ Enabled SQL logging for debugging

### 3. Utility Classes
- ✅ Created `HibernateUtil.java` - Manages SessionFactory lifecycle
- ✅ Created `DataInitializer.java` - Initializes sample data
- ✅ Created `AppContextListener.java` - Application lifecycle management
- ⚠️ `DatabaseUtil.java` is now deprecated (kept for reference)

### 4. Model Classes (JPA Annotations)

#### Person (Base Class)
```java
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;  // Auto-generated database ID
    
    @Column(nullable = false)
    protected String name;
    
    @Column(name = "user_id", unique = true)
    protected int userId;  // User-provided ID
}
```

#### Book
```java
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String author;
    
    @Column(name = "is_available")
    private boolean isAvailable;
}
```

#### Student
```java
@Entity
@Table(name = "students")
public class Student extends Person {
    @Column(nullable = false)
    private String department;
}
```

#### Staff
```java
@Entity
@Table(name = "staff")
public class Staff extends Person {
    // Inherits all fields from Person
}
```

### 5. DAO Classes (Refactored)

All DAO classes now use Hibernate Session API instead of JDBC:

**Before (JDBC):**
```java
String sql = "INSERT INTO books (title, author) VALUES (?, ?)";
try (Connection conn = DatabaseUtil.getConnection();
     PreparedStatement pstmt = conn.prepareStatement(sql)) {
    pstmt.setString(1, book.getTitle());
    pstmt.setString(2, book.getAuthor());
    pstmt.executeUpdate();
}
```

**After (Hibernate):**
```java
try (Session session = HibernateUtil.getSessionFactory().openSession()) {
    transaction = session.beginTransaction();
    session.persist(book);
    transaction.commit();
}
```

### 6. Servlet Controllers

All servlets updated to:
- Use `HibernateUtil` instead of `DatabaseUtil`
- Handle Long IDs from Hibernate
- Use list indices for operations (since we're not exposing database IDs)
- Proper error handling and transaction management

## Database Schema

Hibernate automatically creates these tables:

### books
| Column | Type | Constraints |
|--------|------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| title | VARCHAR(255) | NOT NULL |
| author | VARCHAR(255) | NOT NULL |
| is_available | BOOLEAN | DEFAULT TRUE |

### students
| Column | Type | Constraints |
|--------|------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| name | VARCHAR(255) | NOT NULL |
| user_id | INT | UNIQUE |
| department | VARCHAR(255) | NOT NULL |

### staff
| Column | Type | Constraints |
|--------|------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| name | VARCHAR(255) | NOT NULL |
| user_id | INT | UNIQUE |

## Configuration

### Database Connection
Update `hibernate.cfg.xml` with your MySQL credentials:

```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/library_db</property>
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password">root</property>
```

### Schema Generation Modes

In `hibernate.cfg.xml`, you can change `hibernate.hbm2ddl.auto`:

- `validate` - Validate schema, make no changes
- `update` - Update schema (recommended for development)
- `create` - Create schema, destroying previous data
- `create-drop` - Create schema, drop on SessionFactory close

## Benefits of Hibernate

1. **No Manual SQL** - Hibernate generates SQL automatically
2. **Object-Relational Mapping** - Work with Java objects, not SQL
3. **Database Independence** - Easy to switch databases
4. **Automatic Schema Management** - Tables created automatically
5. **Transaction Management** - Built-in transaction support
6. **Caching** - Performance optimization through caching
7. **Lazy Loading** - Load data only when needed
8. **Type Safety** - Compile-time checking with HQL

## Running the Application

1. **Start MySQL Server**
   ```bash
   # Make sure MySQL is running on localhost:3306
   ```

2. **Update Database Credentials**
   - Edit `src/main/resources/hibernate.cfg.xml`
   - Set your MySQL username and password

3. **Build and Deploy**
   ```bash
   mvn clean package
   # Deploy the WAR file to your servlet container (Tomcat, etc.)
   ```

4. **Access the Application**
   ```
   http://localhost:8080/LibraryManagementSystem_war_exploded/
   ```

## Sample Data

The application automatically initializes with sample data on first run:

**Books:**
- Mathematics by Happy David
- English by Muhizi Brian
- Software Engineering by Mulindwa Christian
- A Level Mathematics by Niyonkuru Darius

**Students:**
- Tresor (ID: 123) - SideJobs
- Herve (ID: 100) - Robotics
- Aloys (ID: 101) - Backend

**Staff:**
- Isaac (ID: 1)
- Herve (ID: 99)

## Troubleshooting

### Connection Issues
- Verify MySQL is running
- Check credentials in `hibernate.cfg.xml`
- Ensure MySQL port 3306 is accessible

### Schema Issues
- Set `hibernate.hbm2ddl.auto` to `create` to recreate tables
- Check Hibernate logs in console for SQL errors

### ClassNotFoundException
- Run `mvn clean install` to download dependencies
- Verify Hibernate JARs are in classpath

## Migration Checklist

- ✅ Added Hibernate dependencies
- ✅ Created Hibernate configuration
- ✅ Added JPA annotations to models
- ✅ Refactored all DAO classes
- ✅ Updated all servlets
- ✅ Created lifecycle listener
- ✅ Added data initializer
- ✅ Updated JSP files
- ✅ Tested all CRUD operations

## Next Steps

Consider these enhancements:
1. Add connection pooling (HikariCP or C3P0)
2. Implement proper exception handling
3. Add logging framework (SLF4J + Logback)
4. Create service layer between controllers and DAOs
5. Add validation annotations
6. Implement pagination for large datasets
7. Add search and filter functionality
8. Create unit tests with H2 in-memory database

## Resources

- [Hibernate Documentation](https://hibernate.org/orm/documentation/)
- [JPA Specification](https://jakarta.ee/specifications/persistence/)
- [MySQL Connector/J](https://dev.mysql.com/doc/connector-j/en/)
