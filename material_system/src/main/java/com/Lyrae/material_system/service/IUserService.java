package com.Lyrae.material_system.service;

import com.Lyrae.material_system.domain.Role;
import com.Lyrae.material_system.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {

    List<UserInfo> findAll() throws Exception;

    void addUser(UserInfo userInfo) throws Exception;

    UserInfo getDetail(Integer id) throws Exception;

    void shutdown(Integer id) throws Exception;

    void authentic(Integer id) throws Exception;

    List<UserInfo> findAllByUsername(String searchName) throws Exception;

    List<Role> loadRolesById(Integer id) throws Exception;

    void addRoles(UserInfo userInfo) throws Exception;
}
