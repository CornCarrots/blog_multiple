$(
    function () {
        var bean = {
            uri: "/foreLoginUser",
            flag: false,
            user: {name: '', password: ''},
            email: ''
        };
        var loginVue = new Vue(
            {
                el: ".main",
                data: bean,
                mounted: function () {
                    Vue.nextTick(function () {
                        $("#loginForm").validationEngine(
                            {
                                promptPosition: 'topRight',
                                showArrow: true
                            }
                        );
                    });
                },
                methods: {
                    cleanName: function () {
                        cleanName(loginVue);
                    },
                    togglePass: function () {
                        togglePass(loginVue);
                    },
                    loginUser: function () {
                        if (!$("#loginForm").validationEngine("validate"))
                            return false;
                        var url = getPath() + "/foreLoginUser" + "?timeStamp=" + new Date().getTime();
                        axios.post(url, loginVue.user).then(function (value) {
                            if (value.code == "500510") {
                                location.href = getPath() + "/user";
                            } else if (value.code == "500507") {
                                $("#nameField").validationEngine("showPrompt", "没有此用户", "error");
                            } else if (value.code == "500508") {
                                $("#passField").validationEngine("showPrompt", "密码错误", "error");
                            }else {
                                $.alert("抱歉!" + value.msg);
                            }
                        })
                    },
                    forgetButton: function () {
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
                                            return changePass(loginVue);
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
                                    getRandom(this, loginVue);
                                }
                            }
                        );
                    }
                }
            });
    }
)
;