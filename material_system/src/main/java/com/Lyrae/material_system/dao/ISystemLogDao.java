package com.Lyrae.material_system.dao;

import com.Lyrae.material_system.domain.SystemLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ISystemLogDao {

    @Insert("insert into logs(visitTime,username,ip,url,executionTime,method) values(#{visitTime},#{username},#{ip},#{url},#{executionTime},#{method})")
    void save(SystemLog systemLog) throws Exception;

    @Select("select * from logs")
    List<SystemLog> findAll() throws Exception;

    @Insert("insert into systemSet(email) values (#{email})")
    void saveEmail(String email) throws Exception;

    @Select("select email from systemSet where id = 1")
    String getEmail() throws Exception;

    @Update("update systemSet set email = #{email} where id = 1")
    void updateEmail(String email) throws Exception;

}
