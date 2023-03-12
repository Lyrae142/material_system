package com.Lyrae.material_system.controller;

import com.Lyrae.material_system.domain.Product;
import com.Lyrae.material_system.domain.Stock;
import com.Lyrae.material_system.service.IStockService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private IStockService stockService;

    @RequestMapping("/findProductList")
    public List<Product> findProductList(){
        List<Product> productList = null;
        try {
            productList = stockService.findProductList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    @RequestMapping("/deleteStocks")
    public void deleteStocks(@RequestBody Integer[] ids){
        try {
            stockService.deleteStocks(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/deleteById")
    public void deleteById(@RequestBody Stock stock){
        try{
            stockService.deleteById(stock.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/update")
    public void update(@RequestBody Stock stock){
        try {
            stockService.update(stock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/addQuant")
    public void addQuant(@RequestBody Stock stock){
        try {
            stockService.addQuant(stock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/addStock",method = {RequestMethod.POST})
    public String addStock(@RequestBody Stock stock){
        String msg = "0"; //添加失败
        try {
            boolean isOk = stockService.addStock(stock);
            if (isOk){
                msg = "1"; //添加成功
            }else {
                msg = "2"; //添加失败:已存在相同编号或者名称的商品
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return msg;
        }
    }

    @RequestMapping("/findAll")
    public PageInfo findAll(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,@RequestParam(name = "size",required = true,defaultValue = "5") Integer size){
        PageInfo pageInfo = null;
        try {
            List<Stock> stockList = stockService.findAll(page, size);
            pageInfo = new PageInfo(stockList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @RequestMapping("/findById")
    public Stock findById(Integer id){
        Stock foundStock = null;
        try {
            foundStock = stockService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foundStock;
    }

    @RequestMapping("/searchByProName")
    public PageInfo searchByProName(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,@RequestParam(name = "size",required = true,defaultValue = "5") Integer size,@RequestBody Stock stock){
        PageInfo pageInfo = null;
        try {
            List<Stock> stockList = stockService.searchByProName(page,size,stock.getProductName());
            pageInfo = new PageInfo(stockList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }
}
