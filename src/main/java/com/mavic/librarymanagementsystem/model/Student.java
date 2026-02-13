package com.mavic.librarymanagementsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student extends Person {
    @Column(nullable = false)
    private String department;
    
    public Student() {
        super();
    }
    
    public Student(String name, int userId, String department) {
        super(name, userId);
        this.department = department;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            book.setAvailable(false);
            System.out.println(name + " successfully borrowed: " + book.getTitle());
        } else {
            System.out.println("Sorry, " + book.getTitle() + " is not available.");
        }
    }
    
    public void returnBook(Book book) {
        book.setAvailable(true);
        System.out.println(name + " returned: " + book.getTitle());
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                ", department='" + department + '\'' +
                '}';
    }
}
