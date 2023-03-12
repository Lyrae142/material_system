package com.Lyrae.material_system.service.impl;

import com.Lyrae.material_system.dao.ISystemLogDao;
import com.Lyrae.material_system.domain.SystemLog;
import com.Lyrae.material_system.service.ISystemLogService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SystemLogServiceImpl implements ISystemLogService {

    @Autowired
    private ISystemLogDao systemLogDao;

    @Override
    public void save(SystemLog systemLog) throws Exception {
        systemLogDao.save(systemLog);
    }

    @Override
    public List<SystemLog> findAll(Integer page, Integer size) throws Exception {
        PageHelper.startPage(page,size);
        return systemLogDao.findAll();
    }

    @Override
    public void saveEmail(String email) throws Exception {
        String existEmail = systemLogDao.getEmail();
        if (existEmail != null && existEmail != ""){
            systemLogDao.updateEmail(email);
        }else {
            systemLogDao.saveEmail(email);
        }
    }

    @Override
    public String getEmail() throws Exception {
        return systemLogDao.getEmail();
    }
}
