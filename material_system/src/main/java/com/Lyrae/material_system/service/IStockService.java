package com.Lyrae.material_system.service;

import com.Lyrae.material_system.domain.Product;
import com.Lyrae.material_system.domain.Stock;

import java.util.List;

public interface IStockService {
    List<Stock> findAll(Integer page,Integer size) throws Exception;

    Stock findById(Integer id) throws Exception;

    List<Stock> searchByProName(Integer page,Integer size,String productName) throws Exception;

    boolean addStock(Stock stock) throws Exception;

    void addQuant(Stock stock) throws Exception;

    void update(Stock stock) throws Exception;

    void deleteById(Integer id) throws Exception;

    void deleteStocks(Integer[] ids) throws Exception;

    List<Product> findProductList() throws Exception;

    boolean subQuant(Product product) throws Exception;
}
