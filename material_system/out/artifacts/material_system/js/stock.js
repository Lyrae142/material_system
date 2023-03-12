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
    el:"#stock",
    data:{
        newStock:{
            id:"",
            productNum:"",
            productName:"",
            productPrice:"",
            executeTimeStr:"",
            username:"",
            productQuant:"",
            processObj:""
        },
        stock:{},
        pageInfo:{},
        ids:[],
        searchName:"",
        currentUsername:"未登录"
    },
    methods:{
        openAdd:function(id){
          this.newStock.id = id;
        },
        addQuant:function(){
            var _this = this;
            axios.post('/manage_sys_web/stocks/addQuant', _this.newStock)
                .then(function (response) {
                    _this.findAll(_this.pageInfo.pageNum,_this.pageInfo.pageSize);
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        findAll:function (page,size) {
            var _this = this;
            axios.get('/manage_sys_web/stocks/findAll?page='+page+'&size='+size)
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
            axios.get('/manage_sys_web/stocks/findById',{
                    params: {
                        id:id
                    }
                })
                .then(function (response) {
                    // handle success
                    _this.stock = response.data;
                    $('#edit-stock').modal('show');
                })
                .catch(function (error) {
                    // handle error
                    console.log(error);
                })
        },
        addStock:function () {
            var _this = this;
            console.log(_this.newProduct);
            axios.post('/manage_sys_web/stocks/addStock', _this.newStock)
                .then(function (response) {
                    _this.findAll(_this.pageInfo.pageNum,_this.pageInfo.pageSize);
                    if(response.data=="0")
                        layer.msg("发生意料之外的错误，请稍后重试", {time:1500, icon:3});
                    else if(response.data=="1")
                        layer.msg("添加成功", {time:1500, icon:6});
                    else if(response.data=="2")
                        layer.msg("添加失败:已存在相同编号或名称的商品", {time:1500, icon:5});
                    Object.keys(_this.newStock).forEach(key=>{_this.newStock[key]=''})
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        update:function () {
            var _this = this;
            axios.post('/manage_sys_web/stocks/update', _this.stock)
                .then(function (response) {
                    _this.findAll(_this.pageInfo.pageNum,_this.pageInfo.pageSize);
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        delById:function (id) {
            var _this = this;
            layer.confirm("确认删除该库存信息？",  {icon: 3, title:'确认'}, function(cindex){
                layer.close(cindex);
                console.log(id);
                axios.post('/manage_sys_web/stocks/delById', {id: id})
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
        delStocks:function () {
            var _this = this;
            $.each($('input[name=ids]:checked'),function(){
                if(this.checked){
                    _this.ids.push($(this).val());
                }
            });
            layer.confirm("确认批量删除所选中的库存信息？",  {icon: 3, title:'确认'}, function(cindex){
                layer.close(cindex);
                axios.post('/manage_sys_web/stocks/delStocks',_this.ids)
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
            axios.post('/manage_sys_web/stocks/searchByProName',{
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