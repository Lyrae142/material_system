package com.Lyrae.material_system.controller;

import com.Lyrae.material_system.domain.Product;
import com.Lyrae.material_system.service.IProductService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
@ResponseBody
public class ProductController {

    @Autowired
    private IProductService productService;

    @RequestMapping("/findProductList")
    public List<Product> findProductList(){
        List<Product> products = null;
        try {
            products = productService.findProductList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/findAll")
    public PageInfo findAll(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,@RequestParam(name = "size",required = true,defaultValue = "5") Integer size){
        PageInfo pageInfo = null;
        try {
            List<Product> products = productService.findAll(page, size);
            pageInfo = new PageInfo(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @RequestMapping("/findById")
    public Product findById(Integer id){
        Product foundProduct = null;
        try {
            foundProduct = productService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foundProduct;
    }

    @RequestMapping(value = "/addProduct",method = {RequestMethod.POST})
    public void addProduct(@RequestBody Product product){
        try{
            productService.addProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/deleteById")
    public void deleteById(@RequestBody Product product){
        try{
            productService.deleteById(product.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/deleteProducts")
    public void deleteProducts(@RequestBody Integer[] ids){
        try {
            productService.deleteProducts(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/update")
    public void update(@RequestBody Product product){
        try {
            productService.update(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/searchByProName")
    public PageInfo searchByProName(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,@RequestParam(name = "size",required = true,defaultValue = "5") Integer size,@RequestBody Product product){
        PageInfo pageInfo = null;
        try {
            List<Product> products = productService.searchByProName(page,size,product.getProductName());
            pageInfo = new PageInfo(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  pageInfo;
    }
}
