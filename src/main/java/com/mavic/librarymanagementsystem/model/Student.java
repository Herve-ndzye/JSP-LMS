package com.mavic.librarymanagementsystem.model;

public class Student extends Person {
    private String department;
    
    public Student() {
        super();
    }
    
    public Student(String name, int id, String department) {
        super(name, id);
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
                ", id=" + id +
                ", department='" + department + '\'' +
                '}';
    }
}
