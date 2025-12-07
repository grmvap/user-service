package com.example.service;



import com.example.dao.UserDao;
import com.example.dao.impl.UserDaoImpl;
import com.example.model.User;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserDao dao = new UserDaoImpl();

    public User create(String name, String email, Integer age) throws Exception {
        User user = new User(name, email, age);
        return dao.create(user);
    }

    public Optional<User> getById(Long id) throws Exception {
        return dao.findById(id);
    }

    public List<User> listAll() throws Exception {
        return dao.findAll();
    }

    public User update(Long id, String name, String email, Integer age) throws Exception {
        Optional<User> opt = dao.findById(id);
        if (opt.isEmpty()) throw new IllegalArgumentException("User not found with id: " + id);
        User u = opt.get();
        if (name != null) u.setName(name);
        if (email != null) u.setEmail(email);
        if (age != null) u.setAge(age);
        return dao.update(u);
    }

    public boolean delete(Long id) throws Exception {
        return dao.delete(id);
    }
}

