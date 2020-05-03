$(
    function () {
        var option = {
            showTab: true,
            animation: 'slide',
            icons: [{
                name: "贴吧表情",
                path: getPath() + "/image/emoji/tieba/",
                maxNum: 50,
                file: ".jpg",
                placeholder: "[tieba_{alias}]"
            }, {
                name: "QQ高清",
                path: getPath() + "/image/emoji/qq/",
                maxNum: 91,
                excludeNums: [41, 45, 54],
                file: ".gif",
                placeholder: "[qq_{alias}]"
            }, {
                name: "emoji高清",
                path: getPath() + "/image/emoji/emoji/",
                maxNum: 84,
                file: ".png",
                placeholder: "[emoji_{alias}]"
            }],
            button: '#smile1',
            position: 'bottomLeft'
        };
        var bean = {
            uri: "/foreArticle",
            article: {user: {nickName: ''}, category: {parent: {name: '', id: 0}, name: '', id: 0}},
            comments: [],
            pages: [],
            // page1: -1,
            // page2: -1,
            user: {},
            comment: {id: 0, text: '', createDate: null, status: 0, pid: 0, uid: 0, aid: 0, like: 0},
            addComment: {id: 0, text: '', createDate: null, status: 2, pid: 0, uid: 0, aid: 0, like: 0},
            flag: true,
            email: '',
            start: 0,
            key: '',
            has: false
        };
        var helpVue = new Vue(
            {
                el: ".main",
                data: bean,
                mounted: function () {
                    this.list(0);
                },
                methods: {
                    list: function (start) {
                        this.start = start;
                        var id = getUrlParms("aid");
                        var url = getPath() + this.uri + "/" + id + "?start=" + start + "&timeStamp=" + new Date().getTime();
                        axios.get(url).then(
                            function (value) {
                                if (value.code != '0') {
                                    location.href = getPath() + "/error";
                                }
                                helpVue.has = value.data.has;
                                helpVue.article = value.data.article;
                                helpVue.pages = value.data.pages;
                                helpVue.comments = value.data.pages.content;
                                helpVue.key = value.data.key;
                                if (value.data.user != null)
                                    helpVue.user = value.data.user;
                                if (value.data.pages.content.length > 0) {
                                    $(".listComments").show();
                                    $(".comments404").hide();
                                    $(".pageDiv").show();
                                }
                                else {
                                    $(".listComments").hide();
                                    $(".comments404").show();
                                    $(".pageDiv").hide();
                                }
                                Vue.nextTick(function () {
                                    setTimeout(
                                        function () {
                                            $("#mycontent").emoji(option);
                                        }, 500);
                                });
                            }
                        );
                    },
                    jump: function (page) {
                        jump(page, helpVue);
                    },
                    jumpByNumber: function (start) {
                        jumpByNumber(start, helpVue);
                    },
                    startArticle: function () {
                        var login = checkLogin();
                        if (!login)
                            return;
                        var url = getPath() + "/startArticle/?timeStamp=" + new Date().getTime();
                        axios.post(url, helpVue.article).then(function (value) {
                            if (value.code == '500202') {
                                $.alert(
                                    {
                                        title: '恭喜你!',
                                        content: '已收藏',
                                        theme: 'modern',
                                        icon: 'fa fa-smile-o',
                                        buttons: {
                                            ok: {
                                                action: function () {
                                                    helpVue.list(0);
                                                }
                                            }
                                        }
                                    }
                                );
                            }
                            else if (value.code == '500203') {
                                $.alert(
                                    {
                                        title: '恭喜你!',
                                        content: '取消收藏成功',
                                        theme: 'modern',
                                        icon: 'fa fa-smile-o',
                                        buttons: {
                                            ok: {
                                                action: function () {
                                                    helpVue.list(0);
                                                }
                                            }
                                        }
                                    }
                                );
                            }
                            else {
                                $.alert(
                                    {
                                        title: '抱歉!',
                                        content: ''+ value.msg +'，请重试',
                                        theme: 'modern',
                                        icon: 'fa fa-close-o'

                                    }
                                );
                            }
                        });
                    },
                    deleteButton:
                        function (id) {
                            $.confirm({
                                title: '确定吗？',
                                content: '您正在删除评论',
                                theme: 'modern',
                                icon: 'fa fa-question',
                                buttons: {
                                    '确认': function () {
                                        var url = getPath() + "/deleteComment/" + id + "?timeStamp=" + new Date().getTime();
                                        axios.delete(url).then(function (value) {
                                            console.log(value)
                                            if (0 != value.code) {
                                                $.alert(value.msg + ',请重试!');
                                            }
                                            else {
                                                $.alert('成功删除!');
                                                helpVue.list(0);
                                            }
                                        });
                                    },
                                    '取消': {
                                        action: function () {
                                            $.alert('已取消!');
                                        }
                                    }
                                }
                            });
                        },
                    likeArticle: function () {
                        var login = checkLogin();
                        if (!login)
                            return;
                        var url = getPath() + "/likeArticle/?timeStamp=" + new Date().getTime();
                        axios.post(url, helpVue.article).then(function (value) {
                            console.log(value)
                            if (value.code == '500206') {
                                $.alert(
                                    {
                                        title: '恭喜你!',
                                        content: '已点赞',
                                        theme: 'modern',
                                        icon: 'fa fa-smile-o',
                                        buttons: {
                                            ok: {
                                                action: function () {
                                                    helpVue.list(0);
                                                }
                                            }
                                        }
                                    }
                                );
                            }
                            else if (value.code == '500207') {
                                $.alert(
                                    {
                                        title: '恭喜你!',
                                        content: '取消赞成功',
                                        theme: 'modern',
                                        icon: 'fa fa-smile-o',
                                        buttons: {
                                            ok: {
                                                action: function () {
                                                    helpVue.list(0);
                                                }
                                            }
                                        }
                                    }
                                );
                            }
                            else {
                                $.alert(
                                    {
                                        title: '抱歉!',
                                        content: '' + value.msg + '，请重试',
                                        theme: 'modern',
                                        icon: 'fa fa-close-o'

                                    }
                                );
                            }
                        });
                    },
                    likeComment: function (comment) {
                        var login = checkLogin();
                        if (!login)
                            return;
                        var url = getPath() + "/likeComment/?timeStamp=" + new Date().getTime();
                        axios.post(url, comment).then(function (value) {
                            helpVue.list(helpVue.start);
                        });
                    },
                    getImage: function (img) {
                        var url = getPath() + img;
                        return url;
                    },
                    commitButton: function (e) {
                        var login = checkLogin();
                        if (!login)
                            return;
                        helpVue.comment.text = $("#mycontent").val();
                        helpVue.comment.text = replace_em(helpVue.comment.text);
                        if (helpVue.comment.text.length == 0) {
                            $.alert("评论内容不可为空!");
                            return;
                        }
                        helpVue.comment.pid = $(e.target).attr("pid");
                        helpVue.comment.aid = helpVue.article.id;
                        var url = getPath() + "/foreCommitComment" + "?timeStamp=" + new Date().getTime();
                        axios.post(url, helpVue.comment).then(
                            function (value) {
                                helpVue.comment = {id: 0, text: '', createDate: null, status: 0, pid: 0, uid: 0, aid: 0, like: 0};
                                $("#mycontent").val("");
                                if (value.code == 0) {
                                    $.alert(
                                        {
                                            title: '恭喜你!',
                                            content: '评论成功',
                                            theme: 'modern',
                                            icon: 'fa fa-smile-o',
                                            buttons: {
                                                ok: {
                                                    action: function () {
                                                        $("#addMessageModel").modal("hide");
                                                        helpVue.list(0);
                                                    }
                                                }
                                            }
                                        }
                                    );
                                }
                                else {
                                    $.alert(
                                        {
                                            title: '抱歉!',
                                            content: '' + value.msg + '，请重试',
                                            theme: 'modern',
                                            icon: 'fa fa-close-o'

                                        }
                                    );
                                }
                            }
                        );
                    },
                    addButton: function (e) {
                        var login = checkLogin();
                        if (!login)
                            return;
                        helpVue.addComment = {
                            id: 0,
                            text: '',
                            createDate: null,
                            status: 2,
                            pid: 0,
                            uid: 0,
                            aid: 0,
                            like: 0
                        };
                        helpVue.addComment.pid = $(e.target).attr("pid");
                        helpVue.addComment.aid = helpVue.article.id;
                        // console.log($(e.target).html())
                        // $("#addMessageModel").modal("show");
                        $(".allDiv").hide();
                        $(e.target).parent().parent().parent().next().next().toggle();
                    },
                    sendButton: function () {
                        // helpVue.comment.text = editor.html();
                        if (helpVue.addComment.text.length == 0) {
                            $.alert("评论内容不可为空!");
                            return;
                        }
                        helpVue.addComment.text = replace_em(helpVue.addComment.text);
                        var url = getPath() + "/foreCommitComment" + "?timeStamp=" + new Date().getTime();
                        axios.post(url, helpVue.addComment).then(
                            function (value) {
                                helpVue.addComment = {
                                    id: 0,
                                    text: '',
                                    createDate: null,
                                    status: 2,
                                    pid: 0,
                                    uid: 0,
                                    aid: 0,
                                    like: 0
                                };
                                if (value.status == 0) {
                                    $.alert(
                                        {
                                            title: '恭喜你!',
                                            content: '评论成功',
                                            theme: 'modern',
                                            icon: 'fa fa-smile-o',
                                            buttons: {
                                                ok: {
                                                    action: function () {
                                                        $(".allDiv").hide();
                                                        helpVue.list(helpVue.start);
                                                    }
                                                }
                                            }
                                        }
                                    );
                                }
                                else {
                                    $.alert(
                                        {
                                            title: '抱歉!',
                                            content: '' + value.msg + '，请重试',
                                            theme: 'modern',
                                            icon: 'fa fa-close-o'

                                        }
                                    );
                                }

                            }
                        );
                    }
                    ,
                    closeButton: function () {
                        helpVue.addComment = {
                            id: 0,
                            text: '',
                            createDate: null,
                            status: 0,
                            pid: 0,
                            uid: 0,
                            aid: 0,
                            like: 0
                        };
                    },
                    loginUser: function () {
                        loginUser(helpVue);
                    },
                    cleanName: function () {
                        cleanName(helpVue);
                    },
                    togglePass: function () {
                        togglePass(helpVue);
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
                                            return changePass(helpVue);
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
                                    getRandom(this, helpVue);
                                }
                            }
                        );
                    },
                    addChicken: function () {
                        var url = getPath() + "/addChicken/" + helpVue.article.uid + "?timeStamp=" + new Date().getTime();
                        axios.post(url).then(function (value) {
                            if (value.code == '500221') {
                                $.alert({
                                        title: '谢谢你!',
                                        content: '赞赏成功',
                                        theme: 'modern',
                                        icon: 'fa fa-smile-o'
                                    }
                                );
                            }else {
                                $.alert(
                                    {
                                        title: '抱歉!',
                                        content: '' + value.msg + '，请重试',
                                        theme: 'modern',
                                        icon: 'fa fa-close-o'

                                    }
                                );
                            }
                        });

                        // $("#chickenModel").modal("show");
                        // $.dialog({
                        //     title: '可以为我加个鸡腿吗？',
                        //     content: '<div align="center" class="payDIV">\n' +
                        //         '    <h5>服务器以及网站维护需要一点费用，如果可以的话，扫描一下二维码支持一下站长，帮我加个鸡腿吧！</h5>\n' +
                        //         '    <img src="image/site/pay1.jpg" alt="alipay" style="width: 20%">\n' +
                        //         '    <img src="image/site/pay2.jpg" alt="weixin" style="width: 20%">\n' +
                        //         '</div>',
                        //     icon: 'fa fa-smile-o',
                        //     theme: 'modern',
                        //     columnClass: 'col-md-12'
                        // });
                    },
                    PDFButton:function () {
                        var pre = "        <h1 style=\"text-align: center\">" +
                            helpVue.article.title +
                            "</h1>\n" +
                            "        <h4 style=\"text-align: center\">By:" +
                            helpVue.article.user.nickName +
                            "</h4>"
                        $(".text_main").print({
                            // title: helpVue.article.title+"_by"+helpVue.article.user.nickName
                            prepend:pre
                        });
                    },
                    downButton:function () {

                    },
                    upButton:function () {

                    }
                }
            });
    }
)
;