package com.Lyrae.material_system.service;

import com.Lyrae.material_system.domain.Permission;
import com.Lyrae.material_system.domain.Role;

import java.util.List;

public interface IPermissionService {
    List<Permission> findAll() throws Exception;

    void addPermission(Permission permission) throws Exception;

    List<Permission> findAllByPermissionName(String permissionName) throws Exception;

    void deletePermission(Integer id) throws Exception;

    void deletePermissions(Integer[] ids) throws Exception;

    List<Role> getRoleByUrl(String url) throws Exception;
}
