package com.exam.carapp.user.service;

import com.exam.carapp.user.models.User;

import java.util.List;

public interface UserService {
    boolean create(User user);

    List<User> readAll();

    User readBy(int id);

    boolean update(User user, int id);

    boolean delete(int id);
}
