new Vue({
    el:"#user",
    data:{
        currentUsername:"未登录",
        userList:[],
        searchName:"",
        userInfo:{
            id:"",
            username:"",
            email:"",
            phoneNum:"",
            status:"",
            statusStr:"",
            roles:[]
        },
        newUserInfo:{
            id:"",
            username:"",
            password:"",
            email:"",
            phoneNum:"",
            status:"",
            statusStr:"",
            roles:[],
            roleIds:[]
        },
        roleList:[],
    },
    methods:{
        addRoles:function(){
            var _this = this;
            $.each($('input[name=ids]:checked'),function(){
                if(this.checked){
                    _this.newUserInfo.roleIds.push($(this).val());
                }
            });
            layer.confirm("是否为此用户添加选中角色？",  {icon: 3, title:'确认'}, function(cindex){
                layer.close(cindex);
                axios.post('/material_system/users/addRoles', _this.newUserInfo)
                    .then(function (response) {
                        _this.findAll();
                        _this.newUserInfo.roleIds = [];
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }, function(cindex){
                _this.newUserInfo.roleIds = [];
                layer.close(cindex);
            });
        },
        loadRolesById:function(id){
          var _this = this;
          this.newUserInfo.id = id;
          $("#roleList td input[type='checkbox']").iCheck("uncheck");
          $("#roleAll").iCheck("uncheck");

            axios.get('/material_system/users/loadRolesById',{
                params:{
                    id:id
                }
            })
                .then(function (response) {
                    _this.roleList = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        search:function(){
          var _this = this;
          axios.get('/material_system/users/findAllByUsername',{
              params:{
                  username:_this.searchName
              }
          }).then(function (response) {
              _this.userList = response.data;
          })
              .catch(function (error) {
                  console.log(error);
              })
        },
        shutdown:function(id){
            var _this = this;
            axios.get('/material_system/users/shutdown',{
                params:{
                    id:id
                }
            })
                .then(function (response) {
                    _this.findAll();
                })
                .catch(function (error) {
                    console.log(error);
                })
        },
        authentic:function(id){
          var _this = this;
          axios.get('/material_system/users/authentic',{
              params:{
                  id:id
              }
          })
              .then(function (response) {
                _this.findAll();
              })
              .catch(function (error) {
                  console.log(error);
              })
        },
        getDetail:function(id){
          var _this = this;
          axios.get('/material_system/users/getDetail?id='+id)
              .then(function (response) {
                  _this.userInfo = response.data;
              })
              .catch(function (error) {
                console.log(error);
              })
        },
        addUser:function(){
          var _this = this;
          axios.post('/material_system/users/addUser',_this.newUserInfo)
              .then(function (response) {
                _this.findAll();
              })
              .catch(function (error) {
                console.log(error);
              })
        },
        findAll:function(){
          var _this = this;
          axios.get('/material_system/users/findAll')
              .then(function (response) {
                  _this.userList = response.data;
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
    $("#roleList td input[type='checkbox']").iCheck({
        checkboxClass: 'icheckbox_square-blue',
        increaseArea: '20%'
    });
    // 全选操作
    $("#roleAll").click(function() {
        var clicks = $(this).is(':checked');
        if (!clicks) {
            $("#roleList td input[type='checkbox']").iCheck("uncheck");
        } else {
            $("#roleList td input[type='checkbox']").iCheck("check");
        }
        $(this).data("clicks", !clicks);
    });
});