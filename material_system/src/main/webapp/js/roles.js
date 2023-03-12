new Vue({
    el:"#role",
    data:{
        currentUsername:"未登录",
        roleList:[],
        searchName:"",
        role:{
            id:"",
            roleName:"",
            roleDesc:"",
            permissions:[]
        },
        newRole:{
            id:"",
            roleName:"",
            roleDesc:"",
            permissions:[],
            permissionIds:[]
        },
        permissionList:[]
    },
    methods:{
        addPermissions:function(){
            var _this = this;
            $.each($('input[name=ids]:checked'),function(){
                if(this.checked){
                    _this.newRole.permissionIds.push($(this).val());
                }
            });
            layer.confirm("是否为此用户添加选中权限？",  {icon: 3, title:'确认'}, function(cindex){
                layer.close(cindex);
                axios.post('/material_system/roles/addPermissions', _this.newRole)
                    .then(function (response) {
                        _this.findAll();
                        _this.newRole.permissionIds = [];
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }, function(cindex){
                _this.newRole.permissionIds = [];
                layer.close(cindex);
            });
        },
        loadPermissionsById:function(id){
            var _this = this;
            this.newRole.id = id;
            $("#permissionList td input[type='checkbox']").iCheck("uncheck");
            $("#permissionAll").iCheck("uncheck");

            axios.get('/material_system/roles/loadPermissionsById',{
                params:{
                    id:id
                }
            })
                .then(function (response) {
                    _this.permissionList = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        search:function(){
          var _this = this;
          axios.get('/material_system/roles/findAllByRoleName',{
              params:{
                  roleName:_this.searchName
              }
          }).then(function (response) {
              _this.roleList = response.data;
          })
              .catch(function (error) {
                  console.log(error);
              })
        },
        getDetail:function(id){
          var _this = this;
          axios.get('/material_system/roles/getDetail?id='+id)
              .then(function (response) {
                  _this.role = response.data;
              })
              .catch(function (error) {
                console.log(error);
              })
        },
        addRole:function(){
          var _this = this;
          axios.post('/material_system/roles/addRole',_this.newRole)
              .then(function (response) {
                _this.findAll();
              })
              .catch(function (error) {
                console.log(error);
              })
        },
        findAll:function(){
          var _this = this;
          axios.get('/material_system/roles/findAll')
              .then(function (response) {
                    _this.roleList = response.data;
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

    // 列表按钮
    $("#permissionList td input[type='checkbox']").iCheck({
        checkboxClass: 'icheckbox_square-blue',
        increaseArea: '20%'
    });
    // 全选操作
    $("#permissionAll").click(function() {
        var clicks = $(this).is(':checked');
        if (!clicks) {
            $("#permissionList td input[type='checkbox']").iCheck("uncheck");
        } else {
            $("#permissionList td input[type='checkbox']").iCheck("check");
        }
        $(this).data("clicks", !clicks);
    });
});