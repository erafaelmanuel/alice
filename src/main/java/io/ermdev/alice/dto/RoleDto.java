package io.ermdev.alice.dto;

import io.ermdev.mapfierj.Excluded;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class RoleDto {

    private Long id;
    private String name;

    @Excluded
    private List<UserDto> users = new ArrayList<>();

    public RoleDto() {}

    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoleDto(Long id, String name, List<UserDto> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
