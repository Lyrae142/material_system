new Vue({
    el:"#syslog",
    data:{
        currentUsername:"未登录",
        pageInfo:{},
        sysEmail:""
    },
    methods:{
        getEmail:function(){
            var _this = this;
            axios.post('/manage_sys_web/sysLog/getEmail')
                .then(function (response) {
                    _this.sysEmail = response.data;
                })
                .catch(function (error) {
                    // handle error
                    console.log(error);
                })
        },
        saveEmail:function(){
            var _this = this;
            axios.get('/manage_sys_web/sysLog/saveEmail',{
                params:{
                    email:_this.sysEmail
                }
            })
                .then(function (response) {
                    layer.msg("设置成功", {time:1500, icon:6})
                })
                .catch(function (error) {
                    // handle error
                    console.log(error);
                })
        },
        findAll:function(page,size){
          var _this = this;
          axios.get('/manage_sys_web/sysLog/findAll?page='+page+"&size="+size)
              .then(function (response) {
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