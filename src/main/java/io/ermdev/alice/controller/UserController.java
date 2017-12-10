package io.ermdev.alice.controller;

import io.ermdev.alice.entity.User;
import io.ermdev.alice.repository.RoleRepository;
import io.ermdev.alice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("user/{userId}")
    public User getUserById(@PathVariable("userId") Long userId) {
        return userRepository.findOne(userId);
    }

    @GetMapping("user/all")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @PostMapping("user/add")
    public User addUser(User user) {
        if(user.getRoles().size() < 1)
            userRepository.save(user);
        else {
            roleRepository.save(user.getRoles());
            user=userRepository.save(user);
        }
        return user;
    }
}
