package com.exam.carapp.user.service;

import com.exam.carapp.user.models.User;
import com.exam.carapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean create(User user) {
        if (userRepository.findByUsername(user.getUsername()).size() != 0) {
            return false;
        } else {
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public User readBy(int id) {
        return userRepository.getById(id);
    }

    @Override
    public boolean update(User user, int id) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
