package com.example.fooddelivery.services;

import com.example.fooddelivery.entities.MyUser;
import com.example.fooddelivery.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MyUser createUser(MyUser user) {
        return userRepository.save(user);
    }

    public MyUser updateUser(int id, MyUser newUserDetails) {
        Optional<MyUser> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            MyUser user = optionalUser.get();
            user.setPassword(newUserDetails.getPassword());
            user.setEmail(newUserDetails.getEmail());
            user.setFirstname(newUserDetails.getFirstname());
            user.setLastname(newUserDetails.getLastname());
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    public boolean deleteUser(int id) {
        Optional<MyUser> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public MyUser getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }
}
