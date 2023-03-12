package com.Lyrae.material_system.service;

import com.Lyrae.material_system.domain.Orders;

import java.util.List;

public interface IOrderService {
    List<Orders> findAll(Integer page,Integer size) throws Exception;

    Orders findById(Integer id) throws Exception;

    void update(Orders orders) throws Exception;

    void addOrders(Orders orders) throws Exception;

    boolean addProduct4Orders(Orders orders) throws Exception;

    void deleteById(Integer id) throws Exception;

    void deleteProductOfOrders(Orders orders) throws Exception;

    List<Orders> searchByOrderNum(Integer page,Integer size,String orderNum) throws Exception;

}
