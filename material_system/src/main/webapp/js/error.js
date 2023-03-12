new Vue({
    el:"#error",
    data:{
        currentUsername:"未登录",
    },
    methods:{
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
        this.loadLoginUser();
    }
})
