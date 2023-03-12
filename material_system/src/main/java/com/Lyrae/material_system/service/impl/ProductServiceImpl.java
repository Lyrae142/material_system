package com.Lyrae.material_system.service.impl;

import com.Lyrae.material_system.dao.IProductDao;
import com.Lyrae.material_system.domain.Product;
import com.Lyrae.material_system.service.IProductService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Override
    public List<Product> findAll(Integer page, Integer size) throws Exception {
        PageHelper.startPage(page,size);
        return productDao.findAll();
    }

    @Override
    public Product findById(Integer id) throws Exception {
        return productDao.findById(id);
    }

    @Override
    public void addProduct(Product product) throws Exception {
        productDao.insert(product);
    }

    @Override
    public void deleteById(Integer id) throws Exception {
        productDao.deleteById(id);
    }

    @Override
    public void update(Product product) throws Exception {
        productDao.update(product);
    }

    @Override
    public void deleteProducts(Integer[] ids) throws Exception {
        productDao.deleteProducts(ids);
    }

    @Override
    public List<Product> searchByProName(Integer page, Integer size, String productName) throws Exception {
        PageHelper.startPage(page,size);
        return productDao.searchByProName(productName);
    }

    @Override
    public List<Product> findProductList() throws Exception {
        return productDao.findAll();
    }
}
