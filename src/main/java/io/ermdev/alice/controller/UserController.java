package io.ermdev.alice.controller;

import io.ermdev.alice.dto.RoleDto;
import io.ermdev.alice.dto.UserDto;
import io.ermdev.alice.entity.Role;
import io.ermdev.alice.entity.User;
import io.ermdev.alice.repository.RoleRepository;
import io.ermdev.alice.repository.UserRepository;
import io.ermdev.mapfierj.SimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private SimpleMapper mapper;

    @Autowired
    public UserController(UserRepository userRepository, RoleRepository roleRepository, SimpleMapper mapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @GetMapping("user/{userId}")
    public UserDto getUserById(@PathVariable("userId") Long userId) {
        User user = userRepository.findById(userId);

        List<RoleDto> roles = new ArrayList<>();
        user.getRoles().parallelStream().forEach(role -> roles.add(mapper.set(role).mapTo(RoleDto.class)));

        UserDto userDto = mapper.set(user).mapTo(UserDto.class);
        userDto.setRoles(roles);
        return userDto;
    }

    @GetMapping("user/all")
    public List<UserDto> getAllUser() {
        List<UserDto> users = new ArrayList<>();
        userRepository.findAll().parallelStream().forEach(user -> {
            List<RoleDto> roles = new ArrayList<>();
            user.getRoles().parallelStream().forEach(role -> roles.add(mapper.set(role).mapTo(RoleDto.class)));

            UserDto userDto = mapper.set(user).mapTo(UserDto.class);
            userDto.setRoles(roles);
            users.add(userDto);
        });
        return users;
    }

    @PostMapping("user/add")
    public UserDto addUser(@RequestParam(value = "roleIds", required = false) List<Long> roleIds, @RequestBody User user) {
        user.getRoles().clear();
        if(roleIds != null && roleIds.size() > 0) {
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
        List<RoleDto> roles = new ArrayList<>();
        user.getRoles().parallelStream().forEach(role -> roles.add(mapper.set(role).mapTo(RoleDto.class)));

        UserDto userDto = mapper.set(user).mapTo(UserDto.class);
        userDto.setRoles(roles);
        return userDto;
    }

    @PutMapping("user/update/{userId}")
    public UserDto updateUserById(
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
        if(roleIds != null && roleIds.size() > 0) {
            List<Role> roles = new ArrayList<>();
            roleIds.parallelStream().forEach(roleId-> roles.add(roleRepository.findById(roleId)));
            user.getRoles().addAll(roles);
        }
        if(user.getRoles().size() > 0) {
            user.getRoles().parallelStream().forEach(role -> roleRepository.save(role));
            currentUser.getRoles().clear();
            currentUser.getRoles().addAll(user.getRoles());
        }
        user = userRepository.save(currentUser);
        List<RoleDto> roles = new ArrayList<>();
        user.getRoles().parallelStream().forEach(role -> roles.add(mapper.set(role).mapTo(RoleDto.class)));

        UserDto userDto = mapper.set(user).mapTo(UserDto.class);
        userDto.setRoles(roles);
        return userDto;
    }

    @DeleteMapping("user/delete/{userId}")
    public UserDto deleteUserById(@PathVariable("userId") Long userId) {
        User user = userRepository.findOne(userId);
        List<RoleDto> roles = new ArrayList<>();
        user.getRoles().parallelStream().forEach(role -> roles.add(mapper.set(role).mapTo(RoleDto.class)));

        userRepository.delete(user);
        UserDto userDto = mapper.set(user).mapTo(UserDto.class);
        userDto.setRoles(roles);
        return userDto;
    }
}
