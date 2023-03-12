package com.Lyrae.material_system.dao;

import com.Lyrae.material_system.domain.Product;
import com.Lyrae.material_system.domain.Stock;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IStockDao {

    @Select("select * from stock")
    List<Stock> findAll() throws Exception;

    @Select("select * from stock where id = #{id}")
    Stock findById(Integer id) throws Exception;

    @Select("select * from stock where productName like '%${value}%'")
    List<Stock> searchByProName(String productName) throws Exception;

    @Insert("insert into stock(productNum,productName,executeTime,productPrice,username,productQuant,processObj) values(#{productNum},#{productName},#{executeTime},#{productPrice},#{username},#{productQuant},#{processObj})")
    void addStock(Stock stock) throws Exception;

    @Select("select * from stock where productNum = #{productNum} or productName = #{productName}")
    Stock isExist(Stock stock) throws Exception;

    @Update("update stock set productNum = #{productNum},productName = #{productName},executeTime = #{executeTime},productPrice = #{productPrice},username = #{username},productQuant = #{productQuant},processObj = #{processObj} where id = #{id}")
    void update(Stock oldStock) throws Exception;

    @Delete("delete from stock where id = #{id}")
    void deleteById(Integer id) throws Exception;

    @Delete("<script>delete from stock where id in<foreach collection='array' item='id' open='(' separator=',' close=')'>#{id}</foreach></script>")
    void deleteStocks(Integer[] ids) throws Exception;

    @Select("select * from stock where productNum = #{productNum} or productName = #{productName}")
    Stock findByPNameAndPNum(Product product) throws Exception;

}
