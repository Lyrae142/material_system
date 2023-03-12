package com.Lyrae.material_system.service;

import com.Lyrae.material_system.domain.Permission;
import com.Lyrae.material_system.domain.Role;

import java.util.List;

public interface IRoleService {
    List<Role> findAll() throws Exception;

    Role getDetail(Integer id) throws Exception;

    void addRole(Role role) throws Exception;

    List<Role> findAllByRoleName(String roleName) throws Exception;

    List<Permission> loadPermissionsById(Integer id) throws Exception;

    void addPermissions(Role role) throws Exception;
}
