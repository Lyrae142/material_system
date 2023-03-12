new Vue({
    el:"#orders",
    data:{
        newOrders:{
            id:"",
            orderNum:"",
            orderTime:"",
            orderTimeStr:"",
            orderDesc:"",
            payType:"",
            payTypeStr:"",
            orderStatus:"",
            orderStatusStr:"",
            member:{
                id:"",
                name:"",
                nickName:"",
                phoneNum:"",
                email:""
            },
            productList:[],
            productQuant:"", //for compute totalPrice about add product of orders
            productIndex:"", //index for productList
            order_price:"",  //数量乘以单价
            totalPrice:"",   //订单价格
            orderQuant:""
        },
        orders:{
            id:"",
            orderNum:"",
            orderTime:"",
            orderTimeStr:"",
            orderDesc:"",
            payType:"",
            payTypeStr:"",
            orderStatus:"",
            orderStatusStr:"",
            member:{
                id:"",
                name:"",
                nickName:"",
                phoneNum:"",
                email:""
            },
            productList:[],
            totalPrice:"",
            orderQuant:""
        },
        productList:[],
        pageInfo:{},
        ids:[],
        memberList:[],
        searchNum:"",
        currentUsername:"未登录"
    },
    methods:{
        findAll:function (page,size) {
            var _this = this;
            axios.get('/manage_sys_web/orders/findAll?page='+page+'&size='+size)
                .then(function (response) {
                    // handle success
                    _this.pageInfo = response.data;
                })
                .catch(function (error) {
                    // handle error
                    console.log(error);
                })
        },
        findById:function (id) {
            var _this = this;
            axios.get('/manage_sys_web/orders/findById',{
                params:{
                    id:id
                }
            })
                .then(function (response) {
                    // handle success
                    _this.orders = response.data;
                })
                .catch(function (error) {
                    // handle error
                    console.log(error);
                })
        },
        onlyNumber:function (event) {
            var keyCode = event.keyCode;
            if((keyCode<48&&keyCode!=8)||keyCode>57){
                event.preventDefault();
                return;
            }
        },
        updatePrice:function () {
            var totalPrice = 0.0;
            var productList = this.orders.productList;
            for(var i=0;i<productList.length;i++){
                totalPrice+=productList[i].productPrice * productList[i].productQuant;
            }
            this.orders.totalPrice = totalPrice;
        },
        saveOrder:function () {
            var _this = this;
            axios.post('/manage_sys_web/orders/update', _this.orders)
                .then(function (response) {
                    _this.findAll(_this.pageInfo.pageNum,_this.pageInfo.pageSize);
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        findMembers:function () {
            var _this = this;
            axios.get('/manage_sys_web/members/findAll')
                .then(function (response) {
                    _this.memberList = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        addOrders:function () {
            var _this = this;
            console.log(_this.newOrders);
            axios.post('/manage_sys_web/orders/addOrders',_this.newOrders)
                .then(function (response) {
                    _this.findAll(_this.pageInfo.pageNum,_this.pageInfo.pageSize);
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        addProduct4Order:function (id) {
            //1.根据id查出order,为后续操作提供数据
            this.findById(id);
            //2.回显所有的product数据,为后续操作提供数据
            var _this = this;
            axios.get('/manage_sys_web/product/findProductList')
                .then(function (response) {
                    _this.productList = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        computeTPWhenPC:function() {
            var index = this.newOrders.productIndex;
            var productQuant = this.newOrders.productQuant;
            var productPrice = this.productList[index].productPrice;
            this.newOrders.order_price = productPrice * productQuant;
        },
        updateNewPrice:function () {
            var index = this.newOrders.productIndex;
            var productQuant = this.newOrders.productQuant;
            var productPrice = this.productList[index].productPrice;
            this.newOrders.order_price = productQuant * productPrice;

        },
        addProductForOrder:function () {
            var _this = this;
            //将product封装到orders中
            var index = this.newOrders.productIndex;
            var product = this.productList[index]
            var productQuant = this.newOrders.productQuant;
            product.productQuant = productQuant;
            this.newOrders.id = this.orders.id;
            if(Array.isArray(this.newOrders.productList)){
                this.newOrders.productList.push(product);
            }else {
                this.newOrders.productList=[];
                this.newOrders.productList.push(product);
            }
            //更新
            axios.post('/manage_sys_web/orders/addProduct4Orders',_this.newOrders)
                .then(function (response) {
                    _this.findAll(_this.pageInfo.pageNum,_this.pageInfo.pageSize);
                    if(response.data=="0")
                        layer.msg("库存不足，请联系仓库管理员添加库存", {time:1500, icon:5});
                    else if(response.data=="1")
                        layer.msg("添加成功", {time:1500, icon:6});
                    _this.newOrders.productList.pop();
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        delById:function (id) {
            var _this = this;
            var size = 0;
            layer.confirm("确认删除该订单？",  {icon: 3, title:'确认'}, function(cindex){
                layer.close(cindex);
                //先确认是否可以删除
                axios.get('/manage_sys_web/orders/findById',{
                    params:{
                        id:id
                    }
                }).then(function (response) {
                        size = response.data.orderQuant;
                    if(size>0){
                        //如果size>0说明订单下仍然关联商品，无法删除
                        layer.msg("该订单下存在商品无法删除该订单！", {time:1500, icon:5})
                    }else{
                        //size=0时可以删除商品
                        axios.get('/manage_sys_web/orders/delById',{
                                params:{
                                    id:id
                                }
                            }).then(function (response) {
                            _this.findAll(_this.pageInfo.pageNum,_this.pageInfo.pageSize);
                            }).catch(function (error) {
                                console.log(error);
                            });
                        }
                    });
            }, function(cindex){
                layer.close(cindex);
            });
        },
        delProudctOfOrders:function(index) {
            var _this = this;
            //封装数据
            var product = this.orders.productList[index];
            if(Array.isArray(this.newOrders.productList)){
                this.newOrders.productList.push(product);
            }else {
                this.newOrders.productList=[];
                this.newOrders.productList.push(product);
            }
            this.newOrders.id = this.orders.id;
            //确认窗口
            layer.confirm("确认删除此产品？",  {icon: 3, title:'确认'}, function(cindex){
                layer.close(cindex);
                //异步请求
                axios.post('/manage_sys_web/orders/delProductOfOrders',_this.newOrders)
                    .then(function (response) {
                        _this.findAll(_this.pageInfo.pageNum,_this.pageInfo.pageSize);
                        _this.newOrders.productList.pop();
                        layer.msg("操作成功", {time:1500, icon:6})
                        $('#order-edit').modal('hide');
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }, function(cindex){
                layer.close(cindex);
            });
        },
        delOrdersByGroup:function () {
            var _this = this;
            $.each($('input[name=ids]:checked'),function(){
                if(this.checked){
                    _this.ids.push($(this).val());
                }
            });
            layer.confirm("确认批量删除所选中的订单？",  {icon: 3, title:'确认'}, function(cindex){
                layer.close(cindex);
                for(var i = 0;i<_this.ids.length;i++){
                    //先确认是否可以删除
                    axios.get('/manage_sys_web/orders/findById',{
                        params:{
                            id:_this.ids[i]
                        }
                    }).then(function (response) {
                        size = response.data.orderQuant;
                        var params = response.config.params;
                        if(size>0){
                            //如果size>0说明订单下仍然关联商品，无法删除
                            layer.msg("id为:"+params.id+"的订单下存在商品无法删除该订单！", {time:1500, icon:5})
                        }else{
                            //size=0时可以删除商品
                            axios.get('/manage_sys_web/orders/delById',{
                                params:params
                            }).then(function (response) {
                                _this.findAll(_this.pageInfo.pageNum,_this.pageInfo.pageSize);
                            }).catch(function (error) {
                                console.log(error);
                            });
                        }
                        _this.ids = [];
                    });
                }
            }, function(cindex){
                layer.close(cindex);
            });
        },
        search:function() {
            var _this = this;
            axios.post('/manage_sys_web/orders/searchByOrderNum',{
                orderNum:_this.searchNum
            })
                .then(function (response) {
                    // handle success
                    _this.pageInfo = response.data;
                })
                .catch(function (error) {
                    // handle error
                    console.log(error);
                })
        },
        loadLoginUser:function () {
            var _this = this;
            axios.get('/manage_sys_web/users/getCurrentUser')
                .then(function (response) {
                    _this.currentUsername = response.data;
                    console.log(_this.currentUsername);
                })
                .catch(function (error) {
                    // handle error
                    console.log(error);
                })
        }
    },
    created:function () {
        this.findAll(1,5);
        this.loadLoginUser();
    }
})

$(document).ready(function() {
    // 选择框
    $(".select2").select2();

    // WYSIHTML5编辑器
    $(".textarea").wysihtml5({
        locale: 'zh-CN'
    });
});


// 设置激活菜单
function setSidebarActive(tagUri) {
    var liObj = $("#" + tagUri);
    if (liObj.length > 0) {
        liObj.parent().parent().addClass("active");
        liObj.addClass("active");
    }
}


$(document).ready(function() {
    // 激活导航位置
    setSidebarActive("admin-index");
});

// 列表按钮
$("#dataList td input[type='checkbox']").iCheck({
    checkboxClass: 'icheckbox_square-blue',
    increaseArea: '20%'
});
// 全选操作
$("#selall").click(function() {
    var clicks = $(this).is(':checked');
    if (!clicks) {
        $("#dataList td input[type='checkbox']").iCheck("uncheck");
    } else {
        $("#dataList td input[type='checkbox']").iCheck("check");
    }
    $(this).data("clicks", !clicks);
});