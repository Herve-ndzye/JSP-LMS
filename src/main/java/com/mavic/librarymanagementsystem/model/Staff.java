package com.mavic.librarymanagementsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "staff")
public class Staff extends Person {
    
    public Staff() {
        super();
    }
    
    public Staff(String name, int userId) {
        super(name, userId);
    }
    
    public void manageBook(Book book, boolean add) {
        if (add) {
            book.setAvailable(true);
            System.out.println("The " + book.getTitle() + " book has been added to the library.");
        } else {
            book.setAvailable(false);
            System.out.println("The " + book.getTitle() + " book has been removed from the library.");
        }
    }
    
    @Override
    public String toString() {
        return "Staff{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }
}
