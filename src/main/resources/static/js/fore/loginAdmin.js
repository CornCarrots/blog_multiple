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
                            if (value.code == "500512") {
                                location.href = getPath() + "/admin";
                            }
                            else if (value.code == "500513") {
                                $("#nameField").validationEngine("showPrompt", "没有此管理员", "error");
                            }
                            else if (value.code == "500508") {
                                $("#passField").validationEngine("showPrompt", "密码错误", "error");
                            }else {
                                $.alert("抱歉!" + value.msg);
                            }
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
                                            var key = this.$content.find('.key').val();
                                            var pass = this.$content.find('.pass').val();
                                            var res = true;
                                            if (!key || !pass) {
                                                $.alert('请输入一个有效的值');
                                                return false;
                                            }
                                            var bean = {key: key, email: loginVue.email, pass: pass};
                                            var url = getPath() + "/forgetManager?timeStamp=" + new Date().getDate();
                                            $.ajax({
                                                type:"POST",
                                                url:url,
                                                data:bean,
                                                async:false,
                                                success:function (value) {
                                                    if (value.code == '0') {
                                                        $.dialog({
                                                            title: '恭喜您,密码修改成功!',
                                                            content: '快去登录吧',
                                                            theme: 'modern',
                                                            icon: 'fa fa-smile-o'
                                                        });
                                                    }
                                                    else if (value.code == '500416') {
                                                        $.alert({
                                                            title: '抱歉!',
                                                            content: '验证码错误',
                                                            theme: 'modern',
                                                            icon: 'fa fa-close'
                                                        });
                                                        res = false;
                                                    }else {
                                                        $.alert({
                                                            title: '抱歉!',
                                                            content: value.msg,
                                                            theme: 'modern',
                                                            icon: 'fa fa-close'
                                                        });
                                                        res = false;
                                                    }
                                                }
                                            });
                                            return res;
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
                                    var self = e;
                                    var url = getPath() + "/forgetManager?email=" + vue.email + "&timeStamp=" + new Date().getDate();
                                    axios.get(url).then(
                                        function (value) {
                                            random = value.data;
                                            if (value.code == '500417') {
                                                $("#forgetModel").modal("hide");
                                                self.setContent(
                                                    '<div class="form-group">' +
                                                    '<label>验证码已发送到您的邮箱，请查看</label>' +
                                                    '<input type="text" placeholder="请输入验证码" class="key form-control"/>' +
                                                    '</div>' +
                                                    '<div class="form-group">' +
                                                    '<label>请输入您要修改的密码</label>' +
                                                    '<input type="text" placeholder="请输入密码" class="pass form-control"/>' +
                                                    '</div>'
                                                );
                                                self.setTitle("修改密码");
                                                self.buttons['formSubmit'].show();
                                                self.buttons['取消'].show();
                                            }
                                            else if (value.code == '500507') {
                                                self.setContent(
                                                    '<div>该邮箱没有绑定管理员, 请重新输入' +
                                                    '</div>'
                                                );
                                                self.buttons['ok'].show();
                                                self.buttons['取消'].show();
                                            }
                                            else {
                                                self.setContent(
                                                    '<div>抱歉!' +
                                                    value.msg +
                                                    '</div>'
                                                );
                                                self.buttons['ok'].show();
                                                self.buttons['取消'].show();
                                            }
                                        }
                                    );
                                }
                            }
                        );
                    }

                }
            }
        );
    }
);