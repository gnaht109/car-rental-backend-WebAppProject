package com.car_rental_backend.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car_rental_backend.Model.User;
import com.car_rental_backend.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000") // Adjust the origin as needed
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //LogIn endpoint
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User u = userService.login(user.getUsername(), user.getPassword());
        return "LOGIN SUCCESS " + user.getUsername();
    }

    //SignUp endpoint
    @PostMapping("/signup")
    public String signUp(@RequestBody @Valid User user) {
        User u = userService.createUser(
            user.getUsername(),
            user.getPassword(),
            user.getRole(), 
            user.getEmail(), 
            user.getPhone()
        );
        if (u == null) {
            return "REGISTER FAIL";
        }
        return "SIGNUP SUCCESS " + user.getUsername();
    }
}
