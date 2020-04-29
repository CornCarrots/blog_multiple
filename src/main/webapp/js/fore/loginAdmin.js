$(
    function () {
        var bean = {
            uri: "/foreLoginAdmin",
            imgURL:'',
            flag: false,
            manager:{name:'',password:''},
            email: ''
        };
        var loginVue = new Vue(
            {
                el:".main",
                data:bean,
                mounted:function () {
                    Vue.nextTick(function () {
                        $("#loginForm").validationEngine(
                            {
                                promptPosition: 'topRight',
                                showArrow: true
                            }
                        );
                    });
                    },
                methods:{
                    cleanName: function () {
                        cleanName(loginVue);
                    },
                    togglePass: function () {
                        togglePass(loginVue);
                    },
                    loginStore: function () {
                        if(!$("#loginForm").validationEngine("validate"))
                            return false;
                        var url = getPath() + this.uri;
                        axios.post(url, loginVue.manager).then(function (value) {
                            console.log(value)
                            if (value.data == "success")
                                location.href = getPath() + "/admin";
                            if (value.data == "manager404")
                                $("#nameField").validationEngine("showPrompt", "没有此管理员", "error");
                            if (value.data == "fail")
                                $("#passField").validationEngine("showPrompt", "密码错误", "error");
                        })
                    },
                    forgetButton: function () {
                        var random;
                        $.confirm({
                                title: '请稍后',
                                content: '<div class="ball"></div>\n' +
                                    '<div class="ball1"></div>',
                                buttons: {
                                    ok: {
                                        text: '确定',
                                        btnClass: 'btn-blue'
                                    },
                                    formSubmit: {
                                        text: '确定',
                                        btnClass: 'btn-blue',
                                        action: function () {
                                            return changePass(loginVue, random);
                                        }
                                    },
                                    '取消': function () {
                                    }
                                },
                                onOpenBefore: function () {
                                    var self = this;
                                    self.buttons['formSubmit'].hide();
                                    self.buttons['ok'].hide();
                                    self.buttons['取消'].hide();
                                },
                                onContentReady: function () {
                                    random = getRandom(this, loginVue);
                                }
                            }
                        );
                    }

                }
            }
        );
    }
);