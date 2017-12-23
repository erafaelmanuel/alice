package io.ermdev.alice.controller;

import io.ermdev.alice.dto.RoleDto;
import io.ermdev.alice.entity.Role;
import io.ermdev.alice.repository.RoleRepository;
import io.ermdev.mapfierj.SimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public RoleDto getById(@PathVariable("roleId") Long roleId) {
        return mapper.set(roleRepository.findById(roleId)).mapTo(RoleDto.class);
    }

    @GetMapping("role/all")
    public List<RoleDto> getAll() {
        return mapper.set(roleRepository.findAll()).mapToList(RoleDto.class);
    }

    @PostMapping("role/add")
    public RoleDto add(@RequestBody Role role) {
        return mapper.set(roleRepository.save(role)).mapTo(RoleDto.class);
    }

    @DeleteMapping("role/{roleId}")
    public RoleDto deleteById(@PathVariable("roleId") Long roleId) {
        Role role = roleRepository.findById(roleId);
        roleRepository.delete(role);
        return mapper.set(role).mapTo(RoleDto.class);
    }

}
