package com.starlight.service;

import com.starlight.model.User;
import com.starlight.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements CommonService<User, Integer> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(User model) {
        userRepository.save(model);
    }

    @Override
    public User findById(Integer value) {
        return null;
    }

    @Override
    public User update(User model) {
        return null;
    }

    @Override
    public void delete(Integer value) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
