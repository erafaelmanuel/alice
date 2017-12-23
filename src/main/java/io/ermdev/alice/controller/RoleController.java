package io.ermdev.alice.controller;

import io.ermdev.alice.dto.RoleDto;
import io.ermdev.alice.repository.RoleRepository;
import io.ermdev.mapfierj.SimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    private RoleRepository roleRepository;
    private SimpleMapper mapper;

    @Autowired
    public RoleController(RoleRepository roleRepository, SimpleMapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @GetMapping("role/{roleId}")
    public RoleDto getById(@PathVariable("roleId") long roleId) {
        return mapper.set(roleRepository.findById(roleId)).mapTo(RoleDto.class);
    }

    @GetMapping("role/all")
    public RoleDto getAll() {
        return null;
    }

}g
