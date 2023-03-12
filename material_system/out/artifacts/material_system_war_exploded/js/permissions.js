new Vue({
    el:"#permission",
    data:{
        currentUsername:"未登录",
        permissionList:[],
        searchName:"",
        permission:{
            id:"",
            permissionName:"",
            url:""
        },
        newPermission:{
            id:"",
            permissionName:"",
            url:""
        },
        ids:[]
    },
    methods:{
        deletePermission:function (id) {
            var _this = this;
            layer.confirm("确认删除该权限信息？",  {icon: 3, title:'确认'}, function(cindex){
                layer.close(cindex);
                console.log(id);
                axios.get('/material_system/permissions/deletePermission?id='+id)
                    .then(function (response) {
                        _this.findAll();
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }, function(cindex){
                layer.close(cindex);
            });
        },
        deletePermissions:function () {
            var _this = this;
            $.each($('input[name=ids]:checked'),function(){
                if(this.checked){
                    _this.ids.push($(this).val());
                }
            });
            layer.confirm("确认批量删除所选中的记录？",  {icon: 3, title:'确认'}, function(cindex){
                layer.close(cindex);
                axios.post('/material_system/permissions/deletePermissions',_this.ids)
                    .then(function (response) {
                        _this.findAll();
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }, function(cindex){
                layer.close(cindex);
            });
        },
        search:function(){
          var _this = this;
          axios.get('/material_system/permissions/findAllByPermissionName',{
              params:{
                  permissionName:_this.searchName
              }
          }).then(function (response) {
              _this.permissionList = response.data;
          })
              .catch(function (error) {
                  console.log(error);
              })
        },
        addPermission:function(){
          var _this = this;
          axios.post('/material_system/permissions/addPermission',_this.newPermission)
              .then(function (response) {
                _this.findAll();
              })
              .catch(function (error) {
                console.log(error);
              })
        },
        findAll:function(){
          var _this = this;
          axios.get('/material_system/permissions/findAll')
              .then(function (response) {
                _this.permissionList = response.data;
              })
              .catch(function (error) {
                  // handle error
                  console.log(error);
              })
        },
        loadLoginUser:function () {
            var _this = this;
            axios.get('/material_system/users/getCurrentUser')
                .then(function (response) {
                    _this.currentUsername = response.data;
                })
                .catch(function (error) {
                    // handle error
                    console.log(error);
                })
        }
    },
    created:function () {
        this.findAll();
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