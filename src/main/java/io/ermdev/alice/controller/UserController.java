package io.ermdev.alice.controller;

import io.ermdev.alice.entity.Role;
import io.ermdev.alice.entity.User;
import io.ermdev.alice.repository.RoleRepository;
import io.ermdev.alice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public User addUser(@RequestParam(value = "roleIds", required = false) List<Long> roleIds, @RequestBody User user) {
        user.getRoles().clear();
        if(roleIds.size() > 0) {
            List<Role> roles = new ArrayList<>();
            roleIds.parallelStream().forEach(roleId-> roles.add(roleRepository.findById(roleId)));
            user.getRoles().addAll(roles);
        }
        if(user.getRoles().size() > 0) {
            user.getRoles().parallelStream().forEach(role -> roleRepository.save(role));
            user = userRepository.save(user);
        } else {
            user=userRepository.save(user);
        }
        return user;
    }

    @PutMapping("user/update/{userId}")
    public User updateUserById(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
            @RequestBody User user) {
        User currentUser = userRepository.findOne(userId);
        if(user.getUsername() != null) {
            currentUser.setUsername(user.getUsername());
        }
        if(user.getPassword() != null) {
            currentUser.setPassword(user.getPassword());
        }
        user.getRoles().clear();
        if(roleIds.size() > 0) {
            List<Role> roles = new ArrayList<>();
            roleIds.parallelStream().forEach(roleId-> roles.add(roleRepository.findById(roleId)));
            user.getRoles().addAll(roles);
        }
        if(user.getRoles().size() > 0) {
            user.getRoles().parallelStream().forEach(role -> roleRepository.save(role));
            currentUser.getRoles().clear();
            currentUser.getRoles().addAll(user.getRoles());
        }
        return userRepository.save(currentUser);
    }

    @DeleteMapping("user/delete/{userId}")
    public User deleteUserById(@PathVariable("userId") Long userId) {
        User user = userRepository.findOne(userId);
        userRepository.delete(user);
        return user;
    }
}
