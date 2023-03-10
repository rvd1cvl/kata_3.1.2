package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    public List<Role> getAllRoles();

    Set<Role> getRoles(Integer[] role_id);
    public void saveRole(Role role);
}