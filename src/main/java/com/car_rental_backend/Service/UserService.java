package com.car_rental_backend.Service;

import org.springframework.stereotype.Service;

import com.car_rental_backend.Model.User;
import com.car_rental_backend.Repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //LogIn logic
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null; // Invalid credentials
    }

    //SignUp logic
    public User signUp(String username, String password, String role, String email, String phone) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(role);  
        newUser.setEmail(email);
        newUser.setPhone(phone);
        if (userRepository.findByUsername(newUser.getUsername()) != null 
        ||userRepository.findByEmail(newUser.getEmail()) != null) {
            return null; // Username already exists or email already registered
        }
        return userRepository.save(newUser);
    }
}
