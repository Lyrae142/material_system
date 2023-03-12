package com.Lyrae.material_system.service.impl;

import com.Lyrae.material_system.dao.IOrderDao;
import com.Lyrae.material_system.dao.IPOrderDao;
import com.Lyrae.material_system.domain.Orders;
import com.Lyrae.material_system.domain.POrder;
import com.Lyrae.material_system.domain.Product;
import com.Lyrae.material_system.service.IOrderService;
import com.Lyrae.material_system.service.IStockService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao orderDao;
    @Autowired
    private IPOrderDao pOrderDao;
    @Autowired
    private IStockService stockService;

    @Override
    public List<Orders> findAll(Integer page, Integer size) throws Exception {
        PageHelper.startPage(page,size);
        return orderDao.findAll();
    }

    @Override
    public Orders findById(Integer id) throws Exception {
        return orderDao.findById(id);
    }

    @Override
    public void update(Orders orders) throws Exception {
        orderDao.updateOrders(orders);
        if (orders.getProductList()!=null && orders.getProductList().size()>0){
            List<POrder> pOrderList = new ArrayList<>();
            for (Product product:orders.getProductList()){
                POrder pOrder = new POrder();
                pOrder.setOrder_id(orders.getId());
                pOrder.setProduct_id(product.getId());
                pOrder.setOrder_price(product.getProductQuant()* product.getProductPrice());
                pOrder.setProduct_quant(product.getProductQuant());
                pOrderList.add(pOrder);
            }
            pOrderDao.updatePOrder(pOrderList);
        }
    }

    @Override
    public void addOrders(Orders orders) throws Exception {
        orders.setOrderTime(new Date());
        orderDao.addOrders(orders);
    }

    @Override
    public boolean addProduct4Orders(Orders orders) throws Exception {
        //封装数据
        POrder pOrder = new POrder();
        //前端页面设置为一次只可以添加一个product
        Product product = orders.getProductList().get(0);
        //防止NullPointException的错误
        if (product!=null){
            //更新库存
            boolean isEnough = stockService.subQuant(product);
            if (isEnough){
                pOrder.setOrder_id(orders.getId());
                pOrder.setProduct_id(product.getId());
                pOrder.setProduct_quant(product.getProductQuant());
                pOrder.setOrder_price(product.getProductPrice()*product.getProductQuant());
                //先查询是否存在相同的记录,只是quantity不同
                POrder oldPOrder = pOrderDao.findByOidAndPid(pOrder);
                if (oldPOrder!=null){
                    //将oldPOrder中quantity增加新添加的量
                    Integer newQuant = oldPOrder.getProduct_quant() + pOrder.getProduct_quant();
                    Double newPrice = oldPOrder.getOrder_price()+pOrder.getOrder_price();
                    oldPOrder.setProduct_quant(newQuant);
                    oldPOrder.setOrder_price(newPrice);
                    pOrderDao.update(oldPOrder);
                } else {
                    //没有重复项则直接更新product_order表
                    pOrderDao.addProduct4Orders(pOrder);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteById(Integer id) throws Exception {
        orderDao.deleteById(id);
    }

    @Override
    public void deleteProductOfOrders(Orders orders) throws Exception {
        POrder pOrder = new POrder();
        pOrder.setOrder_id(orders.getId());
        pOrder.setProduct_id(orders.getProductList().get(0).getId());
        pOrderDao.deleteProductOfOrders(pOrder);
    }

    @Override
    public List<Orders> searchByOrderNum(Integer page, Integer size, String orderNum) throws Exception {
        PageHelper.startPage(page,size);
        return orderDao.searchByOrderNum(orderNum);
    }
}
