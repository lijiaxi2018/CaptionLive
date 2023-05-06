package com.example.ll.service;

import com.example.ll.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(User user);


    User updateUser(Long id, User user);

    void deleteUser(Long id);
}
