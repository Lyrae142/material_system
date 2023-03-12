package com.Lyrae.material_system.service.impl;

import com.Lyrae.material_system.dao.IMemberDao;
import com.Lyrae.material_system.domain.Member;
import com.Lyrae.material_system.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberDao memberDao;

    @Override
    public List<Member> findAll() throws Exception {
        return memberDao.findAll();
    }
}
