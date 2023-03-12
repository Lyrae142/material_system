package com.Lyrae.material_system.service;

import com.Lyrae.material_system.domain.Member;

import java.util.List;

public interface IMemberService {
    List<Member> findAll() throws Exception;
}
