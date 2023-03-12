package com.Lyrae.material_system.service;

import com.Lyrae.material_system.domain.SystemLog;

import java.util.List;

public interface ISystemLogService {
    void save(SystemLog systemLog) throws Exception;

    List<SystemLog> findAll(Integer page,Integer size) throws Exception;

    void saveEmail(String email) throws Exception;

    String getEmail() throws Exception;
}
