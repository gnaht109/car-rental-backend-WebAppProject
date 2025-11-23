package com.car_rental_backend.Service;

import java.util.Optional;

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
    User user = Optional.ofNullable(userRepository.findByUsername(username))
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!password.equals(user.getPassword())) {
        throw new RuntimeException("Password Incorrect");
    }

    return user;
}

    

    //SignUp logic
    /**
     * Creates a new User object with the specified details and saves it to the database.
     *
     * @param username the username of the new user
     * @param password the password for the new user (should be hashed before saving)
     * @param role     the role assigned to the user (e.g., "USER", "ADMIN")
     * @param email    the email address of the user
     * @param phone    the phone number of the user
     * @return the created User object after it has been saved to the database
     * @throws RuntimeException if the user cannot be created or saved
     */
    public User createUser(String username, String password, String role, String email, String phone) {
        if(userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already taken");
        }
        if(userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
        
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(role);  
        newUser.setEmail(email);
        newUser.setPhone(phone);
        
        return userRepository.save(newUser);
    }

    
}
