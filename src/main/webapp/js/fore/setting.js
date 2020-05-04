$(function () {
    var bean = {
        uri: "/foreSetting",
        user: {
            id: 0,
            name: '',
            nickName: '',
            password: '',
            salt: '',
            account: 0,
            sex: 0,
            registerDate: null,
            loginDate: null,
            mobile: '',
            email: '',
            mid: 0,
            score: 0,
            status: 0,
            img: ''
        },
        image: 0,
        password: '',
        imgs: []
    };
    var userVue = new Vue(
        {
            el: ".main",
            data: bean,
            mounted: function () {
                this.get();
            },
            methods: {
                get: function () {
                    // var id = getUrlParms('uid');
                    // if (id == undefined)
                    //     id = 0;
                    var url = getPath() + this.uri + "?timeStamp=" + new Date().getDate();
                    axios.get(url).then(
                        function (value) {
                            if (value.code != '0') {
                                location.href = getPath() + "/error";
                            }
                            userVue.user = value.data.user;
                            var operation = new Array();
                            for (var i = 0; i < value.data.num; i++) {
                                operation.push(i + 1);
                                if ((i + 1) % 6 == 0 || i == value.data - 1) {
                                    userVue.imgs.push(operation);
                                    operation = new Array();
                                }
                            }
                        }
                    );
                    Vue.nextTick(function () {
                        $("#modifyForm").validationEngine(
                            {
                                promptPosition: 'centerRight',
                                showArrow: true
                            }
                        );
                        valControls($("#modifyForm"));
                    });
                },
                getImage: function (img) {
                    var url = getPath() + img;
                    return url;
                },
                getPhoto: function (i) {
                    if (i == 0)
                        return;
                    return getPath() + "/image/profile_user/" + i + ".jpg";
                },
                getUrl: function (key) {
                    return getPath() + key;
                },
                modify: function () {
                    if (controlId.length > 0) {
                        alertinfo(0);
                        return false;
                    }
                    if (!$("#modifyForm ").validationEngine("validate"))
                        return false;  //验证没有使用ajax验证的控件(不是ajax验证的字段是可以正常验证的, 不通过就返回)
                    var url = getPath() + this.uri + "/" + userVue.user.id + "?timeStamp=" + new Date().getDate();
                    axios.put(url, userVue.user).then(function (value) {
                        if (value.code == '500420') {
                            $.dialog({
                                title: '恭喜您!',
                                content: '修改成功啦',
                                theme: 'modern',
                                icon: 'fa fa-smile-o'
                            });
                        }
                        else {
                            $.alert("抱歉!" + value.msg);
                        }
                    });
                },
                reset: function () {
                    this.get();
                },
                selectImg: function () {
                    $("#addMessageModel").modal("show");
                },
                select: function (i) {
                    // console.log(i)
                    this.image = i;
                    $(".successImg").hide();
                    $("#successImg" + i).show();
                },
                upload: function () {
                    var url = getPath() + this.uri + "?timeStamp=" + new Date().getDate();
                    axios.post(url, {num:this.image}).then(
                        function (value) {
                            $("#addMessageModel").modal("hide");
                            if (value.code == '500420') {
                                $.dialog({
                                    title: '恭喜您!',
                                    content: '修改成功啦',
                                    theme: 'modern',
                                    icon: 'fa fa-smile-o',
                                    onDestroy: function () {
                                        // before the modal is hidden.
                                        location.reload();
                                    }
                                });
                            }
                            else {
                                $.alert("抱歉!" + value.msg);
                            }
                        }
                    );
                },
                closeButton: function () {
                    this.image = 0;
                }
            }
        }
    );

});