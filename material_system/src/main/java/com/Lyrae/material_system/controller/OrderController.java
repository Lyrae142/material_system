package com.Lyrae.material_system.controller;

import com.Lyrae.material_system.domain.Orders;
import com.Lyrae.material_system.service.IOrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/findAll")
    public PageInfo findAll(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,@RequestParam(name = "size",required = true,defaultValue = "5") Integer size){
        PageInfo pageInfo = null;
        try{
            List<Orders> ordersList = orderService.findAll(page,size);
            pageInfo = new PageInfo(ordersList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageInfo;
    }

    @RequestMapping("/findById")
    public Orders findById(Integer id){
        Orders orders = null;
        try {
            orders = orderService.findById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return orders;
    }

    @RequestMapping("/update")
    public void update(@RequestBody Orders orders){
        try {
            orderService.update(orders);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/addOrders")
    public void addOrders(@RequestBody Orders orders){
        try {
            orderService.addOrders(orders);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/addProduct4Orders")
    public String addProduct4Orders(@RequestBody Orders orders){
        String msg = "0";
        try {
            boolean isOk = orderService.addProduct4Orders(orders);
            if (isOk)
                msg = "1";
            else
                msg = "0";
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return msg;
        }
    }

    @RequestMapping("/deleteById")
    public void deleteById(Integer id){
        try {
            orderService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/deleteProductOfOrders")
    public void deleteProductOfOrders(@RequestBody Orders orders){
        try {
            orderService.deleteProductOfOrders(orders);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/searchByOrderNum")
    public PageInfo searchByOrderNum(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "5") Integer size,@RequestBody Orders orders){
        PageInfo pageInfo = null;
        try {
            List<Orders> ordersList = orderService.searchByOrderNum(page,size,orders.getOrderNum());
            pageInfo = new PageInfo(ordersList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }
}
