package com.mavic.librarymanagementsystem.model;

public abstract class LibraryUser {
    protected String name;
    
    protected LibraryUser(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public abstract void accessLibrary();
}

class StudentUser extends LibraryUser {
    public StudentUser(String name) {
        super(name);
    }
    
    @Override
    public void accessLibrary() {
        System.out.println(name + " can borrow books from the library!");
    }
}

class StaffUser extends LibraryUser {
    public StaffUser(String name) {
        super(name);
    }
    
    @Override
    public void accessLibrary() {
        System.out.println(name + " can manage the library operations!");
    }
}
