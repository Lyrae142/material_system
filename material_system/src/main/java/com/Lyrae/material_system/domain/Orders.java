package com.Lyrae.material_system.domain;

import com.Lyrae.material_system.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Orders {
    private Integer id;
    private String orderNum;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderTime;
    private String orderTimeStr;
    private String orderDesc;
    private Integer payType; //支付方式(0支付宝 1微信 2其他)
    private String payTypeStr;
    private Integer orderStatus; //订单状态(0未支付 1已支付)
    private String orderStatusStr;
    private Member member;
    private List<Product> productList;
    private Double totalPrice;  //productList中每一项单价乘以数量 不做数据库字段的更新
    private Integer orderQuant; //productList.size()不在数据库中

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderTimeStr() {
        if (orderTime != null){
            orderTimeStr = DateUtils.date2String(orderTime,"yyyy-MM-dd");
        }
        return orderTimeStr;
    }

    public void setOrderTimeStr(String orderTimeStr) {
        this.orderTimeStr = orderTimeStr;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayTypeStr() {
        if (payType == 0){
            payTypeStr = "在线支付-支付宝";
        }else if (payType == 1){
            payTypeStr = "在线支付-微信";
        }else if (payType == 2){
            payTypeStr = "其他";
        }
        return payTypeStr;
    }

    public void setPayTypeStr(String payTypeStr) {
        this.payTypeStr = payTypeStr;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusStr() {
        if (orderStatus == 0){
            orderStatusStr = "未支付";
        }else if (orderStatus == 1){
            orderStatusStr = "已支付";
        }
        return orderStatusStr;
    }

    public void setOrderStatusStr(String orderStatusStr) {
        this.orderStatusStr = orderStatusStr;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getOrderQuant() {
        if (productList != null && productList.size()>0){
            orderQuant = productList.size();
        }
        return orderQuant;
    }

    public void setOrderQuant(Integer orderQuant) {
        this.orderQuant = orderQuant;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", orderNum='" + orderNum + '\'' +
                ", orderTime=" + orderTime +
                ", orderTimeStr='" + orderTimeStr + '\'' +
                ", orderDesc='" + orderDesc + '\'' +
                ", payType=" + payType +
                ", payTypeStr='" + payTypeStr + '\'' +
                ", orderStatus=" + orderStatus +
                ", orderStatusStr='" + orderStatusStr + '\'' +
                ", member=" + member +
                ", productList=" + productList +
                ", totalPrice=" + totalPrice +
                ", orderQuant=" + orderQuant +
                '}';
    }
}
