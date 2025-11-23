package com.car_rental_backend.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.car_rental_backend.Model.User;
import com.car_rental_backend.Repository.UserRepository;
import com.car_rental_backend.util.BadRequestException;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //LogIn logic
    public User login(String username, String password) {
    User user = Optional.ofNullable(userRepository.findByUsername(username))
            .orElseThrow(() -> new BadRequestException("User not found"));

    if (!password.equals(user.getPassword())) {
        throw new BadRequestException("Password Incorrect");
    }

    return user;
}

    

    //SignUp logic
    public User signUp(String username, String password, String role, String email, String phone) {
        Optional.ofNullable(userRepository.findByUsername(username))
            .ifPresent(u -> { throw new BadRequestException("Username already exists"); });

        Optional.ofNullable(userRepository.findByEmail(email))
            .ifPresent(u -> { throw new BadRequestException("Email already registered"); });

        

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(role);  
        newUser.setEmail(email);
        newUser.setPhone(phone);
        
        return userRepository.save(newUser);
    }
}
