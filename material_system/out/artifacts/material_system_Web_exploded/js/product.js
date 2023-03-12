/*private Integer id; //主键
    private String productNum;
    private String productName;
    private double productPrice;
    private String productDesc;
    private Integer productQua;
    private Integer productStatus;//状态(0充足 1缺货)
    vm.$axios.get(url,{
        params: params,
        {before(request) {
            loadingIndex = layer.msg('处理中,请稍后...', {icon: 16});
        }}
    })；
    loadingIndex = layer.msg('正在删除...', {icon: 16});
    layer.msg(result.msg, {time:1500, icon:6});
*/

new Vue({
    el:"#product",
    data:{
        newProduct:{
            id:"",
            productNum:"",
            productName:"",
            productPrice:"",
            productDesc:"",
            productStatus:"",
            productStatusStr:"",
            productQuant:""
        },
        product:{
            id:"",
            productNum:"",
            productName:"",
            productPrice:"",
            productDesc:"",
            productStatus:"",
            productStatusStr:"",
            productQuant:""
        },
        pageInfo:{},
        ids:[],
        searchName:"",
        currentUsername:"未登录",
        productIndex:"",
        productList:[]
    },
    methods:{
        choseProduct:function(){
              var chosenProduct = this.productList[this.productIndex];
              this.newProduct.productName = chosenProduct.productName;
              this.newProduct.productNum = chosenProduct.productNum;
              this.newProduct.productPrice = chosenProduct.productPrice;
              this.newProduct.productStatus = chosenProduct.productStatus;
        },
        loadProducts:function(){
            var _this = this;
            axios.get('/manage_sys_web/stocks/findProductList')
                .then(function (response) {
                    // handle success
                    _this.productList = response.data;
                })
                .catch(function (error) {
                    // handle error
                    console.log(error);
                })
        },
        findAll:function (page,size) {
            var _this = this;
            axios.get('/manage_sys_web/product/findAll?page='+page+'&size='+size)
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
            axios.get('/manage_sys_web/product/findById',{
                    params: {
                        id:id
                    }
                })
                .then(function (response) {
                    // handle success
                    _this.product = response.data;
                    $('#edit-product').modal('show');
                })
                .catch(function (error) {
                    // handle error
                    console.log(error);
                })
        },
        addProduct:function () {
            var _this = this;
            console.log(_this.newProduct);
            axios.post('/manage_sys_web/product/addProduct', _this.newProduct)
                .then(function (response) {
                    _this.findAll(_this.pageInfo.pageNum,_this.pageInfo.pageSize);
                    Object.keys(_this.newProduct).forEach(key=>{_this.newProduct[key]=''})
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        update:function () {
            var _this = this;
            axios.post('/manage_sys_web/product/update', _this.product)
                .then(function (response) {
                    _this.findAll(_this.pageInfo.pageNum,_this.pageInfo.pageSize);
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        delById:function (id) {
            var _this = this;
            layer.confirm("确认删除该产品？",  {icon: 3, title:'确认'}, function(cindex){
                layer.close(cindex);
                console.log(id);
                axios.post('/manage_sys_web/product/delById', {id: id})
                    .then(function (response) {
                        _this.findAll(_this.pageInfo.pageNum,_this.pageInfo.pageSize);
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }, function(cindex){
                layer.close(cindex);
            });
        },
        delProducts:function () {
            var _this = this;
            $.each($('input[name=ids]:checked'),function(){
                if(this.checked){
                    _this.ids.push($(this).val());
                }
            });
            layer.confirm("确认批量删除所选中的产品？",  {icon: 3, title:'确认'}, function(cindex){
                layer.close(cindex);
                axios.post('/manage_sys_web/product/delProducts',_this.ids)
                    .then(function (response) {
                        _this.findAll(_this.pageInfo.pageNum,_this.pageInfo.pageSize);
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }, function(cindex){
                layer.close(cindex);
            });
        },
        search:function() {
            var _this = this;
            axios.post('/manage_sys_web/product/searchByProName',{
                    productName:_this.searchName,
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
        this.loadLoginUser();
        this.findAll(1,5);
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

//product'js
$(document).ready(function() {
    // 激活导航位置
    setSidebarActive("admin-index");
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
});