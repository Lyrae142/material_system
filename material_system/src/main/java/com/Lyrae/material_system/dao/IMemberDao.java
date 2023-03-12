package com.Lyrae.material_system.dao;

import com.Lyrae.material_system.domain.Member;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IMemberDao {

    @Select("select * from member where id = #{id}")
    Member findById(Integer id) throws Exception;

    @Select("select * from member")
    List<Member> findAll() throws Exception;
}
