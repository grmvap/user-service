package com.example.dao;

import com.example.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    User create(User user) throws Exception;

    Optional<User> findById(Long id) throws Exception;

    List<User> findAll() throws Exception;

    User update(User user) throws Exception;

    boolean delete(Long id) throws Exception;
}