package com.mavic.librarymanagementsystem.listener;

import com.mavic.librarymanagementsystem.util.DataInitializer;
import com.mavic.librarymanagementsystem.util.HibernateUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Application lifecycle listener to initialize Hibernate and sample data
 */
@WebListener
public class AppContextListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Library Management System Starting ===");
        
        try {
            // Initialize Hibernate SessionFactory
            System.out.println("Initializing Hibernate...");
            HibernateUtil.getSessionFactory();
            System.out.println("Hibernate initialized successfully!");
            
            // Initialize sample data
            System.out.println("Checking for sample data...");
            DataInitializer.initializeSampleData();
            System.out.println("Data initialization complete!");
            
        } catch (Exception e) {
            System.err.println("Error during application startup: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== Library Management System Started Successfully ===");
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("=== Library Management System Shutting Down ===");
        
        try {
            // Shutdown Hibernate SessionFactory
            HibernateUtil.shutdown();
            System.out.println("Hibernate shutdown successfully!");
        } catch (Exception e) {
            System.err.println("Error during application shutdown: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== Library Management System Stopped ===");
    }
}
