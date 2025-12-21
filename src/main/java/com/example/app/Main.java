package com.example.app;


import com.example.config.HibernateUtil;
import com.example.model.User;
import com.example.service.impl.UserServiceImpl;
import com.example.util.ConsoleHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
//ИСПРАВИТЬ!!!
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final UserServiceImpl USER_SERVICE_IMPL = new UserServiceImpl();

    public static void main(String[] args) {
        try {
            boolean run = true;
            while (run) {
                printMenu();
                String choice = ConsoleHelper.readLine("Select> ");
                switch (choice) {
                    case "1": createUser(); break;
                    case "2": listUsers(); break;
                    case "3": getUserById(); break;
                    case "4": updateUser(); break;
                    case "5": deleteUser(); break;
                    case "0": run = false; break;
                    default: System.out.println("Unknown option");
                }
            }
        } catch (Exception e) {
            log.error("Unhandled exception", e);
        } finally {
            HibernateUtil.shutdown();
            System.out.println("Exiting...");
        }
    }

    private static void printMenu() {
        System.out.println("\n--- User Service ---");
        System.out.println("1) Create user");
        System.out.println("2) List all users");
        System.out.println("3) Get user by id");
        System.out.println("4) Update user");
        System.out.println("5) Delete user");
        System.out.println("0) Exit");
    }

    private static void createUser() {
        try {
            String name = ConsoleHelper.readLine("Name: ");
            String email = ConsoleHelper.readLine("Email: ");
            String ageStr = ConsoleHelper.readLine("Age (leave empty): ");
            Integer age = ageStr.isEmpty() ? null : Integer.valueOf(ageStr);
            User u = USER_SERVICE_IMPL.create(name, email, age);
            System.out.println("Created: " + u);
        } catch (Exception e) {
            log.error("Error creating user", e);
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listUsers() {
        try {
            List<User> users = USER_SERVICE_IMPL.listAll();
            if (users.isEmpty()) System.out.println("No users found.");
            else users.forEach(System.out::println);
        } catch (Exception e) {
            log.error("Error listing users", e);
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getUserById() {
        try {
            Long id = ConsoleHelper.readLong("Id: ");
            if (id == null) { System.out.println("Invalid id"); return; }
            Optional<User> u = USER_SERVICE_IMPL.getById(id);
            System.out.println(u.map(Object::toString).orElse("Not found"));
        } catch (Exception e) {
            log.error("Error fetching user", e);
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateUser() {
        try {
            Long id = ConsoleHelper.readLong("Id: ");
            if (id == null) { System.out.println("Invalid id"); return; }
            String name = ConsoleHelper.readLine("Name (leave empty to keep): ");
            String email = ConsoleHelper.readLine("Email (leave empty to keep): ");
            String ageStr = ConsoleHelper.readLine("Age (leave empty to keep): ");
            Integer age = ageStr.isEmpty() ? null : Integer.valueOf(ageStr);
            User updated = USER_SERVICE_IMPL.update(id, name.isEmpty() ? null : name, email.isEmpty() ? null : email, age);
            System.out.println("Updated: " + updated);
        } catch (Exception e) {
            log.error("Error updating user", e);
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteUser() {
        try {
            Long id = ConsoleHelper.readLong("Id: ");
            if (id == null) { System.out.println("Invalid id"); return; }
            boolean ok = USER_SERVICE_IMPL.delete(id);
            System.out.println(ok ? "Deleted" : "Not found");
        } catch (Exception e) {
            log.error("Error deleting user", e);
            System.out.println("Error: " + e.getMessage());
        }
    }
}

