$(function () {
    var bean = {
        uri: "/foreUser",
        comments: [{parent: {uid: 0, user: {nickName: ''}}, article: {title: ''}}],
        articles: [],
        pages: [],
        user: {id: 0, score: 0, member: {max: 0}},
        has: false,
        from:0,
        to:0,
        authorized: {id: 0, uid: 0, name: '', desc: '', email: '', status: 0},
        image: null,
        uri_tag: "/user/tags",
        pages_tag: {},
        temp: [],
        tags: [],
        syms:[],
        all: [],
        tag: {id: 0, name: '', status: 0, count: 0},
        isadd:"no",
        start_search:0,
        pages_tag_search:{},
        key: '',
        start_tag: 0,
        issearch: false,
        start_comment: 0,
        tab: 0,
        isFollow : 0,
        msg:{id:0, createDate: null, text:'',status:2, sid:0, rid:0},
        uri_msg: "/user/msg"
    };
    var memberVue = new Vue(
        {
            el: ".main",
            data: bean,
            computed: {
                progress1: function () {
                    var score = this.user.score;
                    var sum = this.user.member.max + 1;
                    return score / sum * 100;
                },
                progress2: function () {
                    var score = this.user.score;
                    var sum = 50;
                    return score / sum * 100;
                },
                myScore: function () {
                    var m = this.user.member.max + 1;
                    var s = this.user.score + 1;
                    return m - s;
                },
                myComment: function (str) {
                    if (str.length > 30)
                        return str.substring(0, 30) + "...";
                }
            },
            mounted: function () {
                this.list(0);
            },
            methods: {
                list: function (start) {
                    $(".notfound_list2").hide();
                    if (this.tab == 1)
                        memberVue.start_comment = start;
                    else if (this.tab == 2)
                        memberVue.start_tag = start;
                    if (!this.issearch) {
                        $("#liketag").hide();
                        $(".pageDiv3").hide();
                    }
                    var id = getUrlParms('uid');
                    if (id == undefined)
                        id = 0;
                    var url = getPath() + this.uri + "?start_commnet=" + this.start_comment + "&start_tag=" + this.start_tag + "&uid=" + id + "&timeStamp=" + new Date().getTime();
                    axios.get(url).then(
                        function (value) {
                            console.log(value)
                            if (value.data.comments.content.length > 0) {
                                memberVue.pages = value.data.comments;
                                memberVue.comments = value.data.comments.content;
                                $(".logisticsDiv").show();
                                $("#notLogistics").hide();
                                $(".pageDiv").show();

                            }
                            else {
                                $(".logisticsDiv").hide();
                                $("#notLogistics").show();
                                $(".pageDiv").hide();
                            }
                            if (value.data.articles.length > 0) {
                                memberVue.articles = value.data.articles;
                                $(".articlesDiv").show();
                                $("#notArticles").hide();

                            }
                            else {
                                $(".articlesDiv").hide();
                                $("#notArticles").show();
                            }
                            if (value.data.all.length > 0) {
                                memberVue.all = value.data.all;
                                $(".notfound_list1").hide();
                            } else {
                                $(".notfound_list1").show();
                            }
                            if (value.data.page.content.length > 0) {
                                memberVue.pages_tag = value.data.page;
                                memberVue.temp = value.data.page.content;
                                $("#findtag").show();
                                $(".notfound_list_user").hide();
                                $(".pageDiv2").show();
                                var arr = new Array();
                                $(memberVue.temp).each(
                                    function (i, data) {
                                        var tag = {
                                            id: 0, name: '', status: 0, count: 0, has: false
                                        };
                                        tag.id = data.id;
                                        tag.name = data.name;
                                        tag.status = data.status;
                                        tag.count = data.count;
                                        $(memberVue.all).each(
                                            function (j, res) {
                                                if (res.id == tag.id) {
                                                    tag.has = true;
                                                    return;
                                                }
                                            }
                                        );
                                        arr.push(tag);
                                    }
                                );
                                memberVue.tags = arr;
                            }
                            else {
                                $("#findtag").hide();
                                $(".notfound_list_user").show();
                                $(".pageDiv2").hide();
                            }
                            memberVue.user = value.data.user;
                            memberVue.has = value.data.ismyself;
                            memberVue.from = value.data.from;
                            memberVue.to = value.data.to;
                            memberVue.isFollow  = value.data.isFollow ;

                        }
                    );
                },
                jump: function (page, tab) {
                    memberVue.tab = tab;
                    if (tab == 1) {
                        if ('first' == page && !memberVue.pages.first)
                            memberVue.list(0);

                        else if ('pre' == page && memberVue.pages.hasPrevious)
                            memberVue.list(memberVue.pages.number - 1);

                        else if ('next' == page && memberVue.pages.hasNext)
                            memberVue.list(memberVue.pages.number + 1);

                        else if ('last' == page && !memberVue.pages.last)
                            memberVue.list(memberVue.pages.totalPages - 1);
                    }
                    else if (tab == 2) {
                        if ('first' == page && !memberVue.pages_tag.first)
                            memberVue.list(0);

                        else if ('pre' == page && memberVue.pages_tag.hasPrevious)
                            memberVue.list(memberVue.pages_tag.number - 1);

                        else if ('next' == page && memberVue.pages_tag.hasNext)
                            memberVue.list(memberVue.pages_tag.number + 1);

                        else if ('last' == page && !memberVue.pages_tag.last)
                            memberVue.list(memberVue.pages_tag.totalPages - 1);
                    }
                    else if (tab == '3'){
                        if ('first' == page && !memberVue.pages_tag_search.first)
                            memberVue.search(0);

                        else if ('pre' == page && memberVue.pages_tag_search.hasPrevious)
                            memberVue.search(memberVue.pages_tag_search.number - 1);

                        else if ('next' == page && memberVue.pages_tag_search.hasNext)
                            memberVue.search(memberVue.pages_tag_search.number + 1);

                        else if ('last' == page && !memberVue.pages_tag_search.last)
                            memberVue.search(memberVue.pages_tag_search.totalPages - 1);
                    }
                },
                jumpByNumber: function (start, tab) {
                    memberVue.tab = tab;
                    if (tab == 1) {
                        if(start!=memberVue.pages.number)
                            memberVue.list(start);
                    }
                    else if (tab == 2) {
                        if(start!=memberVue.pages_tag.number)
                            memberVue.list(start);
                    } else if (tab == '3'){
                        if(start!=memberVue.pages_tag_search.number)
                            memberVue.search(start);
                    }
                },
                getImage: function (img) {
                    if (img == undefined)
                        return;
                    var url = getPath() + img;
                    return url;
                },
                getFollow: function (id) {
                    if (this.has)
                        id = 0;
                    var param = window.btoa("uid=" + id + "&timeStamp=" + new Date().getTime());
                    var url = getPath() + "/allUser?" + param;
                    return url;
                },
                getAllArticle: function (id) {
                    if (this.has)
                        id = 0;
                    var param = window.btoa("uid=" + id + "&timeStamp=" + new Date().getTime());
                    var url = getPath() + "/allArticle?" + param;
                    return url;
                },
                upgrade: function () {
                    $.dialog(
                        {
                            title: false,
                            content: '   <div align="center">\n' +
                                '             <h5>新用户首次开通赠送10积分</h5>\n' +
                                '            <h5>用户每次评论，赠送2积分</h5>\n' +
                                '        </div>',
                            theme: 'material'
                        }
                    );
                },
                follow: function (id) {
                    var url = getPath() + "/foreLoginCheck" + "?timeStamp=" + new Date().getTime();
                    axios.get(url).then(function (value) {
                        if (value.code == "500501")
                            $("#loginModel").modal("show");
                        else if (value.code == '500505') {
                            url = getPath() + "/foreFollow/" + id;
                            axios.post(url).then(function (value) {
                                memberVue.from = value.data.from;
                                memberVue.to = value.data.to;
                                memberVue.isFollow = value.data.res;
                            });
                        }else {
                            $.alert("抱歉!"+ value.msg);
                        }
                    });
                },
                sendMsg: function (id) {
                    var url = getPath() + "/foreLoginCheck" + "?timeStamp=" + new Date().getTime();
                    axios.get(url).then(function (value) {
                        if (value.code == "500501")
                            $("#loginModel").modal("show");
                        else if (value.code == '500505') {
                            $("#addMessageModel").modal("show");
                        }else {
                            $.alert("抱歉!"+ value.msg);
                        }
                    });
                },
                loginUser: function () {
                    loginUser(memberVue);
                },
                cleanName: function () {
                    cleanName(memberVue);
                },
                togglePass: function () {
                    togglePass(memberVue);
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
                                        return changePass(memberVue);
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
                                getRandom(this, memberVue);
                            }
                        }
                    );
                },
                setFile: function (e) {
                    this.image = e.target.files[0];
                },
                addAuthButton: function (e) {
                    $("#addAuthorizedModel").modal("show");
                },
                closeAuthButton: function (e) {
                    memberVue.image = null;
                    memberVue.authorized = {id: 0, uid: 0, name: '', desc: '', email: '', status: 0};
                },
                closeButton:function(e){
                    memberVue.msg = {id:0, createDate: null, status:2, sid:0, rid:0,text:''};
                    $("#addMessageModel").modal("hide");
                },
                addAuth: function () {
                    var uri = getPath() + "/foreAuthorized";
                    var formData = new FormData();
                    formData.append("image", this.image);
                    formData.append("uid", this.authorized.uid);
                    formData.append("name", this.authorized.name);
                    formData.append("desc", this.authorized.desc);
                    formData.append("email", this.authorized.email);
                    formData.append("status", this.authorized.status);
                    console.log(this.authorized)
                    console.log(this.image)
                    axios.post(uri, formData).then(
                        function (value) {
                            if (value.code == '500411') {
                                $.alert({
                                    title: '我们已收到您的申请!',
                                    content: '请留意您的邮箱，等待我们的工作人员答复'
                                });
                                memberVue.image = null;
                                memberVue.authorized = {id: 0, uid: 0, name: '', desc: '', email: '', status: 0};
                                $("#addAuthorizedModel").modal("hide");
                            }
                            else {
                                $.alert({
                                    title: '抱歉!' + value.msg,
                                    content: '请检查您的内容并尝试重新提交'
                                });
                            }
                        }
                    );
                },
                reset: function () {
                    memberVue.authorized = {id: 0, uid: 0, name: '', desc: '', email: '', status: 0};
                    memberVue.image = null;
                    $("#addAuthorizedModel").modal("hide");
                },
                addTags: function (id, e) {
                    var uri = getPath() + this.uri_tag + "/" + id;
                    axios.post(uri).then(
                        function (value) {
                            console.log(value)
                            var button = e.target;
                            if (value.code == '500406' || value.code == '500408'){
                                if (!memberVue.issearch) {
                                    memberVue.list(memberVue.start_tag, 2);
                                }
                                else{
                                    memberVue.search(memberVue.start_search);
                                }
                            }
                            if (value.code == '500406') {
                                $(button).removeClass("btn-danger");
                                $(button).addClass("btn-success");
                            }
                            else if (value.code == '500408') {
                                $(button).removeClass("btn-success");
                                $(button).addClass("btn-danger");
                            }
                            else if (value.code == '500407') {
                                $.alert("抱歉!" + value.msg);
                                return;
                            } else {
                                $.alert("抱歉!" + value.msg);
                                return;
                            }
                        }
                    );
                },
                cancel_: function () {
                    memberVue.tag = {id: 0, name: '', status: 0, count: 0};
                    memberVue.issearch = false;
                    memberVue.key = '';
                    memberVue.list(0, 2);
                },backButton:function(){
                    memberVue.issearch = false;
                    memberVue.key = '';
                    memberVue.list(0,2);
                },
                search: function (start) {
                    this.start_search =start;
                    if (!checkEmpty(this.key, '关键词')) {
                        return;
                    }
                    if (this.key.length >= 10) {
                        alert("关键词长度不能大于十，请重新搜索")
                        return;
                    }
                    memberVue.issearch = true;
                    var key = this.key;
                    var url = getPath() + memberVue.uri_tag + "/search/?key=" + key + "&start=" + start;
                    axios.post(url).then(
                        function (value) {
                            $(".pageDiv2").hide();
                            var likes = value.data.likes;
                            var finds = value.data.tags.content;
                            $("#liketag").hide();
                            $("#findtag").hide();
                            memberVue.all = value.data.all;
                            if (likes.length > 0 || finds.length > 0) {
                                if (value.data.tag == null) {
                                    $(".notfound_list_user").show();
                                }else{
                                    $(".notfound_list_user").hide();
                                }
                                memberVue.syms = [];
                                if (likes.length > 0) {
                                    $("#liketag").show();
                                    memberVue.temp = likes;
                                    var arr = new Array();
                                    $(memberVue.temp).each(
                                        function (i, data) {
                                            var tag = {
                                                id: 0, name: '', status: 0, count: 0, has: false
                                            };
                                            tag.id = data.id;
                                            tag.name = data.name;
                                            tag.status = data.status;
                                            tag.count = data.count;
                                            $(memberVue.all).each(
                                                function (j, res) {
                                                    if (res.id == tag.id) {
                                                        tag.has = true;
                                                        return;
                                                    }
                                                }
                                            );
                                            arr.push(tag);
                                        }
                                    );
                                    memberVue.syms = arr;
                                }
                                memberVue.tags = [];
                                if (finds.length > 0) {
                                    memberVue.pages_tag_search = value.data.tags;
                                    memberVue.temp = finds;
                                    $("#findtag").show();
                                    $(".pageDiv3").show();
                                    var arr = new Array();
                                    $(memberVue.temp).each(
                                        function (i, data) {
                                            var tag = {
                                                id: 0, name: '', status: 0, count: 0, has: false
                                            };
                                            tag.id = data.id;
                                            tag.name = data.name;
                                            tag.status = data.status;
                                            tag.count = data.count;
                                            $(memberVue.all).each(
                                                function (j, res) {
                                                    if (res.id == tag.id) {
                                                        tag.has = true;
                                                        return;
                                                    }
                                                }
                                            );
                                            arr.push(tag);
                                        }
                                    );
                                    memberVue.tags = arr;
                                    console.log(memberVue.tags)
                                }else {
                                    $(".pageDiv3").hide();
                                }
                            } else {
                                $(".pageDiv3").hide();
                                $(".notfound_list_user").show();
                            }
                        }
                    );
                },
                addCategoryButton: function () {
                    $("#addCategoryModel").modal("show");
                },
                sendButton:function () {
                    memberVue.msg.rid = memberVue.user.id;
                    var url = getPath() + memberVue.uri_msg;
                    axios.post(url, memberVue.msg).then(
                        function (value) {
                        if (value.code == '500404') {
                            $.alert({
                                title: '恭喜您,发送成功!',
                                content: '博主已经收到私信啦',
                                theme: 'modern',
                                icon: 'fa fa-smile-o',
                                buttons: {
                                    ok: {
                                        action: function () {
                                            memberVue.msg = {id:0, createDate: null, text:'',status:2, sid:0, rid:0};
                                            $("#addMessageModel").modal("hide");
                                        }
                                    }
                                }
                            });
                        } else {
                                $.alert({
                                    title: '抱歉!' + value.msg,
                                    content: '请检查您的内容并尝试重新提交'
                                });
                            }
                        });
                }
            }
        }
    );
});